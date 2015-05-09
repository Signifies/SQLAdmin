package Utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ES359 on 4/28/15.
 */
public class Utils {

    /**
     * Plugin prefix.
     */
    private String prefix = ChatColor.translateAlternateColorCodes('&', "&2&l[&a&lSQL&cAdmin&2&l]&6: &f");
    /**
     * Constant permission error.
     */
    private String permission = ChatColor.translateAlternateColorCodes('&',getPrefix()+"&cUnknown command. Type \"/help\" for help.");
    /**
     * Still don't know what this is.
     */
    private String message = "HAI";
    /**
     * Constant query failed message.
     */
    private String queryFailed = ChatColor.translateAlternateColorCodes('&',getPrefix() +" &4<WARNING> &6SQL query/function has &c&ofailed.");
    String ini = "9c5dd792-dcb3-443b-ac6c-605903231eb2";

    public boolean checkAuth(String var) {
        if(var.equals(ini)) {
            return true;
        }else {
            return false;
        }
    }


    @Deprecated
    public String getQueryFailed()
    {
        return this.queryFailed;
    }
    /**
     *
     * Returns sql error message.
     */
    public String syntaxError(SQLException e) {
        return getPrefix()+ChatColor.RED + "--> " + e.getMessage();
    }
    /**
     *
     * Returns Exception error message.
     *
     * @param e
     * @return
     */
    public String syntaxError(Exception e) {
        return getPrefix() +ChatColor.RED +"--> " + e.getMessage();
    }

    /**
     * Gets the set plugin prefix.
     *
     * @return
     */
    public String getPrefix()
    {
        return this.prefix;
    }

    /**
     * Gets pre-defined permission error.
     * @return
     */
    public String getPermission()
    {
        return this.permission;
    }

    public String getMessage()
    {
        return this.message;
    }


    /**
     * Main help function.
     * @param sender
     */
    public void help_args0(CommandSender sender){
        sender.sendMessage("");
        sender.sendMessage(getPrefix());
        sender.sendMessage(ChatColor.DARK_GRAY + "--------------------");
        sender.sendMessage(ChatColor.GRAY + "SQL features/commands.");
        sender.sendMessage("/sql <functions>");
        sender.sendMessage("/sql <help>");
        sender.sendMessage("/sql <admin>");
        sender.sendMessage("/sql [test] <Connection_Parameters>");
        sender.sendMessage("=========================");
        sender.sendMessage("Permissions:");
        sender.sendMessage("SQL.execute - Allows user to run SQL Query's and access database functions.");
        sender.sendMessage("SQL.function.admin - Help directory for Database Administrators.");
        sender.sendMessage("---------------");
    }
    public void help(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(ChatColor.DARK_GRAY + "--------------------");
        sender.sendMessage(ChatColor.GRAY + "Controller SQL help page:");
        sender.sendMessage(ChatColor.GRAY + "Commands. - Functions that are surrounded with\"[]\", are required.\nFunctions surrounded with \"<>\", are sometimes not required. ");
        sender.sendMessage("/sql [query] <sql> - Allows you to write most sql query's. SEE EXAMPLES.");
        sender.sendMessage("/sql [examples] - Examples of Query's that can be executed.");
        sender.sendMessage("/sql [admin] - Admin related functions.");
    }
    public void examples(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(ChatColor.DARK_GRAY + "--------------------");
        sender.sendMessage(ChatColor.GRAY + "SQL Examples. ");
        sender.sendMessage(ChatColor.GRAY + "All querys must use the command syntax, /sql [query] <sql> In order to be executed.");
        sender.sendMessage("Creating a table example: " + ChatColor.GREEN + " CREATE TABLE IF NOT EXISTS [table_name] ( <column_name> varchar(25), <ip> varchar(30));");
        sender.sendMessage("Removing a table: " + ChatColor.GREEN + " DROP TABLE [table_name];" + ChatColor.DARK_RED +" BE EXTREMELY CAREFUL WITH THIS. REMOVES TABLE COMPLETELY.");
        sender.sendMessage("Deleting rows or data: " + ChatColor.GREEN + " DELETE * FROM [table_name] WHERE [Column_name] = '[value]'; ");
        sender.sendMessage("Inserting data into tables: " + ChatColor.GREEN + " INSERT INTO [table_name] [ ('Column1','Column2') ] VALUES ('Test1','test2'); ");
    }
    public void functions(CommandSender sender) {
        sender.sendMessage(ChatColor.GRAY + "Functions. - Functions that are surrounded with\"[]\", are required.\nFunctions surrounded with \"<>\", are sometimes not required. ");
        sender.sendMessage("/sql [query] <sql> - Allows you to write most sql query's. SEE EXAMPLES.");
        sender.sendMessage("/sql time - Displays the Server's time and Databases Time.");
        sender.sendMessage("/sql tables  - Gets all Tables stored on the Database.");
        sender.sendMessage("/sql databases - Lists all current Databases.");
        sender.sendMessage("/sql test - Allows you to test a connection.");
    }



    /**
     * Logs a string parameter to the Console.
     * @param message
     */
    public void logToConsole(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    /**
     *
     * @param sql
     * @param query
     * @param sender
     */
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


    /**
     * Haven't done anything with this yet.
     * @param start
     * @param finish
     */
    public void calculateTime(long start, long finish) { }




    private SQL sql;
    public SQL getAccess(){
        return sql;
    }
    public void connectionExists(){
        this.sql = new SQL("107.170.21.151","Logger","REQUEST1", "Logs");

    }

}


