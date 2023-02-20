package org.AlerHughes

import kotlinx.serialization.decodeFromString
import org.AlerHughes.Controller.CustomJson
import org.AlerHughes.Model.Tarot
import java.time.LocalDate

typealias tarotTable = Map<Int, Tarot>
typealias tarotCountCache = MutableMap<Long,Tarot>

var tarotsCache: tarotTable =  mapOf()
var tarotCache: tarotCountCache = mutableMapOf()
var datetemp = LocalDate.now().dayOfMonth

fun InitTarot()
{
    tarotsCache = CustomJson.decodeFromString(PluginVoodoo.dataFolder.resolve("TarotData/Tarots.json").readText())
}

fun GetRandomTarot(userid: Long) : Tarot
{
    val localDate = LocalDate.now().dayOfMonth
    if (!tarotCache.containsKey(userid) || localDate != datetemp) {
        tarotCache[userid] = tarotsCache.values.random();
        datetemp = localDate;
    };
    return tarotCache[userid]!!
}

// 获取塔罗牌正逆位文本
fun GetInfoByTarot(tarot: Tarot,randomNum: Int): String
{
    val des :String = if (randomNum == 0) {
        "\n#逆位\n#" + tarot.info.reverseDescription
    } else
        "\n#正位\n#" + tarot.info.description
    return """
        #${tarot.name} $des
        """.trimMargin("#")
}
