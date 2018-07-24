package codewriter

import model.CodeLine

/**
 * This interface is responsible managing a container eg. a HTML div element.
 * Currently it is just possible to append a line of code
 *
 * @author  Julian Kotrba
 */
interface ContainerManager {
    suspend fun appendLineOfCode(codeLine: CodeLine)
}