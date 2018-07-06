package jp.alhinc.nakamura_takuto.calculate_sales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class CalculateSales {
	
	public static void main(String[] args) {
		//支店コード,支店名
		HashMap<String, String> branchName = new HashMap<>();
		//支店コード,売り上げ
		HashMap<String, String> branchSales = new HashMap<>();
		
		
		/*売上ファイル読み込み*/
		ArrayList<String> salesFile = new ArrayList<String>();
		
		File dir = new File(args[0]);
		File[] files = dir.listFiles();
		for(int i = 0; i < files.length; i++){
    		System.out.println(files);
		}

		
		/*支店定義ファイル読み込み*/
		try{
    		File file = new File(args[0], "branch.lst");
	    	FileReader filereader = new FileReader(file);
	        BufferedReader br = new BufferedReader(filereader);
	    	
	    	String str = br.readLine();
	    	while(str != null){
	    		System.out.println(str);
	    		str = br.readLine();
	    	}
	        br.close();
		}catch(FileNotFoundException e){
			System.out.println("支店定義ファイルが存在しません");
		}catch(NumberFormatException e){
			System.out.println("支店定義ファイルのフォーマットが不正です");
		}catch(IOException e) {
			System.out.println(e);
		}
		
		
        

	}
}
