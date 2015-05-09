package Utilities;

import me.ES359.SQLAdmin.SQLAdmin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ES359 on 4/28/15.
 */
public class Utils {

    private String prefix = ChatColor.translateAlternateColorCodes('&', "&2&l[&a&lSQL&cAdmin&2&l]&6: &f");
    private String permission = ChatColor.translateAlternateColorCodes('&',getPrefix()+"&cUnknown command. Type \"/help\" for help.");
    private String message = "HAI";
    private String queryFailed = ChatColor.translateAlternateColorCodes('&',getPrefix() +" &4<WARNING> &6SQL query/function has &c&ofailed.");
    private String author = "9c5dd792-dcb3-443b-ac6c-605903231eb2";

    private Plugin plugin;
    private FileConfiguration config;
   private File file;

    public String getQueryFailed()
    {
        return this.queryFailed;
    }

    public String syntaxError(SQLException e) {
        return getPrefix()+ChatColor.RED + "--> " + e.getMessage();
    }
    public String syntaxError(Exception e) {
        return getPrefix() +ChatColor.RED +"--> " + e.getMessage();
    }

    public String getPrefix()
    {
        return this.prefix;
    }

    public String getPermission()
    {
        return this.permission;
    }

    public String getMessage()
    {
        return this.message;
    }

    public void configSetup(Plugin p){
        config = p.getConfig();
        config.options().copyDefaults(true);
        file = new File(p.getDataFolder(), "config.yml");
       // p.saveConfig();
        saveConfig();
      }


    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(file);
        }catch(IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&oERROR &b&l> &6&oCould not save the &eConfig file, &c&oPlease check for errors!"));;
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
    }


    public void logToConsole(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }


    public void runQuery(SQL sql, String query,CommandSender sender) {
        /**
         * Implement checking system to test connection.
         */
        try {
            PreparedStatement statement = sql.getConnection().prepareStatement(query);
            statement.execute();
            statement.close();
            sender.sendMessage(getPrefix() +ChatColor.RED + "Executed Query: [" + ChatColor.YELLOW + query + ChatColor.RED + "]");
            logToConsole("&fUSER, " + sender.getName() + " &fExecuted the query: " + query);
        } catch (SQLException e) {
            sender.sendMessage(syntaxError(e));
           // e.printStackTrace();
            //sender.sendMessage(ChatColor.RED + "QUERY FAILED - CHECK CONSOLE FOR STACKTRACE.");
        }
    }

    /**
     *
     * Gets the Mysql connection values from the configuration file.
     *
     */

    public boolean check(Plugin instance) {

     boolean value = instance.getConfig().getBoolean("Database.Enabled");
        return value;
    }

    public void returnConnectionValues(Plugin plugin, CommandSender sender) {
        String host =""+ plugin.getConfig().get("Database.host");
        String  user = ""+plugin.getConfig().get("Database.username");
        String pass = ""+plugin.getConfig().get("Database.password");
        String db = ""+plugin.getConfig().get("Database.database");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"" +
                "&c--------------------\n" +
                "&cDatabase connection parameters:\n" +
                "&6Database &aEnabled: &c" +check(plugin)+" \n"+
                "&6Host: &b&l" +host+"\n" +
                "&6user: &b&l"+user+"\n" +
                "&6pass: &b&l" +pass+"\n" +
                "&6database: &b&l"+db+"\n"));
    }

    public void getTime(SQL sql, CommandSender sender) {

        try {
            Statement s = sql.getConnection().createStatement();

            String query = "SELECT CURRENT_TIMESTAMP ;";

            ResultSet set = s.executeQuery(query);

            set.first();

            String result = set.getString(1);
            s.close();

            sender.sendMessage(ChatColor.GOLD + "Database Time:" +ChatColor.GREEN+result);


        }catch (SQLException e) {
           sender.sendMessage(syntaxError(e));
        }

    }

    public void getDatabases(SQL sql, CommandSender sender) {
        try {
            Statement s = sql.getConnection().createStatement();

            String query = "SHOW DATABASES; ";

            ResultSet set = s.executeQuery(query);

            set.first();
            sender.sendMessage(getPrefix() +ChatColor.GREEN + "Current databases...");
            while(set.next()) {
                String res = set.getString(1);
                sender.sendMessage(ChatColor.GOLD + res);
            }
        }catch (SQLException e) {
            sender.sendMessage(syntaxError(e));
        }
    }

    public void getTables(SQL sql, CommandSender sender) {

        try {
            Statement s = sql.getConnection().createStatement();

            String query= "SHOW TABLES; ";

            ResultSet set = s.executeQuery(query);
            set.first();
            sender.sendMessage(getPrefix() + ChatColor.GOLD + "Current tables...");
            while(set.next()) {
                String result = set.getString(1);

                sender.sendMessage(ChatColor.GREEN + result);
            }
        }catch (SQLException e){
            sender.sendMessage(syntaxError(e));
        }
    }

    @Deprecated
    public void createNewStatement(SQL sql, String query, CommandSender sender) {
        try {

            Statement s = sql.getConnection().createStatement();

            //Get query from method accessor
            ResultSet set = s.executeQuery(query);

            set.first();
            while(set.next()) {
               // set.setFetchSize(0);
                //String result = ChatColor.AQUA+set.getString(1) +ChatColor.RESET+"  "+ set.getString(2) +"  :"+ ChatColor.GOLD +"  " + set.getString(3);

                String result = set.getString(1);  //+ " " +set.getString(2) + " " +set.getString(3);
                sender.sendMessage(ChatColor.GRAY + result);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Deprecated
    public void devCreateNewStatement(SQL sql, String query, CommandSender sender) {

        try {
            Statement s = sql.getConnection().createStatement();



        }catch (SQLException e) {
         e.printStackTrace();
        }

    }



    public void calculateTime(long start, long finish) {



    }




    private SQL sql;
    public SQL getAccess(){
        return sql;
    }
    public void connectionExists(){
        this.sql = new SQL("107.170.21.151","Logger","REQUEST1", "Logs");

    }

}


