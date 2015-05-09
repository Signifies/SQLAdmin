package me.ES359.SQLAdmin;

import Commands.QueryCommand;
import Utilities.Debug;
import Utilities.SQL;
import Utilities.Updater;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by ES359 on 4/28/15.
 */
public class SQLAdmin extends JavaPlugin {

    public boolean isSqlEnabled()
    {
        return this.sqlEnabled;
    }

    public SQL getSQL() {
        return this.sql;
    }

    String id = "IP: "+Bukkit.getServer().getIp();
    public void onEnable()


    {
        System.out.println(id);
        debug.setDebugStatus(true);
        this.getConfig().options().copyDefaults(true);
        saveConfig();
        this.getCommand("sql").setExecutor(new QueryCommand(this));
        Bukkit.getServer().getPluginManager().registerEvents(new Updater(),this);
        if(isSqlEnabled()) {
            this.sql = new SQL(getConfig().getString("Database.host"), getConfig().getString("Database.username"), getConfig().getString("Database.password"), getConfig().getString("Database.database"));
            return;
        }


        //this.util.configSetup(this);

    }

    private SQL sql;
    private Debug debug = new Debug();
    //private Utils util = new Utils();
    private boolean sqlEnabled = getConfig().getBoolean("Database.Enabled");
}
