package net.crystopia.affiliatecode.config

interface Configurable {
    fun save()
    fun load() {}
    fun reset() {}
}