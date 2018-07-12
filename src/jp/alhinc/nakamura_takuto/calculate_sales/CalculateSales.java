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
		//コマンドライン引数が1つではない場合
		if (args.length != 1){
			System.out.println("予期せぬエラーが発生しました");
			return;
		}
		//支店コード,支店名
		HashMap<String, String> branchName = new HashMap<>();
		//支店コード,売り上げ
		HashMap<String, Long> branchSales = new HashMap<>();

/*------------------------支店定義ファイル読み込み--------------------*/
		if(!input(args[0], "branch.lst", "支店", "^\\d{3}$", branchName, branchSales)){
			return;
		}

/*---------------------売上ファイル読み込み--------------------*/
		ArrayList<String> rcdName = new ArrayList<String>();
		ArrayList<String> rcdNumber = new ArrayList<String>();

		File dir = new File(args[0]);
		File[] files = dir.listFiles();
		BufferedReader br = null;
		for(int i = 0; i < files.length; i++){
			File item = files[i];

			//売上ファイル名の選別チェック
			if(item.isFile() && item.getName().matches("^\\d{8}\\.rcd$")){
				rcdName.add(files[i].getName());
				
				//連番チェック用リスト抽出
				String[] num = files[i].getName().split("\\.");
				rcdNumber.add(num[0]);
			}
		}

		//売上ファイル連番チェック
		for(int i = 0; i < rcdNumber.size() - 1; i ++){
			int n = Integer.parseInt(rcdNumber.get(i));
			int m = Integer.parseInt(rcdNumber.get(i + 1));

			if(m-n != 1){
				System.out.println("売上ファイル名が連番になっていません");
				return;
			}
		}

/*---------------------集計処理------------------------*/
		for(int i = 0; i < rcdName.size(); i++){
			long sum = 0;
			
			File fileSum = new File(args[0], rcdName.get(i));
			ArrayList<String> salesArray = new ArrayList<String>();

			try{
				FileReader fr = new FileReader(fileSum);
				br = new BufferedReader(fr);
				String str;
				while((str = br.readLine()) != null){
					salesArray.add(str);
				}
				
				//売上ファイルの行数が2行ではない場合
				if(salesArray.size() != 2){
					System.out.println(rcdName.get(i) + "のフォーマットが不正です");
					return;
				}
				//売上ファイルの支店コードが、支店定義ファイルに存在しない場合
				if(!branchName.containsKey(salesArray.get(0))){
					System.out.println(rcdName.get(i) + "の支店コードが不正です");
					return;
				}
				//売上ファイルの売上金額が数字以外（不正）な場合
				if(!salesArray.get(1).matches("^[0-9]{1,10}$")){
					System.out.println("予期せぬエラーが発生しました");
					return;
				}
				//売上HashMapを支店コード（key）で呼び出し、加算
				sum = Long.parseLong(salesArray.get(1)) + branchSales.get(salesArray.get(0));
				
				//集計売上金額が１０桁を超えた場合
				if(String.valueOf(sum).length() > 10){
					System.out.println("合計金額が10桁を超えました");
					return;
				}
				branchSales.put(salesArray.get(0), sum);
				
			} catch (IOException e) {
				System.out.println("予期せぬエラーが発生しました");
				return;
			}finally{
				try {
					br.close();
				} catch (IOException e) {
					System.out.println("予期せぬエラーが発生しました");
					return;
				}
			}
		}


/*---------------------出力処理------------------------*/
		if(!output(args[0], "branch.out", branchName, branchSales)){
			return;
		}
	}

/*--------------入力メソッド(支店定義ファイル-------------------------*/

	public static boolean input(String path, String fileName, String bra, String promise, HashMap<String, String>listName, HashMap<String, Long>listSales){
		File file = new File(path, fileName);
		BufferedReader br = null;

		//支店定義ファイルが存在しない場合
		if(!file.exists()){
			System.out.println(bra + "定義ファイルが存在しません");
			return false;
		}
		try{
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			String s;
			while((s = br.readLine()) != null){
				String[] str = s.split(",");

				//支店定義ファイルのフォーマットが不正な場合
				if(!str[0].matches(promise) || str.length != 2){
					System.out.println(bra + "定義ファイルのフォーマットが不正です");
					return false;
				}
				listName.put(str[0], str[1]);
				listSales.put(str[0], 0L);
			}
		}catch(IOException e) {
			System.out.println("予期せぬエラーが発生しました");
			return false;
		}finally{
			try {
				if(br != null){
					br.close();
				}
			}catch (IOException e) {
				System.out.println("予期せぬエラーが発生しました");
				return false;
			}
		}
		return true;
	}


/*---------------------出力メソッド-----------------------------------*/
	public static boolean output(String path, String fileName, HashMap<String, String>listName, HashMap<String, Long>listSales){
		File fileUp = new File(path, fileName);
		BufferedWriter bw = null;
		try {
			FileWriter fw = new FileWriter(fileUp);
			bw = new BufferedWriter(fw);
			
			//branch.outファイルへ出力
			for(Map.Entry<String, String> entry : listName.entrySet()) {
				bw.write(entry.getKey() + "," + entry.getValue() + "," + listSales.get(entry.getKey()));
				bw.newLine();
			}

		} catch (IOException e) {
			System.out.println("予期せぬエラーが発生しました");
			return false;
		}finally{
			try {
				if(bw != null){
					bw.close();
				}
			} catch (IOException e) {
				System.out.println("予期せぬエラーが発生しました");
				return false;
			}
		}
		return true;
	}
}
