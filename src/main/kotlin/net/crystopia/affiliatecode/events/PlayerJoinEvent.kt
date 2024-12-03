package net.crystopia.affiliatecode.events

import net.crystopia.affiliatecode.config.ConfigManager
import net.crystopia.affiliatecode.config.PlayerConfig
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinEvent : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {

        if (ConfigManager.playerdata.player[event.player.uniqueId.toString()] == null) {
            ConfigManager.playerdata.player[event.player.uniqueId.toString()] =
                PlayerConfig(
                    code = event.player.name,
                    joinedoveryou = mutableListOf(),
                    joinedfor = " ",
                    latest = "",
                )
        }

        ConfigManager.playerdata.player[event.player.uniqueId.toString()]?.code = event.player.name
        ConfigManager.save()


    }

}