package main2_2;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;


public class TextToObject {
	
	static ArrayList<String> line = new ArrayList<String>();	
	public TextToObject(String path) {
		
		File f = new File(path);
		Scanner sc;
		try {
			sc = new Scanner(f);
			int c = 0;
			
			while(sc.hasNextLine()) {
				line.add(sc.nextLine());
	            System.out.println(line.get(c));
	            c++;
			}
			
			sc.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println("copied");
		
		int row =  line.size();
		int col = line.get(0).length();
		int[][] x = new int[row][col];
		
		for(int i = 0 ; i < row; i ++ ) {
			for(int j = 0 ; j < col; j ++ ) {
				x[i][j] = Character.getNumericValue(line.get(i).charAt(j));
			}
		}

		if(f != null) {
			for(int i = 0 ; i < row; i ++ ) {
				for(int j = 0 ; j < col; j ++ ) {
					switch(x[i][j]) {
					case 1 :
						Screen.Cube.add(new Cube(j , -12, row - i, 1, 1, 1, new Color(255,0,0) ));
						break;
					case 2 :
						Screen.Cube.add(new Cube(j , -12, row - i, 1, 1, 1, new Color(98,86,26) ));
						break;
					case 3 :
						Screen.Cube.add(new Cube(j , -12, row - i, 1, 1, 1, new Color(237,148,102) ));
						break;
					}
					
				}
			}
		}	
		
	}

}