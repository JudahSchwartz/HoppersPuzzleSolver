const val BoardSize = 5
class Board private constructor
    (private val backingArr: Array<Array<NodeValue>>, private val jumpPath: JumpPath = JumpPath(listOf())) {

    constructor(normalNodeList : String,enderLocation : Char) : this (createBackingState(normalNodeList,enderLocation))

    companion object {
        private fun createBackingState(normalNodeList: String, enderLocation: Char): Array<Array<Board.NodeValue>> {
            val backingBoard = Array(BoardSize){Array(BoardSize){NodeValue.EMPTY}}

            for ( i in 1..24 step 2){
                val (row,col) = getRowAndCol(i)
                backingBoard[row][col] = NodeValue.BLOCK

            }
            normalNodeList.split(",").forEach {
                val numericValue = (it[0].toInt() - 65) * 2
                val (row,col) = getRowAndCol(numericValue)
                backingBoard[row][col] = NodeValue.NORMAL
            }
            val (row,col) = getRowAndCol((enderLocation.toInt() - 65)* 2)
            backingBoard[row][col] = NodeValue.FINAL
            return backingBoard
        }

        private fun getRowAndCol(asciiValue : Int) =
            (asciiValue).let {
                it / BoardSize to it % BoardSize
            }

    }

    fun getPossibleFutureStates(): Set<Board> {
        val set = mutableSetOf<Board>()
        for (row in backingArr.indices){
            for (col in backingArr[row].indices){
                set += findFutureStatesForMovesFromIndices(row,col)
            }
        }
        return set
    }

    private fun findFutureStatesForMovesFromIndices(row: Int, col: Int) : Set<Board> {
        if(backingArr[row][col] != NodeValue.NORMAL && backingArr[row][col] != NodeValue.FINAL)
            return setOf()
        val retSet = mutableSetOf<Board>()

        for((rowTransposition,colTransposition) in getTranspositions()) {
            val destinationRow = row + rowTransposition * 2
            val destinationCol = col + colTransposition * 2
            val middleRow = row + rowTransposition
            val middleCol = col + colTransposition
            if (isOnBoard(destinationRow) && isOnBoard(destinationCol)
                && backingArr[middleRow][middleCol] == NodeValue.NORMAL &&
                backingArr[destinationRow][destinationCol] == NodeValue.EMPTY
            ) {
                val boardClone = cloneBackingArr(backingArr)
                boardClone[row][col] = NodeValue.EMPTY
                boardClone[middleRow][middleCol] = NodeValue.EMPTY
                boardClone[destinationRow][destinationCol] = backingArr[row][col]
                retSet += Board(
                    boardClone,
                    jumpPath.addStep(toLetter(row, col), toLetter(destinationRow, destinationCol))
                )


            }
        }
        return retSet
    }

    private fun getTranspositions(): List<Pair<Int,Int>> = listOf(
        -1 to -1,
        -2 to 0,
        -1 to 1,
        0 to -2,
        0 to 2,
        1 to -1,
        2 to 0,
        1 to 1
    )

    private fun toLetter(destinationRow: Int, destinationCol: Int) =
        ((destinationRow * 5 + destinationCol)/2 + 65).toChar()


    private fun cloneBackingArr(backingArr: Array<Array<Board.NodeValue>>) = Array(BoardSize){
        backingArr[it].clone()
    }

    private fun isOnBoard(index: Int) = index in 0 until BoardSize

    fun isInWinningState() =
        backingArr.all { row -> row.all { it == NodeValue.BLOCK || it == NodeValue.FINAL || it == NodeValue.EMPTY } }

    fun getWinningPath(): JumpPath {
        return jumpPath
    }
    private enum class NodeValue{
        FINAL,
        EMPTY,
        BLOCK,
        NORMAL
    }
}
