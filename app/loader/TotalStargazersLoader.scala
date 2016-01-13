package loader

import com.thoughtworks.microbuilder.play.{ RpcController, PlayOutgoingJsonService, RpcEntry }
import play.api.ApplicationLoader.Context
import play.api.libs.ws.ning.NingWSComponents
import play.api.routing.Router
import play.api.{ Application, ApplicationLoader, BuiltInComponentsFromContext }
import sdk._
import proxy.{ MicrobuilderIncomingProxyFactory, MicrobuilderOutgoingProxyFactory, MicrobuilderRouteConfigurationFactory }
import router.Routes

class TotalStargazersLoader extends ApplicationLoader {
  override def load(context: Context): Application = {

    val components = new BuiltInComponentsFromContext(context) with NingWSComponents {
      lazy val githubRouteConfiguration = MicrobuilderRouteConfigurationFactory.routeConfiguration_sdk_rpc_IGithubUsers()
      lazy val githubService = new PlayOutgoingJsonService("https://api.github.com/", githubRouteConfiguration, wsApi)(actorSystem.dispatcher)
      lazy val githubUsers = MicrobuilderOutgoingProxyFactory.outgoingProxy_sdk_rpc_IGithubUsers(githubService)

      lazy val bffRouteConfiguration = MicrobuilderRouteConfigurationFactory.routeConfiguration_sdk_rpc_IBffStargazerCounter()
      lazy val bffRpcEntries = Seq(
        RpcEntry(bffRouteConfiguration,
          MicrobuilderIncomingProxyFactory.incomingProxy_sdk_rpc_IBffStargazerCounter(new bff.StargazerCounter(githubUsers)(actorSystem.dispatcher))))
      lazy val bffController = new RpcController(bffRpcEntries)

      lazy val assets = new controllers.Assets(httpErrorHandler)

      override lazy val router: Router = new Routes(httpErrorHandler, bffController, assets)
    }

    components.application
  }
}