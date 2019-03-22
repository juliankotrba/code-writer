package codewriter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import model.CodeBlock

/**
 * CodeWriter
 *
 * @author  Julian Kotrba
 */
class CodeWriter(
        private val coroutineScope: CoroutineScope,
        private val containerManager: ContainerManager
) {

    fun write(code: CodeBlock) {
        this.coroutineScope.launch {
            code.codeLines.forEach { codeLine ->
                containerManager.appendLineOfCode(codeLine)
            }
        }

    }
}