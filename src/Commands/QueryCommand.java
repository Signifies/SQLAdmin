package Commands;

import Utilities.TestConnection;
import Utilities.Timestamp;
import Utilities.Utils;
import me.ES359.SQLAdmin.SQLAdmin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
/*
 * Created by ES359 on 4/28/15.
 */
public class QueryCommand implements CommandExecutor{

    public QueryCommand(SQLAdmin instance) {
        this.main = instance;
    }



    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[]) {


        if(cmd.getName().equalsIgnoreCase("sql")) {
            if (!sender.hasPermission("SQL.execute")) {
                sender.sendMessage(ChatColor.YELLOW + "You don't have permission " + sender.getName() + ".");
            } else {

                if (args.length < 1) {
                    sender.sendMessage("");
                    sender.sendMessage(util.getPrefix());
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
                    //sender.sendMessage("Options -/sql <query> <sql>/sql <data> <sql>");
                } else {

                    if (args[0].equalsIgnoreCase("query")) {

                        if(main.isSqlEnabled()) {
                            if (args.length == 1) {
                                sender.sendMessage("/sql <query> <sql> {All sql related query's can be executed. This function is a work in progress.}");
                            } else {
                                if (args.length > 1) {

                                    StringBuilder str = new StringBuilder();

                                    for (String arg : args) {
                                        str.append(arg + " ");
                                    }
                                    String query = str.toString().replaceAll("query", "");
                                    //util.runQuery(main.getSQL(), query,sender);
                                    util.runQuery(this.main.getSQL(),query,sender);
                                    //util.createNewStatement(this.main.getSQL(),query,sender);
                                }
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("help")) {
                        sender.sendMessage("");
                        sender.sendMessage(ChatColor.DARK_GRAY + "--------------------");
                        sender.sendMessage(ChatColor.GRAY + "Controller SQL help page:");
                        sender.sendMessage(ChatColor.GRAY + "Commands. - Functions that are surrounded with\"[]\", are required.\nFunctions surrounded with \"<>\", are sometimes not required. ");
                        sender.sendMessage("/sql [query] <sql> - Allows you to write most sql query's. SEE EXAMPLES.");
                        //sender.sendMessage("/sql [function] - Due to Bukkits limited visual interface, some SQL features aren't available. They will be hard coded.");
                        sender.sendMessage("/sql [examples] - Examples of Query's that can be executed.");
                        sender.sendMessage("/sql [admin] - Admin related functions.");
                        return true;
                    } else if (args[0].equalsIgnoreCase("examples")) {
                        sender.sendMessage("");
                        sender.sendMessage(ChatColor.DARK_GRAY + "--------------------");
                        sender.sendMessage(ChatColor.GRAY + "SQL Examples. ");
                       // sender.sendMessage(ChatColor.GRAY + "Since minecraft visuals are limited, the only sql functions that will really make sense to use are database functions.");
                        sender.sendMessage(ChatColor.GRAY + "All querys must use the command syntax, /sql [query] <sql> In order to be executed.");
                        //sender.sendMessage("Adding ID tags to tables:" + ChatColor.GREEN + " ALTER TABLE [table_name] ADD id INT PRIMARY KEY AUTO_INCREMENT FIRST;");
                        sender.sendMessage("Creating a table example: " + ChatColor.GREEN + " CREATE TABLE IF NOT EXISTS [table_name] ( <column_name> varchar(25), <ip> varchar(30));");
                        sender.sendMessage("Removing a table: " + ChatColor.GREEN + " DROP TABLE [table_name];" + ChatColor.DARK_RED +" BE EXTREMELY CAREFUL WITH THIS. REMOVES TABLE COMPLETELY.");
                        sender.sendMessage("Deleting rows or data: " + ChatColor.GREEN + " DELETE * FROM [table_name] WHERE [Column_name] = '[value]'; ");
                        sender.sendMessage("Inserting data into tables: " + ChatColor.GREEN + " INSERT INTO [table_name] [ ('Column1','Column2') ] VALUES ('Test1','test2'); ");
                    } else if (args[0].equalsIgnoreCase("admin")) {
                        if (!sender.hasPermission("SQL.function.admin")) {
                            sender.sendMessage(ChatColor.YELLOW + "This section requires the rank " + ChatColor.RED + "Administrator or Above.");
                        } else {
                            sender.sendMessage("");
                            sender.sendMessage(ChatColor.DARK_GRAY + "--------------------");
                            sender.sendMessage(ChatColor.RED + "Administration Controls/options.");
                            /**
                             * Implement a method that gets the current connection parameters.
                             * And displays them to the user.
                             */

                            util.returnConnectionValues(main,sender);


                        }
                    } else if (args[0].equalsIgnoreCase("test")) {
                        //String ip, String userName, String access, String db

                        String params[] = new String[10];

                        if (args.length == 1) {
                            sender.sendMessage("/sql [test] connection [<host> <username> <password> <database>] - Test an SQL Connection.");
                        } else if (args.length  > 1) {
                                params[0] = args[2];
                                params[1] = args[3];
                                params[2] = args[4];
                                params[3] = args[5];
                                test = new TestConnection(params[0], params[1], params[2], params[3], sender);
                        }
                    } else if (args[0].equalsIgnoreCase("time")) {

                        if(args.length > 1)
                        {
                            sender.sendMessage("/sql time");
                        }else {
                            if(main.isSqlEnabled()) {

                                /**
                                 * Implement Server timestamp function soon.
                                 */
                                sender.sendMessage(util.getPrefix());
                                sender.sendMessage(ChatColor.GOLD +"Server Timestamp: " +ChatColor.GREEN+ stamp.getStamp());
                                util.getTime(main.getSQL(),sender);
                               // util.getTime(main.getSQL(),sender);
                            }else if(this.main.isSqlEnabled() == false) {
                                sender.sendMessage(util.getPrefix() +ChatColor.GOLD +"Server Timestamp: " +ChatColor.GREEN+ stamp.getStamp());
                            }
                        }
                    }else if(args[0].equalsIgnoreCase("tables"))
                    {
                        if(args.length > 1) {
                            sender.sendMessage("/sql tables");
                        }else {
                            if(this.main.isSqlEnabled()) {
                                util.getTables(this.main.getSQL(),sender);
                            }
                        }
                    }else if(args[0].equalsIgnoreCase("databases")) {

                       if(this.main.isSqlEnabled()) {
                           //Implement database checker.
                           util.getDatabases(this.main.getSQL(),sender);
                       }
                    }else if(args[0].equalsIgnoreCase("functions")) {
                        sender.sendMessage(ChatColor.GRAY + "Functions. - Functions that are surrounded with\"[]\", are required.\nFunctions surrounded with \"<>\", are sometimes not required. ");
                        sender.sendMessage("/sql [query] <sql> - Allows you to write most sql query's. SEE EXAMPLES.");
                        sender.sendMessage("/sql time - Displays the Server's time and Databases Time.");
                        sender.sendMessage("/sql tables  - Gets all Tables stored on the Database.");
                        sender.sendMessage("/sql databases - Lists all current Databases.");
                        sender.sendMessage("/sql test - Allows you to test a connection.");

                    }else {
                        sender.sendMessage(util.getPrefix() +ChatColor.RED+ "--> " + "Incorrect Arguments. /sql help");
                    }
                }
            }
        }
        return true;
    }

    TestConnection test;

    private SQLAdmin main;
    private Timestamp stamp = new Timestamp();
    private Utils util = new Utils();

}
