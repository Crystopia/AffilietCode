package net.crystopia.affiliatecode

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import gg.flyte.twilight.twilight
import net.crystopia.affiliatecode.commands.AffiliateCodeCommand
import net.crystopia.affiliatecode.config.ConfigManager
import net.crystopia.affiliatecode.events.ChatEvent
import net.crystopia.affiliatecode.events.PlayerJoinEvent
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import su.nightexpress.coinsengine.CoinsEnginePlugin
import su.nightexpress.coinsengine.api.CoinsEngineAPI

class AffiliateCode : JavaPlugin() {

    companion object {
        lateinit var instance: AffiliateCode
    }

    var isInputCode = hashMapOf<String, Boolean>()

    init {
        instance = this
    }

    override fun onEnable() {
        CommandAPI.onLoad(CommandAPIBukkitConfig(this).silentLogs(true))
        CommandAPI.onEnable()

        val twilight = twilight(this)

        // CoinsEngine Hook
        if (Bukkit.getServer().pluginManager.getPlugin("CoinsEngine")?.isEnabled == true) {
            println(CoinsEngineAPI.getCurrency(ConfigManager.settings.Currency))
            logger.info("[AffiliateCode] Hooking into CoinsEngine")
        } else {
            logger.info("[AffiliateCode] No CoinsEngine version Found!")
            Bukkit.getServer().pluginManager.disablePlugin(this)
        }

        // Commands
        AffiliateCodeCommand()

        // Config & Data Storage
        val settings = ConfigManager.settings
        val playerdata = ConfigManager.playerdata

        //Events
        server.pluginManager.registerEvents(PlayerJoinEvent(), this)
        server.pluginManager.registerEvents(ChatEvent(), this)
    }

    override fun onDisable() {
        CommandAPI.onDisable()

    }
}
