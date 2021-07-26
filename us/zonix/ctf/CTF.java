package us.zonix.ctf;

import cc.fyre.proton.Proton;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import us.zonix.ctf.listener.FlagListener;
import us.zonix.ctf.listener.GameListener;
import us.zonix.ctf.listener.JoinListener;
import us.zonix.ctf.manager.*;
import us.zonix.ctf.nametags.Nametags;
import us.zonix.ctf.scoreboard.Scoreboard;
import us.zonix.ctf.teamupgrades.Upgrade;
import us.zonix.ctf.utils.RandomFireWork;

import java.util.Map;

public class CTF extends JavaPlugin {

    public static CTF instance;

    public FileConfiguration config = getConfig();
    public GameManager gameManager = new GameManager();
    public MapManager mapManager = new MapManager();
    public FlagManager flagManager = new FlagManager();
    public KitManager kitManager = new KitManager();
    public EconomyManager economyManager = new EconomyManager();
    public TeamUpgradeManager upgradeManager = new TeamUpgradeManager();


    @Override
    public void onEnable() {
        instance = this;
        Proton.getInstance().getCommandHandler().registerAll(this);
        Proton.getInstance().getScoreboardHandler().setConfiguration(Scoreboard.create());
        Proton.getInstance().getNameTagHandler().registerProvider(new Nametags());
        RandomFireWork.addColors();
        registerListeners();
        saveConfig();
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new GameListener(), this);
        Bukkit.getPluginManager().registerEvents(new FlagListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
    }

}
