package model

/**
 * CodeSequence
 *
 * @author  Julian Kotrba
 */
data class CodeSequence(val text: String,
                        val styleSet: StyleSet,
                        val additionalElementClass: String = "")