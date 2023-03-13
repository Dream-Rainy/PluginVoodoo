package org.alerHughes.controller

import kotlinx.serialization.decodeFromString
import org.alerHughes.model.DivinatorySymbol
import org.alerHughes.PluginVoodoo
import java.time.LocalDate

typealias divinatorySymbolTable = Map<Int, DivinatorySymbol>
typealias divinatorySymbolTableCount = MutableMap<Long,DivinatorySymbol>

var divinatorySymbolsCache: divinatorySymbolTable =  mapOf()
var divinatorySymbolTableCache: divinatorySymbolTableCount = mutableMapOf()
var divinatoryDatetemp: Int = 1


fun InitDivinatorySymbol()
{
    divinatorySymbolsCache = CustomJson.decodeFromString(PluginVoodoo.dataFolder.resolve("DivinatorySymbolData/DivinatorySymbols.json").readText())
}

fun GetRandomDivinatorySymbol(userid: Long) : DivinatorySymbol
{
    val localDate = LocalDate.now().dayOfMonth
    if (!divinatorySymbolTableCache.containsKey(userid) || localDate != divinatoryDatetemp) {
        divinatorySymbolTableCache[userid] = divinatorySymbolsCache.values.random();
        divinatoryDatetemp = localDate;
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
