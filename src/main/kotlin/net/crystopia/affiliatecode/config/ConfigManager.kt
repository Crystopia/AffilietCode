package net.crystopia.affiliatecode.config

import kotlinx.serialization.encodeToString
import xyz.tnsjesper.colorstatus.config.json
import xyz.tnsjesper.colorstatus.config.loadConfig
import java.io.File

object ConfigManager {

    private val settingsFile = File("plugins/AffiliateCode/config.json")
    private val playerFile = File("plugins/AffiliateCode/player.json")

    val settings = settingsFile.loadConfig(SettingsData())
    val playerdata = playerFile.loadConfig(PlayerData())

    fun save() {
        settingsFile.writeText(json.encodeToString(settings))
        playerFile.writeText(json.encodeToString(playerdata))
    }

}