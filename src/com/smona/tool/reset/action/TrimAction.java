package com.smona.tool.reset.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Properties;

import com.smona.tool.reset.util.Util;

public class TrimAction implements IAction {

	private HashMap<String, String> SCREENDENY = new HashMap<String, String>();

	@Override
	public void execute(String source) {
		initData();
		trim(source);
	}

	private void initData() {
		SCREENDENY.put("xhdpi", "xhdpi(720x1280)");
		SCREENDENY.put("xxhdpi", "xxhdpi(1080X1920)");
		SCREENDENY.put("xxxhdpi", "xxxhdpi(1440x2560)");
	}

	private void trim(String path) {
		String trimPath = path + Util.DIR_SPLIT + Util.CLASSIFICATION;
		File trim = new File(trimPath);
		File[] files = trim.listFiles();
		if (files == null) {
			Util.printDetail(trim + " is empty!");
			return;
		}

		for (File file : files) {
			if (!file.isDirectory()) {
				continue;
			}
			trim(file);
		}
	}

	private void trim(File file) {
		if (file.isFile()) {
			trimFile(file);
			return;
		}
		File[] childFiles = file.listFiles();
		for (File child : childFiles) {
			if (child.isDirectory()) {
				trim(child);
			} else {
				trimFile(child);
			}
		}
	}

	private void trimFile(File file) {
		if (Util.language_properties.equalsIgnoreCase(file.getName())) {
			modifyScreenDensity(file);
			modifyCnTitle(file);
			modifyAuthor(file);
		}
	}

	private void modifyScreenDensity(File file) {
		String value = getPropValue(file, "screen_density");
		if (value != null) {
			String screen = SCREENDENY.get(value);
			if (screen != null) {
				updateProps(file, "screen_density", screen);
			}
		}
	}

	private void modifyCnTitle(File file) {
		String lang = getPropValue(file, "zh_CN");
		if (lang != null) {
			String str = asciitocn(lang);
			updateProps(file, "zh_CN", str);
		}
	}

	private void modifyAuthor(File file) {
		String author = getPropValue(file, "author");
		if (author != null) {
			String str = asciitocn(author);
			updateProps(file, "author", str);
		}
	}

	public static String asciitocn(String unicodeStr) {
		if (unicodeStr == null) {
			return null;
		}
		StringBuffer retBuf = new StringBuffer();
		int maxLoop = unicodeStr.length();
		for (int i = 0; i < maxLoop; i++) {
			if (unicodeStr.charAt(i) == '\\') {
				if ((i < maxLoop - 5)
						&& ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
								.charAt(i + 1) == 'U')))
					try {
						retBuf.append((char) Integer.parseInt(
								unicodeStr.substring(i + 2, i + 6), 16));
						i += 5;
					} catch (NumberFormatException localNumberFormatException) {
						retBuf.append(unicodeStr.charAt(i));
					}
				else {
					retBuf.append(unicodeStr.charAt(i));
				}
			} else {
				retBuf.append(unicodeStr.charAt(i));
			}
		}
		return retBuf.toString();
	}

	public static String cntoascii(String ascii) {
		char[] ca = ascii.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ca.length; i++) {
			sb.append("\\u" + Integer.toString(ca[i], 16));
		}
		return sb.toString();
	}

	private String getPropValue(File file, String key) {

		Properties props = new Properties();
		try {
			props.load(new InputStreamReader(new FileInputStream(file), "utf-8"));
			return props.getProperty(key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void updateProps(File file, String key, String value) {
		updateProps(file, key, value, "utf-8");
	}

	private void updateProps(File file, String key, String value, String encode) {
		Properties props = new Properties();
		try {
			props.load(new InputStreamReader(new FileInputStream(file), encode));
			OutputStream fos = new FileOutputStream(file);
			props.setProperty(key, value);
			props.store(new OutputStreamWriter(fos, encode), null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
