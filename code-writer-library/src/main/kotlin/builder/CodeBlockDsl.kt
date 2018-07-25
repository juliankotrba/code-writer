package builder

import model.CodeBlock
import model.CodeLine
import model.CodeSequence
import model.CodeSequenceStyle

/**
 * Dsl builder for a code block
 *
 * @author  Julian Kotrba
 */
class CodeSequenceBuilder(var text: String,
                          var styleBeforeAndAfter: Pair<CodeSequenceStyle, CodeSequenceStyle>,
                          var elementClass: String = "") {

    fun build(): CodeSequence {
        return CodeSequence(text, styleBeforeAndAfter, elementClass)
    }
}

class MultipleCodeSequencesBuilder(var charSequences: List<String>,
                                   var beforeAndAfterStyles: List<Pair<CodeSequenceStyle, CodeSequenceStyle>>,
                                   var elementClasses: List<Pair<Int, String>> = emptyList()) {

    fun build(): List<CodeSequence> {
        validate()
        return createCodeSequences()
    }

    private fun validate() {
        validateCharSequences()
        validateBeforeAndAfterStyles()
        validateElementClasses()
    }

    private fun validateCharSequences() {
        if (charSequences.isEmpty()) {
            throw IllegalArgumentException("There must be at least one charSequence")
        }
    }

    private fun validateBeforeAndAfterStyles() {
        if (beforeAndAfterStyles.size != charSequences.size) {
            throw IllegalArgumentException("There must be a Pair<styleBefore: CodeSequenceStyle, styleAfter: CodeSequenceStyle> for " +
                    "every char sequence. Currently there ${if (beforeAndAfterStyles.size == 1) "is" else "are"} just" +
                    "${beforeAndAfterStyles.size} style ${if (beforeAndAfterStyles.size == 1) "pair" else "pairs"}."
            )
        }
    }

    private fun validateElementClasses() {
        if (elementClasses.isNotEmpty()) {
            elementClasses.forEach { (index, _) ->
                if (index < 1) {
                    throw IllegalArgumentException("An index less than or equal to one is not allowed. " +
                            "Use an index from 1 to ${charSequences.size}."
                    )
                } else if (index > charSequences.size) {
                    throw IllegalArgumentException("The index $index is too big. " +
                            "Use an index from 1 to ${charSequences.size}."
                    )
                }
            }
        }
    }

    private fun createCodeSequences(): List<CodeSequence> {
        val indexToElementClassMap = elementClasses.toMap()
        val codeSequences = mutableListOf<CodeSequence>()

        for (i in 0 until charSequences.size) {
            codeSequences.add(
                    CodeSequence(
                            charSequences[i],
                            beforeAndAfterStyles[i],
                            indexToElementClassMap.getOrElse(i + 1) { "" }
                    )
            )
        }
        return codeSequences
    }
}

class CodeLineBuilder {

    companion object {
        private const val CODE_SEQUENCE_DEFAULT_TEXT = " "
        private const val CODE_SEQUENCE_DEFAULT_COLOR = "#FFFFFF"
    }

    private val texts = mutableListOf<CodeSequence>()

    operator fun CodeSequence.unaryPlus() {
        texts += this
    }

    fun codeSequence(init: CodeSequenceBuilder.() -> Unit) {
        val textWrapperBuilder = CodeSequenceBuilder(
                CODE_SEQUENCE_DEFAULT_TEXT,
                Pair(CodeSequenceStyle(CODE_SEQUENCE_DEFAULT_COLOR), CodeSequenceStyle(CODE_SEQUENCE_DEFAULT_COLOR))
        )
        textWrapperBuilder.init()
        texts += textWrapperBuilder.build()
    }

    fun codeSequence(_text: String, _styleBeforeAndAfter: Pair<CodeSequenceStyle, CodeSequenceStyle>, _elementClass: String = "") {
        codeSequence {
            text = _text
            styleBeforeAndAfter = _styleBeforeAndAfter
            elementClass = _elementClass
        }
    }

    fun codeSequenceWithTabs(tabCount: Int, text: String, styleBeforeAndAfter: Pair<CodeSequenceStyle, CodeSequenceStyle>, elementClass: String = "") {
        codeSequence(text.addPrefixTabs(tabCount), styleBeforeAndAfter, elementClass)
    }

    fun codeSequenceWithTabs(tabCount: Int, init: CodeSequenceBuilder.() -> Unit) {
        val textWrapperBuilder = CodeSequenceBuilder(
                CODE_SEQUENCE_DEFAULT_TEXT,
                Pair(CodeSequenceStyle(CODE_SEQUENCE_DEFAULT_COLOR), CodeSequenceStyle(CODE_SEQUENCE_DEFAULT_COLOR))
        )
        textWrapperBuilder.init()
        val textWrapper = textWrapperBuilder.build()
        texts += textWrapper.copy(text = textWrapper.text.addPrefixTabs(tabCount))
    }

    fun multipleCodeSequences(init: MultipleCodeSequencesBuilder.() -> Unit) {
        val textWrapperBuilder = MultipleCodeSequencesBuilder(emptyList(), emptyList(), emptyList())
        textWrapperBuilder.init()
        texts += textWrapperBuilder.build()
    }

    fun multipleCodeSequencesWithLeadingTabs(tabCount: Int, init: MultipleCodeSequencesBuilder.() -> Unit) {
        val textWrapperBuilder = MultipleCodeSequencesBuilder(emptyList(), emptyList(), emptyList())
        textWrapperBuilder.init()

        val copyOfCharSequences = textWrapperBuilder.charSequences.toMutableList()
        textWrapperBuilder.charSequences = copyOfCharSequences.apply {
            this[0] = this[0].addPrefixTabs(tabCount)
        }
        texts += textWrapperBuilder.build()
    }

    fun build(): CodeLine {
        return CodeLine(this.texts)
    }

    @Deprecated("You cannot create a line of code in a line of code.", level = DeprecationLevel.ERROR)
    fun codeLine(param: () -> Unit = {}) {
    }
}

class CodeBuilder {

    private val codeLines = mutableListOf<CodeLine>()

    operator fun CodeLine.unaryPlus() {
        codeLines += this
    }

    fun codeLine(init: CodeLineBuilder.() -> Unit) {
        val codeLineBuilder = CodeLineBuilder()
        codeLineBuilder.init()
        codeLines += codeLineBuilder.build()
    }

    fun build(): CodeBlock {
        return CodeBlock(codeLines)
    }

    @Deprecated("You are already in a code block.", level = DeprecationLevel.ERROR)
    fun code(param: () -> Unit = {}) {
    }
}

fun codeBlock(init: CodeBuilder.() -> Unit): CodeBlock {
    val codeBuilder = CodeBuilder()
    codeBuilder.init()
    return codeBuilder.build()
}

fun String.addPrefixTabs(tabCount: Int): String = "    ".repeat(tabCount) + this