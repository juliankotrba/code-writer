package codewriter.impl

import codewriter.CodeWriter
import codewriter.ContainerManagement
import kotlinx.coroutines.experimental.launch
import model.CodeBlock

/**
 * JsCodeWriter
 *
 * @author  Julian Kotrba
 */
class JsCodeWriter(private val containerManagement: ContainerManagement) : CodeWriter {

    override fun write(code: CodeBlock) {

        launch {
            code.codeLines.forEach { codeLine ->
                containerManagement.appendLineOfCode(codeLine)
            }
        }

    }
}