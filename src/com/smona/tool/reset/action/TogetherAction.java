package com.smona.tool.reset.action;

import java.io.File;

import com.smona.tool.reset.util.FileOperator;
import com.smona.tool.reset.util.Util;

public class TogetherAction implements IAction {

	@Override
	public void execute(String source) {
		createDir(source + Util.DIR_SPLIT + Util.TOGETHER);
		move(source);
	}

	private void move(String path) {
		String tempResPath = path + Util.DIR_SPLIT + Util.UNZIP;
		String targetPath = path + Util.DIR_SPLIT + Util.TOGETHER;

		Util.printDetail("move themeResPath: " + tempResPath);
		Util.printDetail("move path: " + path);

		String targetFilePath = null;

		File rootFile = new File(tempResPath);
		File[] files = rootFile.listFiles();
		for (File file : files) {
			if (!file.isDirectory()) {
				continue;
			}

			targetFilePath = targetPath + Util.DIR_SPLIT + file.getName();
			createDir(targetFilePath);

			int interceptPathLength = file.getPath().length();
			move(file, interceptPathLength, targetFilePath);
		}
	}

	private void move(File srcPath, int interceptPathLength, String desPath) {
		File[] themeChilds = srcPath.listFiles();
		for (File themeChild : themeChilds) {
			if (themeChild.isFile()) {
				String themePathName = themeChild.getPath();
				String preSuffix = themePathName
						.substring(interceptPathLength + 1);

				FileOperator.fileChannelCopy(
						themeChild,
						desPath
								+ Util.DIR_SPLIT
								+ preSuffix.replace(Util.DIR_SPLIT,
										Util.REPLACE_PATH_SIGN));

			} else if (themeChild.isDirectory()) {
				move(themeChild, interceptPathLength, desPath);
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
