package run.smt.karafrunner.logic.util

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.Test

class ProParserTest {
    val testDotPro = """
    | a = b
    | # commented out line
    | b = c
    | b += d # some comment
    | someMultiline = configuration\
    | string
    """.trimMargin("| ")

    @Test
    fun parse() {
        val result = ProParser.parse(testDotPro)
        assertThat(result)
            .containsOnlyKeys("a", "b", "someMultiline")
            .containsExactly(
                    entry("a", setOf("b")),
                    entry("b", setOf("c", "d")),
                    entry("someMultiline", setOf("configuration\nstring"))
            )
    }

}