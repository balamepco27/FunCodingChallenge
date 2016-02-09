package com.Bala.FCC;


import java.io.IOException;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Bala on 08/02/2016.
 */

public class Main {

    public static void main(String[] args)throws IOException {
        printCommands();
        while(true) {
            System.out.println("Enter:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String data = reader.readLine();
            if(data.equals("Q"))System.exit(0);
            Map<String, String> details = CommandParser.parseCommand(data);
            DatabaseUtility DB = new DatabaseUtility();

            switch (details.get("command")) {
                case "play":
                    DB.playSong(details.get("user"), details.get("song"));
                    break;
                case "follow":
                    DB.followUser(details.get("user1"), details.get("user2"));
                    break;
                case "activity":
                    DB.activityOfUser(details.get("user1"));
                    break;
                default:
                    System.out.println("I can understand only the language of Music");
                    printCommands();
            }
        }
    }

    public static void printCommands()
    {
        System.out.println("Please enter any of the following commands:");
        System.out.println("<UserName> play <SongName>");
        System.out.println("<UserName> follow <UserName>");
        System.out.println("<UserName> activity");
        System.out.println("");
    }
}
