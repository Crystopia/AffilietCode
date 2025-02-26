package net.crystopia.affiliatecode.commands

import dev.jorel.commandapi.CommandTree
import dev.jorel.commandapi.executors.CommandExecutor
import dev.jorel.commandapi.kotlindsl.literalArgument
import gg.flyte.twilight.gui.GUI.Companion.openInventory
import gg.flyte.twilight.gui.gui
import net.crystopia.affiliatecode.AffiliateCode
import net.crystopia.affiliatecode.config.ConfigManager
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickCallback
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta


class AffiliateCodeCommand {

    var command =
        CommandTree("affiliatecode").withAliases("affiliate", "aff").executes(CommandExecutor { sender, arguments ->

            val mm = MiniMessage.miniMessage()

            val player = sender as Player

            val affiliategui =
                gui(mm.deserialize("<color:#cb3bff>Affiliate Code</color>"), type = InventoryType.HOPPER) {
                    pattern(
                        " S U ",
                    )
                    set('S', ItemStack(Material.PLAYER_HEAD).apply {
                        val meta = itemMeta as SkullMeta
                        meta.owningPlayer = viewer

                        meta.displayName(mm.deserialize("<color:#5cb6ff>You Affiliate Stats</color>"))

                        val lore: List<Component> =
                            mutableListOf(
                                mm.deserialize("<gray>-----------------------</gray>"),
                                mm.deserialize("<color:#bfcdff>Your Code</color><white>:</white> <gray>" + ConfigManager.playerdata.player[player.uniqueId.toString()]?.code),
                                mm.deserialize("<color:#7afffd>Joined Count</color><white>:</white> <gray>" + ((ConfigManager.playerdata.player[player.uniqueId.toString()]?.joinedoveryou?.size?.toString()))),
                                mm.deserialize(
                                    "<color:#ff94b2>Latest Join</color><white>:</white> <gray>" + (ConfigManager.playerdata.player[player.uniqueId.toString()]?.latest.takeIf { it!!.isNotEmpty() }
                                        ?: "None")
                                ),
                                mm.deserialize("<color:#2bff92>You Joined from</color><white>:</white> <gray>" + (ConfigManager.playerdata.player[player.uniqueId.toString()]?.joinedfor?.takeIf { it.isNotEmpty() }
                                    ?: "None")


                                )

                            )
                        meta.lore(
                            lore
                        )
                        itemMeta = meta
                    }) {
                        isCancelled = true
                    }
                    set('U', ItemStack(Material.WRITABLE_BOOK).apply {
                        val meta = itemMeta
                        meta.displayName(mm.deserialize("<color:#a3ff61>Use a Affiliate Code</color>"))
                        itemMeta = meta
                    }) {
                        isCancelled = true

                        AffiliateCode.instance.isInputCode.put(player.uniqueId.toString(), true)

                        inventory.close()

                        fun cancelInput(player: Player): ClickCallback<Audience> {
                            return ClickCallback { audience ->
                                val affiliateCodeInstance = AffiliateCode.instance
                                val playerId = player.uniqueId.toString()

                                if (affiliateCodeInstance.isInputCode.contains(playerId)) {
                                    player.sendMessage(mm.deserialize("\n  <gray>You have canceled the input.</gray>"))
                                    affiliateCodeInstance.isInputCode.remove(playerId)
                                }
                            }
                        }
                        player.sendMessage(
                            mm.deserialize("\n                 <color:#f769ff>Enter an Affiliate Code</color> <gray>[Cancel]</gray>\n")
                                .clickEvent(
                                    ClickEvent.callback(
                                        cancelInput(player)
                                    )
                                )
                        )


                    }
                }
            player.openInventory(affiliategui)


        }).literalArgument("reload") {
            withPermission("affiliatecode.command.affiliatecode.reload")
            executes(CommandExecutor { sender, args ->
                val mm = MiniMessage.miniMessage()

                val player = sender as Player

                // Make Reload!

                player.sendMessage(mm.deserialize("<color:#7dff9d>You have reloaded the configs!</color>"))

            })
        }.register()
}