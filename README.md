![](assets/demo.gif)

# Code writer

This is the library I use for my code introduction block on my website. Please see [juliankotrba.xyz](https://juliankotrba.xyz).

## Usage

### Creating a code block using a DSL

#### Styles

A code sequences' style set consists of two styles. A style _textStyleBefore_, which is used when writing and
a style _textStyleAfter_ that is applied after the last character of the code string has been written.

For example, if you write any keyword into your IDE, it will not get highlighted until the word is fully written (obviously). 
That's why two different styles are needed.

``` kotlin
object Styles {

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
```

#### Code block DSL

The following code example shows how to create a new code block. First you need to call the _codeBock_ 
function. Inside the passed lambda you need to define code lines with the _codeLine_ function. Last but not least, the 
text to be written can be defined using one of the _codeSequence_ functions.

Note that there are several ways to create (a) code sequence(s)!

``` kotlin
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
```

Every code sequence also stores an element class (default value is an empty string). This class can be used if additional
styling is needed. In the above example this property is used to mark an error because of the missing enum class _Pkm_. 
It can also be used to make the code responsive as you can see inside the lambdas of the _codeSequenceWithTabs_ functions.

### Writing

A container manager needs so be created first. This example uses the a manager for a \<div\> tag container. The second 
argument of the _DivContainerManager_ is a function, which generates a write delay in milliseconds. 
The default value is 0 milliseconds.

``` kotlin
val introductionCode: CodeBlock = ...
val divContainer = document.getElementById("...") as HTMLDivElement

val divContainerManager = DivContainerManager(divContainer) {
    listOf(40, 40, 40, 50, 60, 100, 150).shuffled().first()
}

JsCodeWriter(divContainerManager)
        .write(introductionCode)

```

### Web directory

You can create a Gradle task to put all generated JavaScript files into a specific directory. 
In the example module, all JavaScript files get put into _web/js_ after building. 
Also the HTML/CSS files in the resource folder are copied to the web folder. For further information check out the [build.gradle](code-writer-library/build.gradle) file.


## License

[MIT](LICENSE)