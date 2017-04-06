package com.smona.tool.reset.action;

import java.io.File;
import java.util.zip.ZipException;

import com.smona.tool.reset.util.FileOperator;
import com.smona.tool.reset.util.Util;
import com.smona.tool.reset.util.ZipFileAction;

public class UnzipAction implements IAction {

	@Override
	public void execute(String source) {
		createDir(source + Util.DIR_SPLIT + Util.UNZIP);
		unzip(source);
	}

	private void unzip(String path) {
		unzips(path);
	}

	private void unzips(String path) {
		String themeResPath = path + Util.DIR_SPLIT + Util.SOURCE;
		String themeTemp = path + Util.DIR_SPLIT + Util.UNZIP;

		Util.printDetail("listTheme themeResPath: " + themeResPath);
		Util.printDetail("listTheme path: " + path);

		File rootFile = new File(themeResPath);
		File[] files = rootFile.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				continue;
			}

			if (file.isFile() && file.getName().endsWith(".gnz")) {
				try {
					ZipFileAction action = new ZipFileAction();
					action.unZip(file.getPath(), themeTemp + Util.DIR_SPLIT
							+ file.getName());
				} catch (ZipException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void createDir(String path) {
		FileOperator.deleteDirectory(new File(path));
		mkdirs(path);
	}

	private static File mkdirs(String target) {
		File tempDir = new File(target);
		tempDir.mkdirs();
		return tempDir;
	}

}
