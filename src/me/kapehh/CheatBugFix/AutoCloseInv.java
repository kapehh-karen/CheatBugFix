package me.kapehh.CheatBugFix;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

/**
 * Created with IntelliJ IDEA.
 * User: Kapeh
 * Date: 08.01.14
 * Time: 21:32
 * To change this template use File | Settings | File Templates.
 */
public class AutoCloseInv implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventory(InventoryClickEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getWhoClicked() == null) {
            return;
        }
        if (event.getInventory().getType() == InventoryType.PLAYER) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        InventoryHolder ih = event.getInventory().getHolder();
        Location player_location = player.getLocation(), location = null;
        boolean is_cancel = false;
        double radius;
        if(ih instanceof BlockState) {
            location = ((BlockState) ih).getLocation();
        } else if(ih instanceof Entity) {
            location = ((Entity) ih).getLocation();
        } else if(ih instanceof DoubleChest) {
            location = ((DoubleChest) ih).getLocation();
        }
        if (location == null) {
            return;
        }
        if (!player_location.getWorld().equals(location.getWorld())) {
            is_cancel = true;
        } else {
            radius = Math.sqrt(
                Math.pow(player_location.getX() - location.getX(), 2) +
                Math.pow(player_location.getY() - location.getY(), 2) +
                Math.pow(player_location.getZ() - location.getZ(), 2)
            );
            is_cancel = radius > 15;
        }
        if (is_cancel) {
            event.setCancelled(true);
            player.closeInventory();
            player.sendMessage("Cancelled!");
        }
    }
}
