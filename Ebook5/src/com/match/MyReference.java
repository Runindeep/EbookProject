package com.match;

import java.io.*;
import java.util.*;

/**
 * 本类用于读取配置文件，从中读取文本生成对应Map/Set。
 *  1.科林斯字典 - dictMap 
 *  2.已掌握单词 - myVocabSet
 * 
 * @author Alice Cui
 */
public class MyReference {

	//设properties配置文件为私有域
	private Properties prop;

	public MyReference(Properties prop) {
		this.prop = prop;
	}

	public Properties getProp() {
		return prop;
	}
	
	/**
	 * 本方法从配置文件中读取柯林斯字典路径，并生成字典Map。
	 * 
	 * @return 柯林斯字典Map
	 */
	public Map<String, String> dictPrepare() {
		FileReader fr = null;
		BufferedReader br = null;
		Map<String, String> dictMap = new LinkedHashMap<>();
		try {
			String str = "";
			fr = new FileReader(prop.getProperty("COLLIONS"));
			br = new BufferedReader(fr);

			while ((str = br.readLine()) != null) {
				if (str.contains("\t")) {
					String[] dictItem = str.split("\\t", 2);
					dictMap.put(dictItem[0], dictItem[1]);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("找不到指定文件");
		} catch (IOException e) {
			System.out.println("读取文件失败");
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				System.out.println("关闭文件失败");
			}
		}
		return dictMap;
	}

	/**
	 * 本方法从配置文件中读取已掌握词汇路径，并生成已掌握词汇Set。
	 * 
	 * @return 已掌握词汇Set
	 */
	public Set<String> vocabPrepare() {
		FileReader fr = null;
		BufferedReader br = null;
		Set<String> myVocabSet = new LinkedHashSet<>();
		try {
			String str = "";
			fr = new FileReader(prop.getProperty("MY_VOCAB"));
			br = new BufferedReader(fr);
			while ((str = br.readLine()) != null) {
				myVocabSet.add(str);
			}
		} catch (FileNotFoundException e) {
			System.out.println("找不到指定文件");
		} catch (IOException e) {
			System.out.println("读取文件失败");
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				System.out.println("关闭文件失败");
			}
		}
		return myVocabSet;
	}

	
}
