class JumpPath (private val list : List<Pair<Char,Char>>) {

    fun addStep(fromPos: Char, toPos: Char) = JumpPath(list + (fromPos to toPos) )

    override fun toString() = list.map { "${it.first} - ${it.second}" }.reduceOrNull{a,b -> "$a,$b"}?:"No path"
}