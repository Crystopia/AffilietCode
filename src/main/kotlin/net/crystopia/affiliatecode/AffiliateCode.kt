package net.crystopia.affiliatecode

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import gg.flyte.twilight.twilight
import me.TechsCode.UltraEconomy.UltraEconomy
import me.TechsCode.UltraEconomy.UltraEconomyAPI
import net.crystopia.affiliatecode.commands.AffiliateCodeCommand
import net.crystopia.affiliatecode.config.ConfigManager
import net.crystopia.affiliatecode.events.ChatEvent
import net.crystopia.affiliatecode.events.PlayerJoinEvent
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class AffiliateCode : JavaPlugin() {

    companion object {
        lateinit var instance: AffiliateCode
    }

    var ueapi: UltraEconomyAPI? = UltraEconomy.getAPI()

    var isInputCode = hashMapOf<String, Boolean>()

    init {
        instance = this
    }

    override fun onEnable() {
        CommandAPI.onLoad(CommandAPIBukkitConfig(this).silentLogs(true))
        CommandAPI.onEnable()

        val twilight = twilight(this)

        // UltraEconomy Hook
        if (Bukkit.getServer().pluginManager.getPlugin("UltraEconomy")?.isEnabled == true) {
            ueapi = UltraEconomy.getAPI()
            logger.info("[AffiliateCode] Hooking into UltraEconomy")
            logger.info(ueapi.toString())
        } else {
            logger.info("[AffiliateCode] No UltraEconomy version Found!")
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
