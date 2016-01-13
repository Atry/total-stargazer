package sdk.rpc;

import jsonStream.rpc.Future;
import sdk.model.GithubModels;
import haxe.ds.Vector;

@:structuralFailure(sdk.model.GithubModels.GithubFailure)
@:nativeGen
interface IGithubUsers {
  
  @:responseContentType("application/json; charset=utf-8")
  @:route("GET", "users/{login}")
  function getSingleUser(login:String):Future<GithubUser>;
  
  @:responseContentType("application/json; charset=utf-8")
  @:route("GET", "users/{login}/repos")
  function getRepositories(login:String):Future<Vector<GithubRepository>>;
  
}