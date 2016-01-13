package view

import com.thoughtworks.binding.{ Binding, dom }

import scala.util.Either
import scala.util.Left
import scala.util.Right
import scala.scalajs.js


/**
 * @author 杨博 (Yang Bo) &lt;pop.atry@gmail.com&gt;
 */
final case class StargazerStatus(stargazerData: Binding[Option[Either[Any, js.Dynamic]]]) {
  @dom
  def render = {
    stargazerData.each match {
      case None =>
        <div>Loading...</div>
      case Some(Left(failure)) =>
        <div>Failed to fetch stargazer data: { failure.toString }</div>
      case Some(Right(data)) =>
        <div>
          { data.name.toString() }
          has
          { data.totalStargazers.toString() }
          stargazers in his
          { data.numberOfRepositories.toString() }
          repositories.
        </div>
    }
  }
}