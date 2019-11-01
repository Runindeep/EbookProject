package com;

import java.io.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;

import com.bean.WordBean;
import com.match.*;
import com.output.*;
import com.properties.*;

/**
 * 本类是读取配置文件，输出电子书生词查阅结果的过程类。 如果想要完成其他目的，可以在本类中编写新方法。
 * 
 * @author Alice Cui
 */
public class EbookToOutput {

	/**
	 * 本方法是读取配置文件，输出电子书生词查阅结果的过程方法，供Main类的main方法调用。
	 * 
	 * @param ebookProperties 配置文件路径字符串
	 */
	public void outputSentenceMeaning(String ebookProperties) {

		System.out.println("===========程序运行开始：===========");

		// =============================================
		// 1. 读取配置文件prop，新建依赖prop的类的对象
		Properties prop = EbookProperties.readProperties(ebookProperties);
		MyReference myReference = new MyReference(prop);
		EbookProcess ep = new EbookProcess(prop);
		OutputToFile outputToFile = new OutputToFile(prop);
		System.out.print("【读取配置文件ebook.properties】完毕！");
		System.out.println("\n---------------------------------");

		// =============================================
		// 2. 获取柯林斯字典Map、电子书的生词Set和例句Set
		Instant start = Instant.now();

		Map<String, String> dictMap = myReference.dictPrepare();
		List<String> wordList = ep.toNewWordList(myReference);
		Set<String> sentenceSet = ep.toSentenceSet();
		System.out.print("【获取柯林斯字典Map、电子书的生词Set和例句Set】完毕！");

		Instant end = Instant.now();
		Duration timeSpent = Duration.between(start, end);
		long nanos = timeSpent.toNanos();
		System.out.printf("花费了%.3f秒。", (float) nanos / 1000000000);
		System.out.println("\n---------------------------------");

		// =============================================
		// 3. 生词与词义、例句匹配
		start = Instant.now();

		// 创建一个通用池
		ForkJoinPool pool = ForkJoinPool.commonPool();
		// 提交可分解的WordMatchTask任务
		WordMatchTask wmt= new WordMatchTask(wordList,0,wordList.size(), dictMap, sentenceSet);
		Future<List<WordBean>> future =pool.submit(wmt);
		try {//确保任务执行完毕
			pool.awaitTermination(2, TimeUnit.MINUTES);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		List<WordBean> wordBeans = null;
		try {//获取执行结果
			wordBeans = future.get();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			e1.printStackTrace();
		}		
		// 关闭线程池
		pool.shutdown();
		System.out.println("书中共有"+wordBeans.size()+"个生单词可在柯林斯字典中查到词义。");
		System.out.print("【生词-例句-词义匹配】完毕！");

		end = Instant.now();
		timeSpent = Duration.between(start, end);
		nanos = timeSpent.toNanos();
		System.out.printf("花费了%.3f秒。", (float) nanos / 1000000000);
		System.out.println("\n---------------------------------");

		// =============================================
		// 4. 与例句和词义有对应的生词WordBean输出至指定的HTML文件中
		start = Instant.now();
		
		outputToFile.outputWordBeans(wordBeans);
		System.out.print("【生词-例句-词义输出至指定HTML文件】完毕！");

		end = Instant.now();
		timeSpent = Duration.between(start, end);
		nanos = timeSpent.toNanos();
		System.out.printf("花费了%.3f秒。", (float) nanos / 1000000000);
		System.out.println("\n---------------------------------");

		// =============================================
		// 此处请选择是否输出未在字典中查到的生词Set
		System.out.println("请选择是否输出未在字典中查到的生词Set：y/n?");
		System.out.print(">>>");
		char choice;
		try {
			choice = (char) System.in.read();
			switch (choice) {
			case 'y': {
				// 5. 将未在柯林斯字典中查到的生词Set输出至指定的TXT文件中				
				Set<String> wordSet = new HashSet<>();
				//从wordBeans中把可查到的生词
				for (WordBean wordBean:wordBeans) {
					wordSet.add(wordBean.newWord);					
				}
				wordList.removeAll(wordSet);
				outputToFile.OutputCollection(wordList, "未查到的生词Set");
				System.out.println("【未查到的生词Set输出至指定TXT文件】完毕！");
				break;
			}
			case 'n': {
				break;
			}
			default: {
				System.out.println("输入错误，未能输出未在字典中查到的生词Set！");
			}
			}
		} catch (IOException e) {
			System.out.println("读取用户输入失败！");
		} finally {
			System.out.println("===========程序运行完毕！===========");
		}

	}

}
