package Utilities;
/**
 * Functions as a debug option for your code.
 * Instead of commenting out or removing your debug statements,
 * you can use a method to control if they are to be displayed or disabled.
 */

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by ES359 on 1/12/15.
 */
public class Debug {

    /**
     * Datamember variable holds a boolean value.
     */
    private boolean control = false;

    /**
     * Warning method's prefix.
     */
    private String prefix_warning = "[&4WARNING&f]: ";
    /**
     * Info methods prefix.
     */
    private String prefix_info = "[&4INFO&f]: ";

    /**
     * Checks to see the status of debugging. Is it enabled or disabled?
     *
     * @return debugging status.
     */
    private boolean status() {
        return control;
    }

    /**
     * Informs the operator/user the status of debugging.
     */
    public void getStatus(Player player) {
        if(status() == false) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED +"Debugging is currently disabled.");
            player.sendMessage(ChatColor.RED +"Debugging is currently disabled.");
            //System.out.print("Debugging is currently disabled.");
        }else {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Debugging is enabled! All debug statements\\nwritten in code will now appear!");
            player.sendMessage(ChatColor.GREEN + "Debugging is enabled! All debug statements\\nwritten in code will now appear!");
            // System.out.println("Debugging is enabled! All debug statements\nwritten in code will now appear!");
        }
    }

    /**
     *
     * @param value Takes a boolean value, that sets debugging enabled or disabled.
     */
    public void setDebugStatus(boolean value)
    {
        control = value;
    }

    /**
     * Warning message.
     *
     * @param msg Takes a string parameter.
     */
    public void warning(String msg) {
        if(status()) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',prefix_warning+msg));
            //System.out.println(prefix_warning+msg); //Using bukkit API. Commenting out System.out.print.
        }
    }

    /**
     * Info console logger.
     * @param msg
     */
    public void info(String msg) {
        if(status()) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',prefix_info+msg));
            //System.out.println(prefix_warning+msg); //Using bukkit API. Commenting out System.out.print.
        }
    }

    public void info(Player p, String msg) {
        if(status()) {
            p.sendMessage(prefix_info + msg);
        }
    }

    public void warning(Player p, String msg) {
        if(status()) {
            p.sendMessage(prefix_warning+msg);
        }
    }
}