package me.kapehh.CheatBugFix;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created with IntelliJ IDEA.
 * User: Kapeh
 * Date: 16.03.14
 * Time: 21:52
 * To change this template use File | Settings | File Templates.
 */
public class AntiCheatNodusListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.isCancelled())
            return;
        if (event.hasItem()) {
            Player me = event.getPlayer();
            ItemStack it = event.getItem();
            if (isCheat(it)) {
                removeCheatItemStack(me, me.getInventory());
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerClickInventory(InventoryClickEvent event) {
        ItemStack it = event.getCurrentItem();
        if ((it != null) && isCheat(it)) {
            removeCheatItemStack((Player) event.getWhoClicked(), event.getInventory());
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInventoryOpen(InventoryOpenEvent event) {
        removeCheatItemStack((Player) event.getPlayer(), event.getInventory());
        removeCheatItemStack((Player) event.getPlayer(), event.getPlayer().getInventory());
    }

    private void removeCheatItemStack(Player me, Inventory inv) {
        boolean flag = false;
        ItemStack[] invItems = inv.getContents();
        for (int i = 0; i < invItems.length; i++) {
            if (invItems[i] == null)
                continue;
            if (isCheat(invItems[i])) {
                invItems[i].setType(Material.AIR);
                invItems[i].setAmount(0);
                flag = true;
            }
        }
        if (flag) {
            inv.setContents(invItems);
        }
    }

    public static boolean isCheat(ItemStack it) {
        return (it.getAmount() < 0) || (it.getAmount() == 0 && !it.getType().equals(Material.AIR));
    }
}
