package model

/**
 * CodeSequence
 *
 * @author  Julian Kotrba
 */
data class CodeSequence(val text: String,
                        val beforeAndAfterStyle: BeforeAndAfterStyle,
                        val additionalElementClass: String = "")