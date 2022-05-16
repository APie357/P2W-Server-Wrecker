package io.github.geniusformat.trollplugin.items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.*;

public class Wand implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Block targetedBlock = p.getTargetBlock(null, p.getClientViewDistance() * 32);
        Location location = targetedBlock.getLocation();
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR)) {
            if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() != null) {
                if (Objects.requireNonNull(event.getItem()).getItemMeta() != null && event.getItem().getItemMeta().getLore() != null) {
                    if (Objects.equals(event.getItem().getItemMeta().getLore().get(0), "WAND")) {
                        List<String> loreSplit = new ArrayList<>(Arrays.asList(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(event.getItem()).getItemMeta()).getLore()).get(1).split(" ")));
                        Material mat = Material.matchMaterial(loreSplit.get(0));
                        int radius = Integer.parseInt(loreSplit.get(1));

                        World world = p.getWorld();

//                    for(int x = Math.max(max.getBlockX(), min.getBlockX()); x > Math.min(min.getBlockX(), max.getBlockX()); x--) {
//                        for(int y = Math.max(max.getBlockY(), min.getBlockY()); y > Math.min(min.getBlockY(), max.getBlockY()); y--) {
//                            for(int z = Math.max(max.getBlockZ(), min.getBlockZ()); z > Math.min(min.getBlockZ(), max.getBlockZ()); z--) {
//                                Block block = world.getBlockAt(x, y, z);
//                                block.setType(mat);
//                            }
//                        }
//                    }

//                    for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
//                        for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
//                            for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
//                                Block block = world.getBlockAt(x, y, z);
//                                block.setType(mat);
//                            }
//                        }
//                    }

                        for (int x = (location.getBlockX() - radius); x <= (location.getBlockX() + radius); x++) {
                            for (int y = (location.getBlockY() - radius); y <= (location.getBlockY() + radius); y++) {
                                for (int z = (location.getBlockZ() - radius); z <= (location.getBlockZ() + radius); z++) {
                                    Block block = world.getBlockAt(x, y, z);
                                    assert mat != null;
                                    block.setType(mat);
                                }
                            }
                        }

                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
