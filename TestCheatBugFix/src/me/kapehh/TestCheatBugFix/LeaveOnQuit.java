package me.kapehh.TestCheatBugFix;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kapehh
 * Date: 17.08.13
 * Time: 8:14
 * To change this template use File | Settings | File Templates.
 */
public class LeaveOnQuit implements Listener {
    private Integer[] networkid_leaveonquit = new Integer[] {40, 46};

    private boolean isContainsIntegerOnList(Integer n) {
        for (Integer i : networkid_leaveonquit)
            if (i.intValue() == n.intValue())
                return true;
        return false;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        if (p.getVehicle() == null)
            return;
        EntityType et = p.getVehicle().getType();
        if (isContainsIntegerOnList((int) et.getTypeId())) {
            p.leaveVehicle();
        }
    }
}
