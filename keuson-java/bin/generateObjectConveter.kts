import java.io.File

val file = File("../../common/com/minek/kotlin/everywehre/keuson/encode/object_.kt")
val writer = file.writer()

writer.write("""
    import com.minek.kotlin.everywehre.keuson.convert.Converter
import com.minek.kotlin.everywehre.keuson.convert.Converters
import com.minek.kotlin.everywehre.keuson.decode.Decoders
import com.minek.kotlin.everywehre.keuson.decode.map
import com.minek.kotlin.everywehre.keuson.encode.Encoder
import com.minek.kotlin.everywehre.keuson.encode.Encoders
    """)

for (i in 1..22) {
    val r = 1..i
    val types = r.joinToString(", ") { "T$it" }
    val pairs = r.joinToString(", ") { "t$it: Pair<Pair<String, (U) -> T$it>, Converter<T$it>>" }
    val encoder = r.joinToString(", ") { "t$it.first.first to t$it.second.first(t$it.first.second(it))" }
    val decoder = r.joinToString(", ") { "Decoders.field(t$it.first.first, t$it.second.second)" }
    val body = """
        fun <$types, U> Converters.object$i(mapper: ($types) -> U, $pairs): Converter<U> {
            val encoder: Encoder<U> = {
                Encoders.object_($encoder)
            }
            val decoder = map($decoder, mapper)
            return encoder to decoder
        }
    """
    writer.write(body)
}

writer.close()
