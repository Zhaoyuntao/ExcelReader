package com.test.test2app.fastrecordview

import com.test.test2app.S

/**
 * created by zhaoyuntao
 * on 2019-10-29
 * description:
 */
class Test {
    var abc: Int = 12
    var aaa=""
    var abdc: String = "asd"
    fun abc() {
        aaa="a"
        test("a")
    }

    public fun test(abdc: String) {
        fun b(a: Int): Int = a * 2
        val a: (Int, Int) -> Int = { x, y -> x + y }
        S.s(a(1, 2))  // 输出 3
        S.s(b(12))  // 输出 3
    }
}