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
		
		
/*----------支店定義ファイル読み込み-----------*/
		try{
    		File file = new File(args[0], "branch.lst");
	    	FileReader filereader = new FileReader(file);
	        BufferedReader br = new BufferedReader(filereader);
	    	String s = br.readLine();
	    	while(s != null){
	    		String[] str = s.split(",");
	    		branchName.put(str[0], str[1]);
	    		System.out.println(branchName.get(str[0]));
	    		s = br.readLine();
	    	}
	        br.close();
		}catch(FileNotFoundException e){
			System.out.println("支店定義ファイルが存在しません");
		}catch(NumberFormatException e){
			System.out.println("支店定義ファイルのフォーマットが不正です");
		}catch(IOException e) {
			System.out.println("予期せぬエラーが発生しました");
		}
		
		
/*------------売上ファイル読み込み--------*/
		ArrayList<String> fileName = new ArrayList<String>();
		
		File dir = new File(args[0]);
		File[] files = dir.listFiles();
		for(int i = 0; i < files.length; i++){
			File item = files[i];
    		if(item.getName().matches("^\\d{8}\\.rcd$")){
    			fileName.add(files[i].getName());
    			
    		}
		}
		
		
		//if文で、連番になっているかのチェック
		
		
		
		
		for(int i = 0; i < fileName.size(); i++){
			System.out.println(fileName.get(i));
			//int sum = 0 ;
			try{
		        File file = new File(args[0], fileName.get(i));
    	        FileReader filereader = new FileReader(file);
                BufferedReader br = new BufferedReader(filereader);
    	        String str = br.readLine();
    	        String[] arraySales = new String[2];
    	        int n = 0;
    	        while(str != null){
    	        	System.out.println(str);
    	        	arraySales[n] = (str);
    	    		str = br.readLine();
    	    		n++;
    	    	}
    	        br.close();
    	        
    	        if(branchSales.containsKey(arraySales[0])){
    	        	int sum = Integer.parseInt(arraySales[1] + branchSales.get(arraySales[0]));
    	        	branchSales.put(arraySales[0], Integer.toString(sum));
    	        }else{
    	            branchSales.put(arraySales[0], arraySales[1]);
    	        }
			} catch (FileNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		
		System.out.println(branchSales.get("005"));
	}
}
