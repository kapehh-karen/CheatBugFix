package me.kapehh.TestCheatBugFix;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Created with IntelliJ IDEA.
 * User: Kapeh
 * Date: 18.12.13
 * Time: 23:16
 * To change this template use File | Settings | File Templates.
 */
public class LocationEx {

    public static String toString(Location in) {
        return toString(in, false);
    }

    public static String toString(Location in, boolean useInt) {
        if (in == null) {
            return "null";
        }
        if (useInt) {
            return in.getWorld().getName() + ":" + (long)in.getX() + "," + (long)in.getY() + "," + (long)in.getZ();
        } else {
            return in.getWorld().getName() + ":" + in.getX() + "," + in.getY() + "," + in.getZ();
        }
    }
    
    public static Location parseLocation(String in) throws Exception {
        if (in == null) {
            return null;
        }
        String strDest = in.trim();
        String strName;
        String strXYZ;
        if (strDest.contains(":")) {
            strName = strDest.substring(0, strDest.indexOf(":"));
            strXYZ = strDest.substring(strDest.indexOf(":") + 1);
        } else {
            strName = strDest;
            strXYZ = null;
        }
        World worldDest = Bukkit.getWorld(strName);
        if (worldDest == null) {
            throw new Exception("World '" + strName + "' not found!");
        }
        if (strXYZ == null) {
            return worldDest.getSpawnLocation();
        } else {
            String c[] = strXYZ.split(",");
            if (c.length != 3) {
                throw new Exception("Incorrect destination!");
            }
            Double d[] = new Double[] {
                    Double.parseDouble(c[0]),
                    Double.parseDouble(c[1]),
                    Double.parseDouble(c[2])
            };
            return new Location(worldDest, d[0], d[1], d[2]);
        }
    }

    public static boolean isMiddleLocation(Location playerLocation, Location pos1, Location pos2) {
        if (!pos1.getWorld().equals(pos2.getWorld()) || !pos1.getWorld().equals(playerLocation.getWorld())) {
            return false;
        }
        double min_x = Math.min(pos1.getX(), pos2.getX());
        double min_y = Math.min(pos1.getY(), pos2.getY());
        double min_z = Math.min(pos1.getZ(), pos2.getZ());
        double max_x = Math.max(pos1.getX(), pos2.getX());
        double max_y = Math.max(pos1.getY(), pos2.getY());
        double max_z = Math.max(pos1.getZ(), pos2.getZ());
        double x = playerLocation.getX();
        double y = playerLocation.getY();
        double z = playerLocation.getZ();
        return ((min_x <= x) && (x <= max_x)) && ((min_y <= y) && (y <= max_y)) && ((min_z <= z) && (z <= max_z));
    }

    public static void broadcastMessage(String msg) {
        broadcastMessage(msg, null, 0);
    }

    public static void broadcastMessage(String msg, Location from, double radius) {
        if (msg == null) {
            return;
        }
        if ((radius <= 0) || (from == null)) {
            for(Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(msg);
            }
        } else {
            Location f;
            for(Player player : from.getWorld().getPlayers()) {
                f = player.getLocation();
                //Math.sqrt(Math.pow(from.getX() - f.getX(), 2) + Math.pow(from.getY() - f.getY(), 2) + Math.pow(from.getZ() - f.getZ(), 2));
                if (f.distance(from) <= radius) {
                    player.sendMessage(msg);
                }
            }
        }
    }
}
