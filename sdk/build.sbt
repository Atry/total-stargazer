enablePlugins(MicrobuilderJavaSdk)

enablePlugins(MicrobuilderJs)

enablePlugins(HaxeJsNpmPlugin)

organization := "com.thoughtworks.total-stargazers"

name := "sdk"

MicrobuilderCommon.autoImport.packageName := name.value

haxeOptions in Js += "com.thoughtworks.microbuilder.js.JsOutgoingJsonService"