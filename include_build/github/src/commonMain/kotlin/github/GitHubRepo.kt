package github

data class GitHubRepo(
    val imageUrl: String,
    val name: String,
    val organization: String,
    val description: String,
    val commitLogs:List<CommitInfo> = emptyList()
)
