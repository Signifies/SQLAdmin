package Utilities;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by ES359 on 4/28/15.
 */
public class TestConnection {

    public TestConnection(String ip, String userName, String access, String db, CommandSender user) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            /*
      ADDITIONS. ADD A TEST CONNECTION COMMAND. CREATE A COMMAND TO ADD A NEW CONNECTION. ETC.
     */
            Connection c = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + db + "?user=" + userName + "&password=" + access);
            user.sendMessage("Connection to database parameters: " + ChatColor.AQUA +"[ "+ip + ", " + userName +", " + access +", " + db + " ] Was successful!");
        } catch (Exception e) {
            e.printStackTrace();

            user.sendMessage(util.syntaxError(e));
        }
    }

    Utils util = new Utils();

}
