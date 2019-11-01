package com.bean;

import java.util.Arrays;

public class WordBean {

	//设置生词newWord为公有域，但不设setter和getter方法，保证newWord一经初始化便不再变动
	public String newWord;
	//单词的词义域
	private String meaningInCollions;
	//单词的例句域
	private String[] wordInSentenceArray;

	public String[] getWordInSentenceArray() {
		return wordInSentenceArray;
	}

	public void setWordInSentenceArray(String[] wordInSentenceArray) {
		this.wordInSentenceArray = wordInSentenceArray;
	}

	public String getMeaningInCollions() {
		return meaningInCollions;
	}

	public void setMeaningInCollions(String meaningInCollions) {
		this.meaningInCollions = meaningInCollions;
	}

	/**
	 * 带一个参数的构造器。
	 * @param newWord 生单词字符串
	 */
	public WordBean(String newWord) {
		this.newWord = newWord;
	}

	@Override
	public String toString() {
		return "OutputBean [生词=" + newWord + ", 词义=" + meaningInCollions + ", 例句="
				+ Arrays.toString(wordInSentenceArray) + "]";
	}
	
	/**
	 * 本方法提供单词的【生词-例句-词义】的HTML输出字符串。
	 * @return 单词的【生词-例句-词义】的HTML输出String
	 */
	public String output() {
		StringBuilder sb = new StringBuilder();
		sb.append("<div><h3>-  " + newWord + "  -</h3>" + "<div style=\"display:block;border:2px solid salmon;pad:15px;\">");
		sb.append("<ul>");
		for (int i = 0; i < wordInSentenceArray.length; i++) {
			sb.append("<li>" + wordInSentenceArray[i] + "</li>");
		}
		sb.append("</ul>");
		sb.append("<hr><div style=\"margin:15px;\">" + meaningInCollions + "</div></div></div>");		
		String s = new String(sb);
		return s;
	}
}
