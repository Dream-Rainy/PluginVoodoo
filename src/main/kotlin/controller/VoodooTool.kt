package org.alerHughes.controller

import kotlinx.serialization.decodeFromString
import org.alerHughes.model.Tarot
import org.alerHughes.PluginVoodoo
import java.time.LocalDate

typealias tarotTable = Map<Int, Tarot>
typealias tarotCountCache = MutableMap<Long,Tarot>

var tarotsCache: tarotTable =  mapOf()
var tarotCache: tarotCountCache = mutableMapOf()
var datetemp: Int = 1

fun InitTarot()
{
    tarotsCache = CustomJson.decodeFromString(PluginVoodoo.dataFolder.resolve("TarotData/Tarots.json").readText())
}

fun GetRandomTarot(userid: Long) : Tarot
{
    val localDate: Int = LocalDate.now().dayOfMonth
    //println(localDate)
    //println(datetemp)
    if (!tarotCache.containsKey(userid) || localDate != datetemp) {
        tarotCache[userid] = tarotsCache.values.random();
        //println("change the tarot result ok")
        //println(tarotCache[userid])
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
