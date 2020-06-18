package utilites.proxy

import java.nio.file.Files
import java.nio.file.Paths

val dir = "C:\\Users\\JekaJops\\IntelliJIDEAProjects\\ExampleTelegramBot\\src\\main\\java\\files\\resources\\proxy\\"
fun main() {
    val file = Paths.get((dir + "unformated_proxy"))
    val inputList = Files.readAllLines(file)
    val outputList = ArrayList<String>()
    val outputFile = Paths.get(dir + "formated_proxy")
    val writer = Files.newBufferedWriter(outputFile)
    for (string in inputList) {
        if (string[0].isDigit() && string.length > 15) {
            val split = string.split("\t")
            val out = split[0] + ":" + split[1]
            // Files.writeString(Paths.get(dir+"formated_proxy"), out)
            writer.write(out)
            writer.newLine()

            //outputList.add(out)
        }
    }
    writer.close()
}