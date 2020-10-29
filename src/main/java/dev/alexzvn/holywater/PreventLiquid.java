package dev.alexzvn.holywater;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PreventLiquid extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(
            new LiquidHandler(this.getConfig()), this
        );
    }

    @Override
    public void onDisable() {}

}
