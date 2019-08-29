package demo;

public class Demo7 {

	public static void main(String[] args) {
		Long par = 30273L;
		Integer in = 30273;
		System.out.println(par.longValue() == in.longValue());
		System.out.println(par.intValue() == in.intValue());
		System.out.println(par.longValue() == in.intValue());
		
		System.out.println(Integer.valueOf("01"));
		
		
		String bbb = ",  ,   ,";
		String[] split = bbb.split(",");
		System.out.println(split.length);
	}
}
