package demo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Demo6 {

	public static void main(String[] args) throws UnsupportedEncodingException {
//		System.out.println(String.format("%08d", 1234567));
//		Son1 son1 = new Son1();
//		Son2 son2 = new Son2();
//		
//		son1.print();
//		son2.print();
//		Boolean fff = null;
//		if(fff) {
//			
//		}
	    
		Super sup = new Super();
		sup.setName("super");
		
		Son1 convertToSon1 = sup.convertToSon1();
		convertToSon1.setOther(100);
		
	}
	
	/*
     * unicode编码转中文
     */
    public static String decodeUnicode(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }
    
    public static String decodeUnicode2(String unicode) {     
        StringBuffer sb = new StringBuffer();
          
        String[] hex = unicode.split("\\\\u");
      
        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i], 16);
            sb.append((char) data);
        }
        return sb.toString();  
     }


}
