package codewriter

import kotlinx.coroutines.experimental.delay
import model.CodeLine
import model.TextStyle
import model.CodeSequence
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLSpanElement
import kotlin.browser.document

/**
 * Implementation for a HTML div container
 *
 * @author  Julian Kotrba
 */
class HtmlDivContainer(private val codeContainer: HTMLDivElement,
                       private inline val writeDelayGenerator: () -> Int) : ContainerManagement {

    override suspend fun appendLineOfCode(codeLine: CodeLine) {
        val lineContainer = document.createElement("div") as HTMLDivElement
        this.codeContainer.appendChild(lineContainer)

        codeLine.line.forEach { handleText(it, lineContainer, this.writeDelayGenerator) }
    }

    private suspend fun handleText(codeSequence: CodeSequence, codeLineContainer: HTMLDivElement, delayGenerator: () -> Int) {
        val span = createSpanElement(codeSequence)
        codeLineContainer.appendChild(span)

        codeSequence.text.forEach { char ->
            span.innerText = span.innerText + char
            delay(delayGenerator.invoke())
        }

        val (_, textStyleAfter) = codeSequence.styleBeforeAndAfter
        span.setAttribute("style", textStyleAfter.toCssString() + "white-space: pre")
    }

    private fun createSpanElement(codeSequence: CodeSequence): HTMLSpanElement {
        val (textStyleBefore, _) = codeSequence.styleBeforeAndAfter
        return (document.createElement("span") as HTMLSpanElement).apply {
            this.textContent = ""
            this.setAttribute("style", textStyleBefore.toCssString() + "white-space: pre")
            this.setAttribute("class", codeSequence.additionalElementClass)
        }
    }

    private fun TextStyle.toCssString() = "color: ${this.colorInHex};" +
            "font-weight: ${this.fontWeight};"
}