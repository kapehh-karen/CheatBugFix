package me.kapehh.TestCheatBugFix;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Karen on 28.05.2014.
 */
public class TestCheatBugFix extends JavaPlugin {
    public static TestCheatBugFix instance = null;

    @Override
    public void onEnable() {
        this.instance = this;

        getServer().getPluginManager().registerEvents(new AntiCheatNodusListener(), this);
        getServer().getPluginManager().registerEvents(new LeaveOnQuit(), this);
        getServer().getPluginManager().registerEvents(new AutoCloseInv(), this);
        getServer().getPluginManager().registerEvents(new FindDuperListener(), this);

        CFLogger.setup();
        CFLogger.PrintInfo("* * * * * * * * * * STARTED * * * * * * * * * *");
    }

    @Override
    public void onDisable() {
        CFLogger.shutDown();

        this.instance = null;
    }

}
