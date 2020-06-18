package utilites.proxy

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.ArrayList

class ProxyUtil{
    private val dir = "C:\\Users\\JekaJops\\IntelliJIDEAProjects\\ExampleTelegramBot\\src\\main\\java\\files\\resources\\proxy\\"
    private fun getProxy(string: String): Proxy? {
        val split = string.split(":").toTypedArray()
        val ip = split[0]
        val port = split[1]
        return Proxy(ip, port)
    }

    fun getProxyList(name: String): ArrayList<Proxy>? {
        val file = Paths.get(dir + name)
        val outputList = Files.readAllLines(file)
        val result = ArrayList<Proxy>()
        for (string in outputList) {
            result.add(getProxy(string)!!)
        }
        return result
    }

}