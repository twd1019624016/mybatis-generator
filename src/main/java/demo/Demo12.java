package demo;

import java.math.BigInteger;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;

public class Demo12 {

	public static void main(String[] args) {
		System.out.println("000000000000");
		System.out.println(Strings.isNullOrEmpty(""));
		
		System.out.println(Strings.isNullOrEmpty("      "));
		System.out.println(StringUtils.isNoneBlank("      "));
		ArrayList cc = new ArrayList<>();
		cc.stream().map(item->item.toString());
	
		int pageNum=1073742;
		int pageSize=2000;
		int result = 1073742 * 2000;
		
		System.out.println(result);
		
		System.out.println(Integer.MAX_VALUE);
		System.out.println(Long.MIN_VALUE);
		
		String number = "2923813194129238131941";
		boolean numeric = StringUtils.isNumeric(number);
		
		BigInteger MAX_VALUE = new BigInteger("2147483647");
		BigInteger big= MAX_VALUE;
		
		StringBuilder stringBuilder = new StringBuilder(Integer.MAX_VALUE-2);
		for(int i=0;i<Integer.MAX_VALUE;i++) {
			stringBuilder.append("1");
			if(i == Integer.MAX_VALUE/2) {
				System.out.println("2");
				System.out.println(new BigInteger(stringBuilder.toString()).toString().length());
			}
			
		}
	/*	
		int i=0;
		for(i=0;i<65075262;i++) {
			
			big = big.multiply(big);
			System.out.println(i);
			//System.out.println(big);
			System.out.println(big.toString().length());
		}
		System.out.println(numeric);
		BigInteger bigInteger = new BigInteger(number);
		
		System.out.println(Long.parseLong(number));
		*/
		
	}
}
