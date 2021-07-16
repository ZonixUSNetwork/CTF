package us.zonix.ctf;

import cc.fyre.proton.Proton;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import us.zonix.ctf.listener.GameListener;
import us.zonix.ctf.listener.JoinListener;
import us.zonix.ctf.manager.FlagManager;
import us.zonix.ctf.manager.GameManager;
import us.zonix.ctf.manager.KitManager;
import us.zonix.ctf.manager.MapManager;
import us.zonix.ctf.scoreboard.Scoreboard;

import java.util.Map;

public class CTF extends JavaPlugin {

    public static CTF instance;
    public GameManager gameManager = new GameManager();
    public MapManager mapManager = new MapManager();
    public FlagManager flagManager = new FlagManager();
    public KitManager kitManager = new KitManager();

    @Override
    public void onEnable() {
        instance = this;
        Proton.getInstance().getCommandHandler().registerAll(this);
        Proton.getInstance().getScoreboardHandler().setConfiguration(Scoreboard.create());
        registerListeners();
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new GameListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
    }

}
