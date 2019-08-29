package demo;

import java.math.BigDecimal;

public class Demo11 {

	public static void main(String[] args) {
		System.out.println(divide(1,6,4));
	}
	
	public static double divide (int v1, int v2, int scale) {

        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
