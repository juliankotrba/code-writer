package codewriter.impl

import codewriter.CodeWriter
import codewriter.ContainerManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import model.CodeBlock

/**
 * CodeWriterImpl
 *
 * @author  Julian Kotrba
 */
class CodeWriterImpl(
        private val coroutineScope: CoroutineScope,
        private val containerManager: ContainerManager
) : CodeWriter {

    override fun write(code: CodeBlock) {
        this.coroutineScope.launch {
            code.codeLines.forEach { codeLine ->
                containerManager.appendLineOfCode(codeLine)
            }
        }

    }
}