package com.Bala.FCC;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Bala on 08/02/2016.
 */
public class DatabaseUtility {
    private final String userDetailsFileName = "UserDetails.txt";
    private final String userActivityFileName = "Activity.txt";
    private final String userFollowerFileName = "Followers.txt";

    public void playSong(String user, String song)
    {
        this.registerUser(user);
        this.fileWrite(user + "," + song,userActivityFileName);
    }

    public void followUser(String user, String user1)
    {
        if((this.fileRead(user, userDetailsFileName) == null) || (this.fileRead(user1, userDetailsFileName) == null))
        {
            System.out.println("Only registered users can follow others, Its impossible to hack our system.");
            System.out.println("Just play song and our 'state of the art' AI system will automatically register you.");
        }
        else
        this.fileWrite(user + "," + user1, userFollowerFileName);
    }

    public void activityOfUser(String user)
    {
        if(this.fileRead(user, userDetailsFileName) == null) {
            System.out.println(user + " is not a registered user");
            System.out.println("There is no one named " + user + ", are you sure he is not your imaginary friend ?");
            return;
        }
        System.out.printf("\n\n");
        System.out.println("Songs played by " + user + ":");
        ArrayList<String> contents = this.getAll(user, userActivityFileName);
        String songs="";
        String separator = "";
        for (String content:contents)
        {
            songs = songs + separator + content.split(",")[1];
            separator = ",";
        }
        System.out.println(songs);

        System.out.printf("\n\n");
        Map hm = this.topTenSongs(contents);
        System.out.println("The Top 10 Played Songs are:");
        int count = 0;
        for (Object key:hm.keySet())
        {
            if(count<11)
            System.out.printf("Song: %-20s  Count:%s \n",key.toString(), hm.get(key).toString());
            count++;
        }
        System.out.printf("\n\n");
        ArrayList<String> followContents = this.getAll(user, userFollowerFileName);

        String following="";
        separator = "";
        for(String fContent:followContents)
        {
            if(fContent.split(",")[0].toLowerCase().equals(user.toLowerCase()))
            {
                following = following + separator + fContent.split(",")[1];
                separator = ",";
            }
        }
        if(!following.equals(""))
        {
            System.out.println(user + " is following:");
            System.out.println(following);
        }
        else
        {
            System.out.println(user + " is a lone wolf, he doesn't follow anyone.");
        }
        System.out.printf("\n\n");

    }

    public Map topTenSongs(ArrayList songList)
    {
        HashMap<String, String> tt = new HashMap<>();
        for (Object content:songList) {
            String song = content.toString().split(",")[1];
            String count = tt.get(song.toLowerCase());
            int songCount = 0;

            if(count != null)
            songCount = Integer.parseInt(count);

            tt.put(song.toLowerCase(),(new Integer(++songCount)).toString());
        }
        return this.sortByValue(tt);
    }

    public void registerUser(String user)
    {
        String content = this.fileRead(user, userDetailsFileName);
        if(content == null) {
            this.fileWrite(user + "," + (new Date()).toString(), userDetailsFileName);
        }
    }

    private ArrayList<String> getAll(String user, String fileName)
    {
        ArrayList<String> content = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.createFileIfNotExists(fileName)));
            String line;
            while((line = br.readLine()) != null)
            {
                if(line.toLowerCase().indexOf(user.toLowerCase())>=0)
                {
                    content.add(line);
                }
            }
            br.close();
        }catch(IOException e) {
            System.out.println("FileRead: Oh no, something weird happened. Please contact our customer care.");
            e.printStackTrace();
        }
        return content;
    }


    private String fileRead(String user, String fileName)
    {
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.createFileIfNotExists(fileName)));
            String line;
            while((line = br.readLine()) != null)
            {
                String[] details = line.split(",");
                if(user.toLowerCase().equals(details[0].toLowerCase()))
                {
                    line = details[0];
                    break;
                }
            }
            br.close();
            return line;
        }catch(IOException e) {
            System.out.println("FileRead: Oh no, something weird happened. Please contact our customer care.");
            e.printStackTrace();
        }
        return null;
    }

    private void fileWrite(String content, String fileName)
    {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(this.createFileIfNotExists(fileName), true));
            bw.write(content);
            bw.newLine();
            bw.close();
        }catch(IOException e){
            System.out.println("FileWrite: Oh no, something weird happened. Please contact our customer care.");
            e.printStackTrace();
        }
    }

    private File createFileIfNotExists(String fileName)
    {
        File fl = new File(fileName);
        try {
            if (!fl.exists()) {
                fl.createNewFile();
            }
        }catch (IOException ie){ie.printStackTrace();}
        return fl;
    }
    private Map sortByValue(Map map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue())
                        .compareTo(((Map.Entry) (o1)).getValue());
            }
        });

        Map result = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry)it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
