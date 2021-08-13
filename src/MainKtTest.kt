import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainKtTest{
    @Test
    fun testPuzzleCases() {
        givenInputsShouldResultIn("A,F,G,I", 'H', "A - K,H - F,K - G,F - H")
        givenInputsShouldResultIn("A,B,D,E,F,H,I,L", 'J', "A - C,H - B,C - A,A - K,L - F,K - A,A - G,J - D")
    }
    private fun givenInputsShouldResultIn(normalNodes : String, endLoc : Char, result : String){
        val jumpPath = findSolution(Board(normalNodes, endLoc))
        assertEquals(jumpPath.toString(),result)
    }
}