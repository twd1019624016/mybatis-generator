package demo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Demo5 {
	{
		System.out.println("1111111111111111");
	}
	
	static {
		System.out.println("111133333333111111111111");
	}
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		System.out.println(!"iiiii".equals("iiiii"));
		new Demo5();
		
		HashMap<Integer, String> map = new HashMap<>();
		map.put(new Integer(8888888), "ttt");
		System.out.println(map.get(new Integer(8888888)));
		
		Map map1 = new HashMap(4);
	/*	this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity);*/
		Field declaredField = map1.getClass().getDeclaredField("table");
		declaredField.setAccessible(true);
		System.out.println(((Object[])declaredField.get(map1)).length);
		map1.put("212", "212");
		map1.put("212333", "21200");
		System.out.println(declaredField.get(map1));
	}
}
