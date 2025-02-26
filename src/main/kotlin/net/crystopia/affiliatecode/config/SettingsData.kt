package net.crystopia.affiliatecode.config

import kotlinx.serialization.Serializable

@Serializable
data class SettingsData(
    var Currency: String = "crystals",
    var RewardAmount: Double = 25.0,
)

@Serializable
data class PlayerData(
    var playercache: MutableList<String> = mutableListOf(),
    var player: MutableMap<String, PlayerConfig> = mutableMapOf()
)

@Serializable
data class PlayerConfig(
    var code: String? = " ",
    var joinedoveryou: MutableList<String> = mutableListOf(),
    var joinedfor: String? = "",
    var latest: String? = "",
)