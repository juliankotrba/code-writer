package codewriter

import model.CodeLine

/**
 * This class is an expected manager responsible for managing a container eg. a HTML div element.
 * Currently it is just possible to append a line of code
 *
 * @author  Julian Kotrba
 */
expect class ContainerManager {
    suspend fun appendLineOfCode(codeLine: CodeLine)
}
