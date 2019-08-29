package demo;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class Demo10 {

	public static void main(String[] args) {
		System.out.println(new String(new byte[]{0x6a, 0x75, 0x6c, 0x79}));
		System.out.println( new String(new byte[]{0x64, 0x6f, 0x6e, 0x67, 0x67, 0x75, 0x61}));
       
		System.out.println(Stream.of(1, 2, 3).reduce(4, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer + integer2;
            }
        }
        , new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer * integer2;
            }
        }));
	}
}
