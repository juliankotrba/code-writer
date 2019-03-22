import Styles.CMNT_STYLES
import Styles.DFLT_STYLES
import Styles.ENUM_CONSTANT_STYLES
import Styles.KW_STYLES
import Styles.STRING_STYLES
import Styles.VAR_STYLES
import builder.codeBlock
import codewriter.ContainerManager
import codewriter.impl.CodeWriterImpl
import kotlinx.coroutines.GlobalScope
import model.StyleSet
import model.CodeSequenceStyle
import model.WeightValue
import org.w3c.dom.HTMLDivElement
import kotlin.browser.document

/**
 * This example writes the following code into a div container:
 *
 * data class Person(
 *      val name: String = "Ash Ketchum",
 *      val job: String? = "Pokémon Trainer",
 *      val favPokemon: Pkm = Pkm.PIKACHU
 * )
 *
 * @author  Julian Kotrba
 */

fun main() {

    val divContainer = document.getElementById("code_container") as HTMLDivElement
    val divContainerManager = ContainerManager(divContainer) {
        listOf(40, 40, 50, 60, 100, 150, 200).shuffled().first()
    }

    CodeWriterImpl(GlobalScope, divContainerManager)
            .write(introductionCode)
}

private object Styles {

    private const val DEFAULT_COLOR = "#E8EAF6"
    private const val KEYWORD_COLOR = "#2196F3"
    private const val COMMENT_COLOR = "#00E676"
    private const val VARIABLE_COLOR = "#FF9800"
    private const val STRING_COLOR = "#00E676"

    val DFLT_STYLES = StyleSet(CodeSequenceStyle(DEFAULT_COLOR), CodeSequenceStyle(DEFAULT_COLOR))
    val KW_STYLES = StyleSet(CodeSequenceStyle(DEFAULT_COLOR), CodeSequenceStyle(KEYWORD_COLOR, WeightValue.BOLD))
    val CMNT_STYLES = StyleSet(CodeSequenceStyle(DEFAULT_COLOR), CodeSequenceStyle(COMMENT_COLOR))
    val VAR_STYLES = StyleSet(CodeSequenceStyle(VARIABLE_COLOR), CodeSequenceStyle(VARIABLE_COLOR))
    val STRING_STYLES = StyleSet(CodeSequenceStyle(STRING_COLOR), CodeSequenceStyle(STRING_COLOR))
    val ENUM_CONSTANT_STYLES = StyleSet(CodeSequenceStyle(DEFAULT_COLOR), CodeSequenceStyle(VARIABLE_COLOR))
}

private val introductionCode =
        codeBlock {
            codeLine {
                codeSequence {
                    text = "// "
                    styleSet = CMNT_STYLES
                }

                codeSequence {
                    text = "Person.kt"
                    styleSet = STRING_STYLES
                }
            }
            codeLine {
                codeSequence("data ", KW_STYLES)
                codeSequence("class ", KW_STYLES)
                codeSequence("Person(", DFLT_STYLES)
            }
            codeLine {
                codeSequenceWithTabs(2, "val ", KW_STYLES)
                codeSequence("name", VAR_STYLES)
                codeSequence(": String = ", DFLT_STYLES, "breakable")
                codeSequence("\"Ash Ketchum\"", STRING_STYLES, "cssTab")
                codeSequence(",", DFLT_STYLES)
            }
            codeLine {
                codeSequenceWithTabs(2, "val ", KW_STYLES)
                codeSequence("job", VAR_STYLES)
                codeSequence(": String? = ", DFLT_STYLES, "breakable")
                codeSequence("\"Pokémon Trainer\"", STRING_STYLES, "cssTab")
                codeSequence(",", DFLT_STYLES)
            }
            codeLine {
                multipleCodeSequencesWithLeadingTabs(2) {
                    charSequences = listOf("val ", "favPkm", ": ", "Pkm", " = ", "Pkm", ".", "PIKACHU")
                    styleSets = listOf(
                            KW_STYLES,
                            VAR_STYLES,
                            DFLT_STYLES,
                            DFLT_STYLES,
                            DFLT_STYLES,
                            DFLT_STYLES,
                            DFLT_STYLES,
                            ENUM_CONSTANT_STYLES
                    )
                    elementClasses = listOf(4 to "unknown", 6 to "unknown") // 1-indexed !
                }
            }
            codeLine {
                codeSequence(")", DFLT_STYLES)
            }
        }

