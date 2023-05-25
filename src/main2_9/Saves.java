package main2_9;

import java.awt.*;
import java.io.*;

/**
 * 生成・削除したオブジェクト情報を記録するクラス
 * @version 2.10
 * @serial
 */
public final class Saves {
    private int x;
    private int y;
    private int z;
    private int d;
    private Color color;
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

    private File file;
    Saves(String str){
        this.file = new File(str);
    }

    //TODO
    public void load(){
        FileReader fr = null;
        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String str = br.readLine();
            String[] data = str.split(",");
            x = Integer.parseInt(data[1]);
            y = Integer.parseInt(data[2]);
            z = Integer.parseInt(data[3]);
            System.out.println(x + " " + y + " " + z);
            fr.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void write(String data ,Color c){
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
