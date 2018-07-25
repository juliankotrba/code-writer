package model

/**
 * CodeSequence
 *
 * @author  Julian Kotrba
 */
data class CodeSequence(val text: String,
                        val styleBeforeAndAfter: Pair<CodeSequenceStyle, CodeSequenceStyle>,
                        val additionalElementClass: String = "")