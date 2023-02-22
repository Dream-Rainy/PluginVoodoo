package org.alerHughes

import org.alerHughes.controller.GetInfoByDivinatorySymbol
import org.alerHughes.controller.GetRandomDivinatorySymbol
import org.alerHughes.controller.InitDivinatorySymbol
import net.mamoe.mirai.console.command.CommandManager
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import net.mamoe.mirai.event.EventPriority
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.utils.info
import org.alerHughes.command.*
import org.alerHughes.model.Tarot
import net.mamoe.mirai.message.data.At
import org.alerHughes.command.DivinatorySymbolCommand
import org.alerHughes.command.EverydayLuckCommand
import org.alerHughes.command.TarotCommand
import org.alerHughes.model.DivinatorySymbol
import org.alerHughes.controller.GetInfoByTarot
import org.alerHughes.controller.GetRandomTarot
import org.alerHughes.controller.InitTarot
import java.time.LocalDate
import kotlin.math.abs
import kotlin.random.Random


object PluginVoodoo : KotlinPlugin(
    JvmPluginDescription(
        id = "org.AlerHughes.plugin-voodoo",
        name = "PluginVoodoo",
        version = "1.6.1",
    ) {
        author("AlerHughes")
    }
) {
    override fun onEnable() {
        logger.info { "VoodooPlugin loaded" }

        InitTarot()
        InitDivinatorySymbol()

        CommandManager.registerCommand(EverydayLuckCommand)
        CommandManager.registerCommand(TarotCommand)
        CommandManager.registerCommand(DivinatorySymbolCommand)

        pluginVoodooEventChannel.subscribeGroupMessages (priority = EventPriority.NORMAL)
        {
            "塔罗牌" {
                val tarot: Tarot = GetRandomTarot(sender.id)
                val localDate = LocalDate.now()
                val randomNum = Random(sender.id + localDate.year + localDate.monthValue + localDate.dayOfMonth).nextInt() % 2
                val info: String = GetInfoByTarot(tarot,randomNum)
                subject.sendMessage(At(sender) + PlainText("\n" + info))
                subject.sendImage(PluginVoodoo.dataFolder.resolve(tarot.info.imgUrl))
            }

            "求签" {
                val divinatorySymbol: DivinatorySymbol = GetRandomDivinatorySymbol(sender.id)
                val info: String = GetInfoByDivinatorySymbol(divinatorySymbol)
                subject.sendMessage(At(sender) + PlainText("\n" + info))
            }

            "今日运势" {
                val localDate = LocalDate.now()
                val luck = abs(Random(sender.id + localDate.year + localDate.monthValue + localDate.dayOfMonth).nextInt()) % 100
                subject.sendMessage(At(sender) + PlainText("的今日运势为:$luck"))
            }
        }
    }

    override fun onDisable() {
        logger.info { "VoodooPlugin unloaded" }
        CommandManager.unregisterCommand(EverydayLuckCommand)
    }
}
