//todo unused
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class SampleTests {
    @Test
    fun testMath() {
        assertEquals(4, 2 * 2)
        assertNotEquals(5, 2 + 2)
    }
}

class SampleEnzymeTest : EnzymeTest() {
    @Test
    fun testPostView() = runAsyncTest {
//        var callCount = 0
//        val testPostData = GitHubRepo(
//            post = Post(1, 1, "title", "body"),
//            comments = mutableListOf(
//                CommitInfo(1, 1, "John.Doe", "john.doe@example.com", "comment"),
//                CommitInfo(1, 2, "Jane.Doe", "jane.doe@example.com", "comment")
//            )
//        )
//
//        val element = enzymeMount {
//            child(PostView::class) {
//                attrs.gitHubRepo = testPostData
//                attrs.onMoreComments = {
//                    callCount++
//                }
//            }
//        }
//
//        assertEquals(2, element.find(".PostStyles-comment").map { it.domInstance() }.size)
//
//        element.find("button").simulate("mousedown")
//
//        assertEquals(1, callCount)
//        assertTrue(element.find("PostView").state<PostState>().loading)
    }
}