package com.Bala.FCC;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bala on 09/02/2016.
 */
public class CommandParser {

    public static Map parseCommand(String command)
    {
        Map<String, String> commandDetails = new HashMap<>();
        String[] details = command.split(" ");
        int le = details.length;
        commandDetails.put("command","");
        if(details.length>1) {
            if (details[1].toLowerCase().equals("play")) {
                if (details.length > 2) {
                    commandDetails.put("command", "play");
                    commandDetails.put("user", details[0]);
                    int startIndex = command.indexOf("play") + "play ".length();
                    int ln = command.length();
                    commandDetails.put("song", command.substring(startIndex, command.length()));
                }
            } else if (details[1].toLowerCase().equals("follow")) {
                if (details.length > 2) {
                    commandDetails.put("command", "follow");
                    commandDetails.put("user1", details[0]);
                    commandDetails.put("user2", details[2]);
                }
            } else if (details[1].toLowerCase().equals("activity")) {
                    commandDetails.put("command", "activity");
                    commandDetails.put("user1", details[0]);
            }
        }
        return commandDetails;
    }
}
