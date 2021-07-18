package us.zonix.ctf.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import us.zonix.ctf.game.Team;

public class FlagStealEvent extends Event implements Cancellable {

    private Team flag;
    private Player player;

    public FlagStealEvent(Team flag, Player player) {
        this.flag = flag;
        this.player = player;
    }

    public Team getFlag() {
        return flag;
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