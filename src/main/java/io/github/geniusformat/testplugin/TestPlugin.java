package io.github.geniusformat.testplugin;

import io.github.geniusformat.trollplugin.TrollPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("enabled");
        TrollPlugin.init(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("disabled");
    }
}
