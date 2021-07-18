package us.zonix.ctf.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import us.zonix.ctf.game.Team;

public class FlagCaptureEvent extends Event implements Cancellable {

    private Team capturer;
    private Player player;

    public FlagCaptureEvent(Team capturer, Player player) {
        this.capturer = capturer;
        this.player = player;
    }

    public Team getCapturer() {
        return capturer;
    }

    public Player getPlayer() {
        return player;
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