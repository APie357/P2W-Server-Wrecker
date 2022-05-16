package io.github.geniusformat.trollplugin.items;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

public class Nuke implements Listener {
    List<FallingBlock> nukes = new ArrayList<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() != null) {
                if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§cHand Grenade")) {
                    Player p = event.getPlayer();

                    Block targetedBlock = p.getTargetBlock(null, p.getClientViewDistance() * 32);
                    Location location = targetedBlock.getLocation();
                    p.getWorld().createExplosion(location, 50, false, true);

                    event.setCancelled(true);
                }
            }
        }
    }
}
