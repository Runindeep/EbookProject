package com.match;

import java.util.concurrent.*;
import com.bean.WordBean;
import java.util.*;

/**
 * 本类为继承RecursiveTask的多线程类，主要用于对电子书中的生单词Set匹配词义和例句，得到相应的Bean集合。
 * 
 * @author Alice
 *
 */
@SuppressWarnings("serial")
public class WordMatchTask extends RecursiveTask<List<WordBean>> {

	// 每个“小任务”只最多只处理300个
	private static final int THRESHOLD = 300;
	// 生词List
	private List<String> wordList;
	// 生词List的start index
	private int start;
	// 生词List的end index
	private int end;
	// 柯林斯字典Map
	private Map<String, String> dictMap;
	// 例句Set
	private Set<String> sentenceSet;
	
	public WordMatchTask(List<String> wordList, int start, int end, Map<String, String> dictMap,
			Set<String> sentenceSet) {
		this.wordList = wordList;
		this.start = start;
		this.end = end;
		this.dictMap = dictMap;
		this.sentenceSet = sentenceSet;
	}	

	@Override
	protected List<WordBean> compute() {		
		if (end - start < THRESHOLD) {// 当end与start之间的差小于THRESHOLD时，开始进行查询操作
			List<WordBean> wordBeans = new LinkedList<>();
			for (int i = start; i < end; i++) {
				WordBean wordBean = new WordBean(wordList.get(i));
				WordMatch wordMatch = new WordMatch(wordBean);
				boolean flag = wordMatch.matchWordMeaning(dictMap);
				if (flag) {
					wordMatch.matchWordSentence(sentenceSet);
//					System.out.println(wordMatch.getWordBean());
					wordBeans.add(wordMatch.getWordBean());
				}
			}
			return wordBeans;
		} else {
			// 如果当end与start之间的差大于THRESHOLD时将大任务分解成两个小任务。
			int middle = (start + end) / 2;
			WordMatchTask left = new WordMatchTask(wordList, start, middle, dictMap, sentenceSet);
			WordMatchTask right = new WordMatchTask(wordList, middle, end, dictMap, sentenceSet);
			// 并行执行两个“小任务”
			left.fork();
			right.fork();
			
			List<WordBean> wordBeans = left.join();
			wordBeans.addAll(right.join());
			return wordBeans;
		}
	}
}
