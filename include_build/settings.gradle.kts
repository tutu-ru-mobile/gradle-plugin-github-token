include("plugin-github-token")
include("github")
include("client")
include("network")
include("lib")
include("lib-mvi")
include("lib-redux")
include("ci-panel-mvi")
include("ci-panel-view")
include("aes")
if (System.getenv("BUILD_JAR_CLI") == "true") {
    include("jar-cli")
}
