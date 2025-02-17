package net.crystopia.affiliatecode.utils

import net.crystopia.affiliatecode.AffiliateCode
import net.crystopia.affiliatecode.config.ConfigManager
import su.nightexpress.coinsengine.api.CoinsEngineAPI
import su.nightexpress.coinsengine.api.currency.Currency


class EcoManager {
    var currency: Currency? = CoinsEngineAPI.getCurrency(ConfigManager.settings.Currency)
}