package codewriter.impl

import codewriter.ContainerManager
import kotlinx.coroutines.experimental.delay
import model.CodeLine
import model.CodeSequenceStyle
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
                          private inline val writeDelayInMillisGenerator: () -> Int = { 0 }) : ContainerManager {

    override suspend fun appendLineOfCode(codeLine: CodeLine) {
        val lineContainer = document.createElement("div") as HTMLDivElement
        this.codeContainer.appendChild(lineContainer)

        codeLine.line.forEach {
            handleText(it, lineContainer)
        }
    }

    private suspend fun handleText(codeSequence: CodeSequence, codeLineContainer: HTMLDivElement) {
        val span = codeSequence.toSpanElement()
        codeLineContainer.appendChild(span)

        codeSequence.text.forEach { char ->
            span.innerText = span.innerText + char
            delay(this.writeDelayInMillisGenerator.invoke())
        }

        val textStyleAfter = codeSequence.styleSet.textStyleAfter
        span.setAttribute("style", textStyleAfter.toCssString() + "white-space: pre")
    }

    private fun CodeSequence.toSpanElement(): HTMLSpanElement {
        val textStyleBefore = this.styleSet.textStyleBefore
        return (document.createElement("span") as HTMLSpanElement).apply {
            this.textContent = ""
            this.setAttribute("style", textStyleBefore.toCssString() + "white-space: pre")
            this.setAttribute("class", this@toSpanElement.additionalElementClass)
        }
    }

    private fun CodeSequenceStyle.toCssString() = "color: ${this.colorInHex};" +
            "font-weight: ${this.fontWeight};"
}

