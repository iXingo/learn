object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val a = 3
        val b = 8
        val c = 2
        val d = 5
        println("最大值为：" + maxData(a, b))
        println("最大值为：" + maxData(c, d))
    }

    //判断最大值
    private fun maxData(a: Int, b: Int): Int {
        return if (a > b) a else b
    }
}