package main2_9;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

/*
* rscフォルダ内のテキストを二次元配列に変換し、
* オブジェクトとしてScreen.javaにあるList "Cube"にaddするclass
* */
class TextToObject {
	//文字列を格納するList
	ArrayList<String> line = new ArrayList<>();
	
	public TextToObject(String path) {
		
		File f = new File(path);
		Scanner sc;
		
		try {
			//ファイルの読み込み
			sc = new Scanner(f);
			//行数カウント用変数
			int c = 0;
			//１行事ファイルから文字列を抜き出す
			while(sc.hasNextLine()) {
				line.add(sc.nextLine());
	            System.out.println(line.get(c));
	            c++;
			}
			//リソース解放
			sc.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println("copied");
		//行数
		int row =  line.size();
		//列数
		int col = line.get(0).length();
		//ファイルから2次元配列に代入する為に定義
		int[][] x = new int[row][col];
		
		for(int i = 0 ; i < row; i ++ ) {
			for(int j = 0 ; j < col; j ++ ) {
				//String -> Char -> int に変換
				x[i][j] = Character.getNumericValue(line.get(i).charAt(j));
			}
		}

		if(f != null) {
			for(int i = 0 ; i < row ; i ++ ) {
				for(int j = 0 ; j < col; j ++ ) {
					//それぞれに対応した色にしてCubeを作成
					switch(x[i][j]) {
					case 1 :
						Screen.Cube.add(new Cube(j , -10, row - i , 1, 1, 1, new Color(255,0,0) ));
						break;
					case 2 :
						Screen.Cube.add(new Cube(j , -10, row - i , 1, 1, 1, new Color(98,86,26) ));
						break;
					case 3 :
						Screen.Cube.add(new Cube(j , -10, row - i , 1, 1, 1, new Color(237,148,102) ));
						break;
					}
				}
			}
		}
	}
}