package com.output;

import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

import com.bean.WordBean;

/**
 * 本类用于将目标输出结果写入到文件。
 * 
 * @author Alice Cui
 */
public class OutputToFile {

	// 设properties配置文件为私有域
	private Properties prop;

	public Properties getProp() {
		return prop;
	}

	public OutputToFile(Properties prop) {
		this.prop = prop;
	}

	/**
	 * 此方法用于将英文电子书中生词相应的原版例句和其在柯林斯词典中查到的词义输出到HTML文件中，以供查阅。
	 * 
	 * @param wordBeans 【生词-例句-词义】WordBean类的List
	 */
	public void outputWordBeans(List<WordBean> wordBeans) {

		// 输出文件名的时间戳
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd_HH-mm-ss");
		String nowStamp = formatter.format(now);

		// 输出文件设置
		String outputFolder = prop.getProperty("OUTPUT_FOLDER") + prop.getProperty("EBOOK_NAME");
		File path = new File(outputFolder);
		if (!path.exists()) {
			path.mkdirs();
		}
		String outputPath = prop.getProperty("OUTPUT_FOLDER") + prop.getProperty("EBOOK_NAME") + "\\"
				+ prop.getProperty("EBOOK_NAME") + "_" + nowStamp + ".html";

		// 输出
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(outputPath);
			bw = new BufferedWriter(fw);
			for (WordBean wordBean : wordBeans) {
				bw.write(wordBean.output());
				bw.flush();
			}
		} catch (IOException e) {
			System.out.println("写入文件失败！");
		} catch (NullPointerException e) {
			System.out.println("输入集合为空！");
		} finally {
			try {
				bw.close();
				fw.close();
				System.out.println("已将" + prop.getProperty("EBOOK_NAME") + "的生词-例句-词义文件输出到文件：" + outputPath);
			} catch (IOException e) {
				System.out.println("保存文件失败！");
			}
		}
	}

	/**
	 * 此方法用于将任意Collection<String>内容输出到TXT文件中，以供查阅。
	 * 
	 * @param someCollection 任意Collection<String>
	 * @param name    指定someCollection的名称
	 */
	public void OutputCollection(Collection<String> someCollection, String name) {

		// 输出文件名的时间戳
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd_HH-mm-ss");
		String nowStamp = formatter.format(now);

		// 输出文件设置
		String outputFolder = prop.getProperty("OUTPUT_FOLDER") + prop.getProperty("EBOOK_NAME");
		File path = new File(outputFolder);
		if (!path.exists()) {
			path.mkdirs();
		}
		String outputPath = prop.getProperty("OUTPUT_FOLDER") + prop.getProperty("EBOOK_NAME") + "\\"
				+ prop.getProperty("EBOOK_NAME") + "_" + name + "_" + nowStamp + ".txt";

		// 输出
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(outputPath);
			bw = new BufferedWriter(fw);
			for (String s : someCollection) {
				bw.write(s + "\n");
				bw.flush();
			}
		} catch (IOException e) {
			System.out.println("写入文件失败！");
		} finally {
			try {
				bw.close();
				fw.close();
				System.out.println("已将指定Set输出到文件：" + outputPath);
			} catch (IOException e) {
				System.out.println("保存文件失败！");
			}
		}
	}
}
