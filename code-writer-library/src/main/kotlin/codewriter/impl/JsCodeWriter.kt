package codewriter.impl

import codewriter.CodeWriter
import codewriter.ContainerManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import model.CodeBlock

/**
 * JsCodeWriter
 *
 * @author  Julian Kotrba
 */
class JsCodeWriter(private val containerManager: ContainerManager) : CodeWriter {

    override fun write(code: CodeBlock) {
        GlobalScope.launch {
            code.codeLines.forEach { codeLine ->
                containerManager.appendLineOfCode(codeLine)
            }
        }

    }
}