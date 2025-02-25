package net.crystopia.affiliatecode

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import gg.flyte.twilight.twilight
import me.jesforge.econix.Econix
import me.jesforge.econix.api.EconixAPI
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

    var econix: EconixAPI? = null

    var isInputCode = hashMapOf<String, Boolean>()

    init {
        instance = this
    }

    override fun onEnable() {
        CommandAPI.onLoad(CommandAPIBukkitConfig(this).silentLogs(true))
        CommandAPI.onEnable()

        val twilight = twilight(this)

        // Econix Hook
        if (Bukkit.getServer().pluginManager.getPlugin("Econix")?.isEnabled == true) {
            logger.info("Hooking into Econix")
            logger.info(Econix.getAPI().toString())
            econix = Econix.getAPI()
        } else {
            logger.warning("No Econix version Found!")
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
