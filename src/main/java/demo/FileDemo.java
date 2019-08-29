package demo;

import java.io.File;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;

public class FileDemo {

	public static void main(String[] args) {
		File file = FileUtil.file("C:\\Users\\1\\Desktop\\微信图片_20190805164434.jpg");
		String type = FileTypeUtil.getType(file);
		//输出 jpg则说明确实为jpg文件
		Console.log(type);
	}
}
