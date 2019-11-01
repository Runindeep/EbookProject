package com.match;

import java.util.*;
import java.util.regex.*;
import com.bean.*;

/**
 * 本类用于对电子书的单个生单词进行词义、例句匹配操作。
 * 
 * @author Alice Cui
 */
public class WordMatch {

	// 设WordBean类对象为私有域
	private WordBean wordBean;

	public WordMatch(WordBean wordBean) {
		this.wordBean = wordBean;
	}

	public WordBean getWordBean() {
		return wordBean;
	}

	/**
	 * 此方法用于在柯林斯字典中查阅英文电子书的生词，并将词义设置到WordBean的meaningInCollions域。
	 * 
	 * @param dictMap 柯林斯字典Map
	 */
	public boolean matchWordMeaning(Map<String, String> dictMap) {
		if (!(dictMap.get(wordBean.newWord) == null)) {
			wordBean.setMeaningInCollions(dictMap.get(wordBean.newWord));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 此方法用于为生词匹配英文电子书中的原版例句，并将例句Array设置到WordBean的wordInSentenceArray域。
	 * 
	 * @param sentenceSet 电子书的例句Set
	 */
	public void matchWordSentence(Set<String> sentenceSet) {
		Pattern wordWrap = Pattern.compile("\\b" + wordBean.newWord + "\\b", Pattern.CASE_INSENSITIVE);
		StringBuilder sentenceBuilder = new StringBuilder();
		for (String sentence : sentenceSet) {
			Matcher matcher = wordWrap.matcher(sentence);
			if (matcher.find()) {
				sentence = matcher.replaceAll("<font color=\"red\">" + wordBean.newWord + "</font>");
				sentenceBuilder.append(sentence + "】");
			}
		}
		String sentenceAll = new String(sentenceBuilder);
		String[] sentenceArray = sentenceAll.split("】");
		wordBean.setWordInSentenceArray(sentenceArray);
	}
}
