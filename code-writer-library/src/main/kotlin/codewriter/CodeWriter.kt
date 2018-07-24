package codewriter

import model.CodeBlock

/**
 * CodeWriter
 *
 * @author  Julian Kotrba
 */
interface CodeWriter {
    fun write(code: CodeBlock)
}