package demo;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class Demo3 {

	public static void main(String[] args) throws IOException {
		URL resource2 = ClassLoader.getSystemClassLoader().getResource("mbn");
		
		System.out.println(resource2);
		System.out.println("---------------------");
Enumeration<URL> resources = ClassLoader.getSystemClassLoader().getResources("mbn");
		if(resources.hasMoreElements()) {
			System.out.println(resources.nextElement());
		}
	}
}
