package org.AlerHughes

import kotlinx.serialization.decodeFromString
import org.AlerHughes.Controller.CustomJson
import org.AlerHughes.Model.DivinatorySymbol
import java.time.LocalDate

typealias divinatorySymbolTable = Map<Int, DivinatorySymbol>
typealias divinatorySymbolTableCount = MutableMap<Long,DivinatorySymbol>

var divinatorySymbolsCache: divinatorySymbolTable =  mapOf()
var divinatorySymbolTableCache: divinatorySymbolTableCount = mutableMapOf()


fun InitDivinatorySymbol()
{
    divinatorySymbolsCache = CustomJson.decodeFromString(PluginVoodoo.dataFolder.resolve("DivinatorySymbolData/DivinatorySymbols.json").readText())
}

fun GetRandomDivinatorySymbol(userid: Long) : DivinatorySymbol
{
    val localDate = LocalDate.now().dayOfMonth
    if (!divinatorySymbolTableCache.containsKey(userid) || localDate != datetemp) {
        divinatorySymbolTableCache[userid] = divinatorySymbolsCache.values.random();
        datetemp = localDate;
    };
    return divinatorySymbolTableCache[userid]!!
}

// 获取易经卦象文本
fun GetInfoByDivinatorySymbol(divinatorySymbol: DivinatorySymbol): String
{
    return """
        #${divinatorySymbol.name}  ${divinatorySymbol.info.level}
        #${divinatorySymbol.info.description}
        """.trimMargin("#")
}
