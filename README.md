# gradle plugin ru-tutu-github-token

Plugins helps in creation and usage of GitHub token.
1. Use task ```./gradlew createToken``` to request GitHub token.
2. Task will print ```http://localhost:55555```. Navigate to this url in your browser.
3. Request and approve GitHub token in browser.
4. Done, your token saved to future usage. (Also you may optionally encrypt token)
5. You may use token in gradle scripts with function ```gitHubToken.getToken(project)```

## Usage in build.gradle.kts (Kotlin-DSL)
Look at sample project [sample-kts](sample-kts).
```Kotlin
plugins {
    id("ru-tutu-github-token") version "1.0.0"
}
// Configuration:
gitHubToken {
    scope = "read:packages"
    secretAES = "some_secret_key"
    saveToHomeDir = true
}
// Token usage:
val token:String = gitHubToken.getToken(project)
```

## Usage in build.gradle (Groovy)
Look at sample project [sample-groovy](sample-groovy).
```Groovy
plugins {
    id("ru-tutu-github-token") version "1.0.0"
}
//Configuration:
gitHubToken {
    scope = "read:packages"
    secretAES = "some_secret_key"
    saveToHomeDir = true
}
// Token usage:
def token = gitHubToken.getToken(project)
```
## Configuration
```Kotlin
gitHubToken {
    /**
     * Scope of GitHub token. Use white space delimiter.
     * Also look at:
     * https://developer.github.com/apps/building-oauth-apps/understanding-scopes-for-oauth-apps/
     */
    var scope: String = "read:packages"

    /**
     * Save github token to ~/.gradle/gradle.properties. 
     * Attention! Please use AES key to encrypt this globally available token!
     */
    var saveToHomeDir: Boolean = false

    /**
     * AES secret key (256-bit maximum size)
     */
    var secretAES: String? = null

    /**
     * Prefix in properties file
     */
    var propertyPrefix: String = "github.token"

    /**
     * id to configure multiple github token's in different projects
     */
    var id: String = "default"
}
```

## Source code:
Main source code of plugin available at [include_build/plugin-github-token](include_build/plugin-github-token)  

