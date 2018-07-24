package codewriter.impl

import codewriter.ContainerManager
import kotlinx.coroutines.experimental.delay
import model.CodeLine
import model.TextStyle
import model.CodeSequence
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLSpanElement
import kotlin.browser.document

/**
 * Implementation for a HTML div container manager
 *
 * @author  Julian Kotrba
 */
class DivContainerManager(private val codeContainer: HTMLDivElement,
                          private inline val writeDelayInMillisGenerator: () -> Int) : ContainerManager {

    override suspend fun appendLineOfCode(codeLine: CodeLine) {
        val lineContainer = document.createElement("div") as HTMLDivElement
        this.codeContainer.appendChild(lineContainer)

        codeLine.line.forEach { handleText(it, lineContainer) }
    }

    private suspend fun handleText(codeSequence: CodeSequence, codeLineContainer: HTMLDivElement) {
        val span = codeSequence.toSpanElement()
        codeLineContainer.appendChild(span)

        codeSequence.text.forEach { char ->
            span.innerText = span.innerText + char
            delay(this.writeDelayInMillisGenerator.invoke())
        }

        val textStyleAfter = codeSequence.styleBeforeAndAfter.second
        span.setAttribute("style", textStyleAfter.toCssString() + "white-space: pre")
    }

    private fun CodeSequence.toSpanElement(): HTMLSpanElement {
        val textStyleBefore = this.styleBeforeAndAfter.first
        return (document.createElement("span") as HTMLSpanElement).apply {
            this.textContent = ""
            this.setAttribute("style", textStyleBefore.toCssString() + "white-space: pre")
            this.setAttribute("class", this@toSpanElement.additionalElementClass)
        }
    }

    private fun TextStyle.toCssString() = "color: ${this.colorInHex};" +
            "font-weight: ${this.fontWeight};"
}