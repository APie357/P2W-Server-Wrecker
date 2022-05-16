package io.github.geniusformat.trollplugin.items;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ZeusStick implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Block targetedBlock = p.getTargetBlock(null, p.getClientViewDistance() * 32);
        Location location = targetedBlock.getLocation();
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR)) {
            if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() != null) {
                if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§6Zeus's Stick")) {
                    p.getWorld().strikeLightning(location);
                    p.getWorld().createExplosion(location, 2, true, true);
                    p.getWorld().createExplosion(location, 10, true, false);
                }
            }
        }
    }
}
