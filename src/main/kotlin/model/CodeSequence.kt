package model

/**
 * CodeSequence
 *
 * @author  Julian Kotrba
 */
data class CodeSequence(val text: String,
                        val styleBeforeAndAfter: Pair<TextStyle, TextStyle>,
                        val additionalElementClass: String = "")