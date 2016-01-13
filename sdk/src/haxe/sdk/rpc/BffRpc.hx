package sdk.rpc;

import jsonStream.rpc.Future;
import sdk.model.BffModels;

@:nativeGen
interface IBffStargazerCounter {

  @:responseContentType("text/json; charset=utf-8")
  @:route("GET", "users/{username}/stargazer")
  function getStargazer(username:String):Future<BffUser>;

}