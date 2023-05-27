package main3_0;

import java.awt.*;
import java.io.*;

public final class Saves {
    /**
    public static boolean isGameOver = false;
    public static boolean isGameStarted = false;
    public static boolean isGamePaused = false;
    public static boolean isGameWon = false;
    public static boolean isGameLost = false;
    public static boolean isGameRestarted = false;
    public static boolean isGameQuit = false;
    public static boolean isGameStartedFromMainMenu = false;
    public static boolean isGameStartedFromGameOver = false;
    public static boolean isGameStartedFromGameWon = false;
    public static boolean isGameStartedFromGameLost = false;
    public static boolean isGameStartedFromGameRestarted = false;
    public static boolean isGameStartedFromGameQuit = false;
    public static boolean isGameStartedFromGamePause = false;
    public static boolean isGameStartedFromGameResume = false;
    public static boolean isGameStartedFromGameRestart = false;
    public static boolean isGameStartedFromGameQuitFromGameOver = false;
    */

    private final String path;
    File file;
    Saves(String str){
        this.path = str;
    }
    @Deprecated
    public void load(){
        file = new File(path);
        FileReader fr;
        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String str = br.readLine();
            String[] data = str.split(",");
            int x = Integer.parseInt(data[1]);
            int y = Integer.parseInt(data[2]);
            int z = Integer.parseInt(data[3]);
            System.out.println(x + " " + y + " " + z);
            fr.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void write(String data ,Color c){
        file = new File(path);
        FileWriter fw;
        String nc = c.toString();
        // remove "java.awt.Color" from the string
        if(file != null){
            try {
                fw = new FileWriter(file , true);
                fw.write(data +","+ nc + "\n");
                fw.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
