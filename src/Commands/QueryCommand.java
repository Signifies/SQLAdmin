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
                   util.help_args0(sender);
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
                        util.help(sender);
                        return true;
                    } else if (args[0].equalsIgnoreCase("examples")) {
                       util.examples(sender);
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
                               try {
                                   params[0] = args[2];
                                   params[1] = args[3];
                                   params[2] = args[4];
                                   params[3] = args[5];
                                   test = new TestConnection(params[0], params[1], params[2], params[3], sender);
                               }catch (Exception e) {
                                   sender.sendMessage(util.syntaxError(e));
                               }
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
                        util.functions(sender);
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
