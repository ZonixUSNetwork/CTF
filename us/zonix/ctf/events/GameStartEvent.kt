package us.zonix.ctf.events

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class GameStartEvent : Event(){

    private var isCancelled = false

    fun GameStartEvent() {
        isCancelled = false
    }

    private val handlers = HandlerList()

    override fun getHandlers(): HandlerList? {
        return handlers
    }

    fun getHandlerList(): HandlerList? {
        return handlers
    }

}