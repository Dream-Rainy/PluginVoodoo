package org.alerHughes.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.At
import org.alerHughes.controller.GetInfoByDivinatorySymbol
import org.alerHughes.controller.GetRandomDivinatorySymbol
import org.alerHughes.model.DivinatorySymbol
import org.alerHughes.PluginVoodoo


object DivinatorySymbolCommand : SimpleCommand(
    PluginVoodoo, "vdDS","卦象",
    description = "易经卦象"
){

    @Handler
    suspend fun CommandSender.handle() {
        val divinatorySymbol: DivinatorySymbol = GetRandomDivinatorySymbol(user!!.id)
        val info: String = GetInfoByDivinatorySymbol(divinatorySymbol)

        sendMessage(At(user!!) + PlainText("\n" + info))
    }
}