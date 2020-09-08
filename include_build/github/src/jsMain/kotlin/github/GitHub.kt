package github

import lib.Result2
import lib.dyn
import network.Method
import network.requestStr
import org.w3c.dom.url.URL
import kotlin.browser.window

private val DEBUG_MODE = false

object GitHub {

    fun auth(clientId: String) {
        window.location.href = authHref(clientId)
    }

    fun authHref(clientId: String): String {
        //https://developer.github.com/apps/building-oauth-apps/understanding-scopes-for-oauth-apps/
        val url = URL("https://github.com/login/oauth/authorize/");
        url.searchParams.append("client_id", clientId)
        url.searchParams.append("scope", "read:packages")
        // url.searchParams.append("redirect_uri", REDIRECT_URL)
        //document.body?.innerHTML = "<a href='${url.toString()}'>login in GitHub</a>"
        val href = url.toString()
        return href
    }

    suspend fun repos(organization: String) =
        requestStr("https://api.github.com/orgs/$organization/repos")
            .map { jsonStr ->
                val dynamicArray: Array<dynamic> = JSON.parse(jsonStr)
                dynamicArray.map { obj: dynamic ->
                    GitHubRepo(
                        name = obj.name,
                        imageUrl = obj.owner.avatar_url,
                        organization = obj.owner.login,
                        description = obj.description
                    )
                }
            }

    suspend fun commitLog(organization: String, repo: String, count: Int = 5) =
        requestStr("https://api.github.com/repos/$organization/$repo/commits")
            .map { jsonStr ->
                val dynamicArray: Array<dynamic> = JSON.parse(jsonStr)
                dynamicArray.map { obj: dynamic ->
                    CommitInfo(
                        author = obj.commit.author.email,
                        title = obj.commit.message,
                        time = obj.commit.author.date
                    )
                }
            }

    suspend fun repoInfo(token: String, organization: String, repo: String): Result2<RepoInfo> =
        requestStr(
            hostPath = "https://api.github.com/graphql",
            method = Method.POST,
            headers = mapOf(
                "Authorization" to "bearer $token",
                "Accept" to "*/*",
                "Content-Type" to "application/json"
            ),
            body = JSON.stringify(dyn {
                // https://developer.github.com/v4/explorer/
                //language=JavaScript
                query = """
                   query {
                      repository(owner:"$organization", name:"$repo") {
                        name
                        refs(refPrefix:"refs/heads/", last:100) {
                          edges {
                            node {
                              name
                            }
                          }
                          totalCount
                        }
                        defaultBranchRef {
                          name
                        }
                      }
                    }
                """
            })
        ).map { jsonStr ->
            println(jsonStr)
            val repository = JSON.parse<dynamic>(jsonStr).data.repository
            RepoInfo(
                branches = (repository.refs.edges as Array<dynamic>).map { it.node.name },
                defaultBranch = repository.defaultBranchRef.name
            )
        }

    suspend fun branchesOld(token: String, organization: String, repo: String): Result2<List<BranchInfo>> =
        requestStr(
            "https://api.github.com/repos/$organization/$repo/branches",
            headers = mapOf(
                "Authorization" to "token $token"
//                "Accept" to  "application/vnd.github.ant-man-preview+json"
            )
        )
            .map { jsonStr ->
                val dynamicArray: Array<dynamic> = JSON.parse(jsonStr)
                dynamicArray.map { obj: dynamic ->
                    BranchInfo(
                        name = obj.name
                    )
                }
            }

    suspend fun deploy(
        token: String,
        organization: String,
        project: String,
        branch: String,
        payloadObj: Any//todo Map<String, Any>
    ): Result2<String> {
        val jsonStr = JSON.stringify(
            kotlinToJsObject(
                mapOf(
                    "payload" to payloadObj,
                    "ref" to branch,
                    "auto_merge" to false,
                    "environment" to "qa",
                    "required_contexts" to emptyArray<String>()
                )
            ),
            space = 1
        )
        val confirmBodyStr: String? = if (DEBUG_MODE) {
            window.prompt("Confirm to trigger GitHub Actions", jsonStr)
        } else {
            jsonStr
        }
        return if (confirmBodyStr == null) {
            Result2.success("CANCELED BY USER")
        } else {
            requestStr(
                hostPath = "https://api.github.com/repos/$organization/$project/deployments",
                method = Method.POST,
                headers = mapOf(
                    "Authorization" to "token $token",
                    "Accept" to "application/vnd.github.ant-man-preview+json"
                ),
                body = confirmBodyStr
            )
        }
    }

}

class RepoInfo(
    val branches: List<String>,
    val defaultBranch: String
)

fun kotlinToJsObject(obj: Any?): Any? =
    when (obj) {
        is Map<*, *> -> {
            val result: dynamic = dyn {}
            obj.entries.forEach {
                result[it.key.toString()] = kotlinToJsObject(it.value)
            }
            result
        }
        is Collection<*> -> {
            obj.map { kotlinToJsObject(it) }
        }
        else -> {
            obj
        }
    }
