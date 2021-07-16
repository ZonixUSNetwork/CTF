package us.zonix.ctf.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStartEvent extends Event implements Cancellable {

    public GameStartEvent() {

    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean arg0) {
    }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}