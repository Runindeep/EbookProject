package com.properties;

import java.io.*;
import java.util.Properties;

/**
 * 本类是读取properties文件的工具类。
 * 
 * @author Alice Cui
 */
public class EbookProperties {
	
	/**
	 * 本方法用于读取properties文件。
	 * @param ebookProperties 配置文件路径字符串
	 * @return Properties类对象，用于获取properties文件中的信息
	 */
	public static Properties readProperties(String ebookProperties) {
		Properties prop = new Properties();
		FileReader fr = null;
		try {
			fr = new FileReader(ebookProperties);
			prop.load(fr);
		} catch (FileNotFoundException e) {
			System.out.println("找不到指定文件");
		} catch (IOException e) {
			System.out.println("读取文件失败");
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				System.out.println("关闭文件失败");
			}
		}
		return prop;
	}	
}
