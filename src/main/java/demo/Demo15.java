package demo;

import com.google.common.base.Objects;
/**
 * 判断这个常量是否存在于常量池。
  如果存在
   判断存在内容是引用还是常量，
    如果是引用，
     返回引用地址指向堆空间对象，
    如果是常量，
     直接返回常量池常量
  如果不存在，
   将当前对象引用复制到常量池,并且返回的是当前对象的引用
 * @author 1
 *
 */
public class Demo15 {

	public static void main(String[] args) {
		String a1 = "AA";
				
		String a3 = new String("AA");    //在堆上创建对象AA
	    a3.intern(); //在常量池上创建对象AA的引用
	    String a4 = "AA"; //常量池上存在引用AA，直接返回该引用指向的堆空间对象，即a3
	    System.out.println(a3 == a4); //false,如果这个例子不理解，请看完整篇文章再回来看这里
	    System.out.println(a4 == a3.intern());
	
	}
}
