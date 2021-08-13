fun main(args: Array<String>) {
    val board = Board(args[0].toUpperCase(), args[1].toUpperCase()[0])
    val jumpPath = findSolution(board)
    if(jumpPath != null){
        println("solution is : $jumpPath")
    }
    else {
        print("No solution is possible for this configuration")
    }

}

fun findSolution(board: Board): JumpPath? {
    if(board.isInWinningState())
        return board.getWinningPath()
    for(nextBoard in board.getPossibleFutureStates() ){
        val solution = findSolution(nextBoard)
        if(solution != null)
            return solution
    }

    return null
}
