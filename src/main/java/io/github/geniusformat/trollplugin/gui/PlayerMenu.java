package io.github.geniusformat.trollplugin.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

import javax.annotation.Nullable;

public class PlayerMenu implements InventoryHolder {
    private Inventory inv;
    private Player p;

    public PlayerMenu(Player player) {
        inv = Bukkit.createInventory(this, 54, ChatColor.GOLD + "Control " + player.getDisplayName());
        p = player;
    }

    private void init() {
        inv.addItem();
    }

    private ItemStack getHead(Player player, @Nullable Integer count) {
        if (count == null) { count = 1; }
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, count);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        skullMeta.setOwnerProfile((PlayerProfile) player);
        item.setItemMeta(skullMeta);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + player.getDisplayName());
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
