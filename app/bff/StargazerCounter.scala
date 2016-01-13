package bff

import concurrent.ExecutionContext
import concurrent.Future
import com.thoughtworks.each.Monadic._
import jsonStream.rpc.IFuture1
import scalaz.std.scalaFuture._
import sdk._
import model._
import com.thoughtworks.microbuilder.play.Implicits._
import com.thoughtworks.microbuilder.play.exception.MicrobuilderException._

final class StargazerCounter(users: rpc.IGithubUsers)(implicit executionContext: ExecutionContext) extends rpc.IBffStargazerCounter {

  override def getStargazer(login: String): IFuture1[BffUser] = throwableMonadic[Future] {
    val result = new BffUser()
    try {
      val githubUserFuture: Future[GithubUser] = users.getSingleUser(login)
      result.name = githubUserFuture.each.name
    } catch {
      case e: StructuralApplicationException[_] =>
        e.status match {
          case 404 =>
            throw TextApplicationException(s"Failed to getSingleUser for $login: ${e.failure.asInstanceOf[GithubFailure].message}", 404)
        }
    }
    val githubRepositories = try {
      val githubRepositoriesFuture: Future[Array[GithubRepository]] = users.getRepositories(login)
      githubRepositoriesFuture.each
    } catch {
      case e: StructuralApplicationException[_] =>
        e.status match {
          case 404 =>
            throw TextApplicationException(s"Failed to getRepositories for $login: ${e.failure.asInstanceOf[GithubFailure].message}", 404)
        }
    }
    result.numberOfRepositories = githubRepositories.length
    result.totalStargazers = (for { repository <- githubRepositories.view } yield repository.stargazers_count).sum
    result
  }

}