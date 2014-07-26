package me.kapehh.CheatBugFix;

import me.kapehh.main.pluginmanager.logger.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Karen on 28.05.2014.
 */
public class CheatBugFix extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new AntiCheatNodusListener(), this);
        getServer().getPluginManager().registerEvents(new LeaveOnQuit(), this);
        getServer().getPluginManager().registerEvents(new AutoCloseInv(), this);
    }

}
