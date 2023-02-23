package org.alerHughes.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.getGroupOrNull
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import org.alerHughes.controller.GetRandomTarot
import org.alerHughes.controller.GetInfoByTarot
import org.alerHughes.model.Tarot
import org.alerHughes.PluginVoodoo
import java.time.LocalDate
import kotlin.random.Random


object TarotCommand : SimpleCommand(
    PluginVoodoo, "vdTarot","塔罗牌","tarot",
    description = "塔罗牌占卜"
){
    @Handler
    suspend fun CommandSender.handle() {
        val localDate = LocalDate.now()
        val tarot: Tarot = GetRandomTarot(user!!.id)
        val randomNum = Random(user!!.id + localDate.year + localDate.monthValue + localDate.dayOfMonth).nextInt() % 2
        val info: String = GetInfoByTarot(tarot, randomNum)

        val imgFile = PluginVoodoo.resolveDataFile(tarot.info.imgUrl).uploadAsImage(user!!)
        sendMessage(At(user!!) + PlainText("\n" + info) + imgFile)
        //sendMessage(At(user!!) + PlainText("\n" + info))
        //getGroupOrNull()?.sendImage((PluginVoodoo.dataFolder.resolve(tarot.info.imgUrl)))
    }
}