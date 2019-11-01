package com;

/**
 * 英文电子书生词查阅主程序
 * 
 * @author Alice Cui
 * @version 5.0
 */
public class Main {

	public static void main(String[] args) {

		// 1. Jar包运行用代码
		// 读取配置文件输出电子书的生词查询结果
//		new EbookToOutput().outputSentenceMeaning(args[0]);
		
		// 2. Eclipse内测试用代码
		new EbookToOutput().outputSentenceMeaning("D:\\ebookProject5.0\\ebook.properties");
	}
}
