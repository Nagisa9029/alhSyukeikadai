package jp.alhinc.nakamura_takuto.calculate_sales;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CalculateSales {
	
	public static void main(String[] args) {
		//支店コード,支店名
		HashMap<String, String> branchName = new HashMap<>();
		//支店コード,売り上げ
		HashMap<String, Long> branchSales = new HashMap<>();
		
		
		//コマンドライン引数が1つではない場合
		if (args.length != 1){
			System.out.println("予期せぬエラーが発生しました");
		}else{
/*------------------------支店定義ファイル読み込み--------------------*/
			File file = new File(args[0], "branch.lst");
			BufferedReader br = null;
			
			
			if(!file.exists()){      //支店定義ファイルが存在しない場合
				System.out.println("支店定義ファイルが存在しません");
			}else{
				try{
					FileReader filereader = new FileReader(file);
					br = new BufferedReader(filereader);
					String str = br.readLine();
					while(str != null){
						String[] shop = str.split(",");
						
						//支店定義ファイルのフォーマットが正しい場合
						if(shop.length == 2 && shop[0].matches("^\\d{3}$")){
							branchName.put(shop[0], shop[1]);
							str = br.readLine();
						}else{  //支店定義ファイルのフォーマットが不正な場合
							System.out.println("支店定義ファイルのフォーマットが不正です");
							break;
						}
					}
				}catch(IOException e) {
					System.out.println("予期せぬエラーが発生しました");
				}finally{
					try {
						br.close();
					}catch (IOException e) {
						System.out.println("予期せぬエラーが発生しました");
					}
				}
			}
			
			
/*---------------------売上ファイル読み込み--------------------*/
			ArrayList<String> fileName = new ArrayList<String>();
			ArrayList<String> fileNumber = new ArrayList<String>(); 
			
			File dir = new File(args[0]);
			File[] files = dir.listFiles();
			for(int i = 0; i < files.length; i++){
				File item = files[i];
				
			//売上ファイルのみ選別
				if(item.getName().matches("^\\d{8}\\.rcd$")){
					fileName.add(files[i].getName());
					
				//連番チェック用リスト抽出
					String[] fileNum = files[i].getName().split("\\.");
					fileNumber.add(fileNum[0]);
				}
			}
			
		//if文で、連番になっていない場合
			for(int i = 0; i < fileNumber.size() - 1; i ++){
				int n = Integer.parseInt(fileNumber.get(i));
				int m = Integer.parseInt(fileNumber.get(i + 1));
				
				if(m-n != 1){
					System.out.println("売上ファイル名が連番になっていません");
					break;
				}
			}
			
/*---------------------集計処理------------------------*/
			for(int i = 0; i < fileName.size(); i++){
				File fileSum = new File(args[0], fileName.get(i));
			//BufferedReader br = null;
				ArrayList<String> salesArray = new ArrayList<String>();
				
				try{
					FileReader filereader = new FileReader(fileSum);
					br = new BufferedReader(filereader);
					String s = br.readLine();
					while(s != null){
						salesArray.add(s);
						s = br.readLine();
					}
					
					
				//売上ファイルの行数が2行ではない場合
					if(salesArray.size() != 2){
						System.out.println(fileName.get(i) + "のフォーマットが不正です");
						break;
					}
				//売上ファイルの支店コードが、支店定義ファイルに存在しない場合
					else if(!branchName.containsKey(salesArray.get(0))){
						System.out.println(fileName.get(i) + "の支店コードが不正です");
						break;
					}
				//売上ファイルの売上金額が数字以外（不正）な場合
					else if(!salesArray.get(1).matches("^[0-9]{1,10}$")){
						System.out.println("予期せぬエラーが発生しました");
						break;
						}
				//売上HashMapにキーが存在していれば、加算してUoDate.。なければ新規登録
					else{
						if(branchSales.containsKey(salesArray.get(0))){
							Long sum = Long.parseLong(salesArray.get(1)) + branchSales.get(salesArray.get(0));
							branchSales.put(salesArray.get(0), sum);
							
						//集計売上金額が１０桁を超えた場合
							if(String.valueOf(sum).length() > 10){
								System.out.println("合計金額が10桁を超えました");
								break;
							}
						}else{
							branchSales.put(salesArray.get(0), Long.parseLong(salesArray.get(1)));
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {
						br.close();
					} catch (IOException e) {
						System.out.println("予期せぬエラーが発生しました");
					}
				}
			}
			
			
/*---------------------出力処理------------------------*/
			File fileUp = new File(args[0], "branch.out");
			BufferedWriter bw = null;
			try {
				FileWriter fw = new FileWriter(fileUp);
				bw = new BufferedWriter(fw);
				
			//branch.outファイルへ出力
				for(Map.Entry<String, String> entry : branchName.entrySet()) {
					bw.write(entry.getKey() + "," + entry.getValue() + "," + branchSales.get(entry.getKey()) + '\n');
				}
				
			} catch (IOException e) {
				System.out.println("予期せぬエラーが発生しました");
			}finally{
				try {
					bw.close();
				} catch (IOException e) {
					System.out.println("予期せぬエラーが発生しました");
				}
			}
		}
	}
}
