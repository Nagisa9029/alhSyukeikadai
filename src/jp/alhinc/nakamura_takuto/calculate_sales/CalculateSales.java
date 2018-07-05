package jp.alhinc.nakamura_takuto.calculate_sales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class CalculateSales {
	public static void main(String[] args) {
		File file = new File(args[0], "branch.lst");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		while((line = br.readLine()) != null) {
			
		    System.out.println(line);
		}
		br.close();
	}
}
