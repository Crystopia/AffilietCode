package net.crystopia.affiliatecode.events

import io.papermc.paper.event.player.AsyncChatEvent
import net.crystopia.affiliatecode.AffiliateCode
import net.crystopia.affiliatecode.config.ConfigManager
import net.crystopia.affiliatecode.utils.EcoManager
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import su.nightexpress.coinsengine.api.CoinsEngineAPI

class ChatEvent : Listener {

    @EventHandler
    fun onChat(event: AsyncChatEvent) {
        val mm = MiniMessage.miniMessage()

        val player = event.player
        val message = event.message() as TextComponent
        val text = message.content()

        if (AffiliateCode.instance.isInputCode.contains(player.uniqueId.toString())) {
            event.isCancelled = true

            val reward = ConfigManager.settings.RewardAmount

            val reciverplayer = Bukkit.getOfflinePlayer(text)

            val reciverconfig = ConfigManager.playerdata.player[reciverplayer.uniqueId.toString()]
            val userconfig = ConfigManager.playerdata.player[player.uniqueId.toString()]


            if (reciverconfig == null) {
                player.sendMessage(mm.deserialize("‰ <color:#ff6047>An error has occurred!</color> <color:#ff9a6b>This player does not have an affiliate account.</color>"))
                AffiliateCode.instance.isInputCode.remove(player.uniqueId.toString())
                return
            } else if (reciverplayer == null) {
                player.sendMessage(mm.deserialize("‰ <color:#ff6047>This Player was not Found</color>"))
                AffiliateCode.instance.isInputCode.remove(player.uniqueId.toString())
                return
            } else if (reciverplayer.player == player) {
                player.sendMessage(mm.deserialize("‰ <color:#ff6047>You can use your own name!</color>"))
                AffiliateCode.instance.isInputCode.remove(player.uniqueId.toString())
                return
            } else if (ConfigManager.playerdata.playercache.contains(player.uniqueId.toString())) {
                player.sendMessage(mm.deserialize("‰ <color:#ff6047>You have already used an affiliate code</color>"))
                AffiliateCode.instance.isInputCode.remove(player.uniqueId.toString())
                return
            } else {
                var reciveraccount = EcoManager().currency?.let {
                    CoinsEngineAPI.addBalance(
                        reciverplayer.uniqueId, it, reward.toDouble()
                    )
                };
                var useraccount = EcoManager().currency?.let {
                    CoinsEngineAPI.addBalance(
                        player.uniqueId, it, reward.toDouble()
                    )
                };

                userconfig?.joinedfor = reciverplayer.name.toString()

                ConfigManager.playerdata.playercache.add(player.uniqueId.toString())

                reciverconfig.latest = player.name

                reciverconfig.joinedoveryou.add(player.uniqueId.toString())

                ConfigManager.save()

                AffiliateCode.instance.isInputCode.remove(player.uniqueId.toString())

                player.sendMessage(mm.deserialize("ċ <color:#70ff81>You have successfully used the affiliate of</color> <color:#c7fff5>" + reciverplayer.name + "</color>. <gray>You have received</gray> <color:#75fffa>" + reward.toInt() + "</color> <white>⁐.</white>"))

            }
        }
    }

}