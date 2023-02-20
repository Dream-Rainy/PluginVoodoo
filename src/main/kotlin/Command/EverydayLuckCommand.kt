package org.AlerHughes.Command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.PlainText
import org.AlerHughes.PluginVoodoo
import java.time.LocalDate
import kotlin.random.Random

object EverydayLuckCommand : SimpleCommand(
    PluginVoodoo, "vdLuck","jrrp","今日人品",
    description = "今日运势"
){
    @Handler
    suspend fun CommandSender.handle() {
        val localDate = LocalDate.now()
        val luck = Math.abs(Random(user!!.id + localDate.year + localDate.monthValue + localDate.dayOfMonth).nextInt()) % 100
        sendMessage(At(user!!) + PlainText("今天的欧气值为:" + luck))
    }
}