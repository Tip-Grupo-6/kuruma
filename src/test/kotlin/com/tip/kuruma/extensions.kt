@file:Suppress("TestFunctionName", "UNUSED_PARAMETER")

package com.tip.kuruma

import org.junit.jupiter.api.Assertions

fun <T> GIVEN(message: String = "", block: () -> T): T = block()
fun <T> WHEN(message: String = "", block: () -> T): T = block()
fun <T> AND(message: String = "", block: () -> T): T = block()
fun <T> THEN(message: String = "", block: () -> T): T = block()
inline fun <reified E : Throwable> THEN_THROW(message: String = "", crossinline block: () -> Unit): E {
    return Assertions.assertThrows(E::class.java) {
        block()
    }
}

fun <T> EXPECT(message: String = "", block: () -> T): T = block()
inline fun <reified E : Throwable> EXPECT_TO_THROW(message: String = "", crossinline block: () -> Unit): E {
    return Assertions.assertThrows(E::class.java) {
        block()
    }
}

inline fun EXPECT_NOT_THROW(message: String = "", crossinline block: () -> Unit) {
    return Assertions.assertDoesNotThrow {
        block()
    }
}


infix fun Any?.equals(expected: Any?) {
    Assertions.assertEquals(expected, this)
}

infix fun <T> T.assertTrue(block: (T) -> Boolean) {
    Assertions.assertTrue(block(this))
}
