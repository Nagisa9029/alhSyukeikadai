package jp.alhinc.nakamura_takuto.calculate_sales;

import java.util.regex.Pattern;

public class www {
	public static void main(String[] args) {
	//String str = "javatest@example.com" ;
	//String pattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$" ;
	String str = "javatest53425256あ26" ;
	String pattern = "^[\\w]+$" ;


	Pattern p = Pattern.compile(pattern);
	if(p.matcher(str).find()){
	    System.out.println(str + "はメールアドレスの形式です");
	}else{
	    System.out.println(str + "はメールアドレスの形式ではありません");
	}

}
}