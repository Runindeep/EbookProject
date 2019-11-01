package com.match;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * 本类是对电子书文件进行词汇、例句分析操作的工具类。
 * 
 * @author Alice Cui
 */
public class EbookProcess {

	// 设properties配置文件为私有域
	private Properties prop;

	public EbookProcess(Properties prop) {
		this.prop = prop;
	}

	public Properties getProp() {
		return prop;
	}

	/**
	 * 本方法从配置文件中读取目标电子书路径，并生成电子书全部文本用"【】"连接的String。
	 * 
	 * @return 电子书全部文本用"【】"连接的String
	 */
	public String ebookPrepare() {
		FileReader fr = null;
		BufferedReader br = null;
		StringBuilder ebookAll = new StringBuilder();
		try {
			String str = "";
			fr = new FileReader(prop.getProperty("EBOOK_PATH"));
			br = new BufferedReader(fr);

			while ((str = br.readLine()) != null) {
				ebookAll.append(str + "【】");
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
		return new String(ebookAll);
	}

	/**
	 * 此方法用于提取英文电子书中的全部句子，并返回全部句子Set。
	 * 
	 * @return 电子书的全部句子Set
	 */
	public Set<String> toSentenceSet() {
		// 读取全书得到一个字符串
		String ebookAll = ebookPrepare();
		// 全书字符串转化为全部句子Array
		ebookAll = ebookAll.replaceAll("(\\.|\\?|!|\"|…|”|’)\\s+([A-Z]|‘|“)", "$1【】$2");
		String[] sentenceArray = ebookAll.split("【】");
		// 筛选出符合要求的句子加入例句Set（自动去重）
		Set<String> sentenceSet = new LinkedHashSet<>();
		for (String s : sentenceArray) {
			if (s.length() > 3) {
				sentenceSet.add(s);
			}
		}
		System.out.println(prop.getProperty("EBOOK_NAME") + "中有" + sentenceSet.size() + "个例句。");
		return sentenceSet;
	}

	/**
	 * 此方法用于提取英文电子书中的全部生词，并返回全部生词List。
	 * 
	 * @return 电子书的全部生词List
	 */
	public List<String> toNewWordList(MyReference myReference) {
		// 读取全书得到一个字符串
		String ebookAll = ebookPrepare();
		// 全书字符串转化为全部单词Array
		String[] wordArray = ebookAll.split("\\W");
		// 筛选出符合要求的单词加入全部单词Set（自动去重）
		Set<String> wordSet = new LinkedHashSet<>();
		for (String w : wordArray) {
			if (w.matches("[0-9]+")) {// 除去数字
				continue;
			} else if (w.length() > 2) {// 除去长度小于等于2的单词
				String wl = w.toLowerCase();// 转化为小写
				wordSet.add(wl);
			}
		}
		// 从参考文件中读入已掌握词汇Set
		Set<String> myWordSet = myReference.vocabPrepare();
		// 全部词汇Set - 已掌握词汇Set = 生词Set
		wordSet.removeAll(myWordSet);
		System.out.println(prop.getProperty("EBOOK_NAME") + "中有" + wordSet.size() + "个生词。");		
		// Set转化为List
		return new LinkedList<>(wordSet);
	}
}
