package com.smona.tool.reset.action;

import java.io.File;
import java.io.IOException;

import com.smona.tool.reset.util.FileOperator;
import com.smona.tool.reset.util.Util;
import com.smona.tool.reset.util.ZipFileAction;

public class ClassificationAction implements IAction {

	@Override
	public void execute(String source) {
		createDir(source + Util.DIR_SPLIT + Util.CLASSIFICATION);
		compose(source);
	}

	private void compose(String path) {
		String moveResPath = path + Util.DIR_SPLIT + Util.TOGETHER;
		String composePath = path + Util.DIR_SPLIT + Util.CLASSIFICATION;

		File move = new File(moveResPath);
		File[] files = move.listFiles();
		if (files == null) {
			Util.printDetail(moveResPath + " is empty!");
			return;
		}

		for (File file : files) {
			if (!file.isDirectory()) {
				continue;
			}
			String targetPath = composePath + Util.DIR_SPLIT + file.getName();
			createDir(targetPath);
			compose(file, targetPath);
		}

	}

	private void compose(File parent, String targetPath) {
		String iconResPath = targetPath + Util.DIR_SPLIT + Util.Icon
				+ Util.DIR_SPLIT;
		createDir(iconResPath);
		createDir(targetPath + Util.DIR_SPLIT + Util.Attachment);

		String fileName;
		File[] files = parent.listFiles();
		for (File file : files) {
			if (!file.isFile()) {
				continue;
			}
			fileName = file.getName().replace(Util.REPLACE_PATH_SIGN,
					Util.DIR_SPLIT);
			if (!isHasDir(fileName)) {
				if (fileName.equalsIgnoreCase(Util.language_properties)
						|| fileName.equalsIgnoreCase(Util.since_properties)) {
					FileOperator.fileChannelCopy(file, targetPath
							+ Util.DIR_SPLIT + fileName);
				}
			} else if (isDrawableArray(fileName)) {
				int lastIndex = fileName.lastIndexOf(Util.DIR_SPLIT);

				String desDir = targetPath + Util.DIR_SPLIT + Util.DIR_SPLIT
						+ Util.drawable_array + Util.DIR_SPLIT;
				try {
					ZipFileAction.copyFileWithDir(
							file.getAbsolutePath(),
							desDir,
							Util.Lock
									+ Util.Color_JING
									+ fileName.substring(lastIndex + 1,
											fileName.length()));
				} catch (IOException e) {
					Util.printDetail(e.toString());
					e.printStackTrace();
				}
			} else if (isSpecialFile(fileName)) {
				int lastIndex = fileName.lastIndexOf(Util.DIR_SPLIT);
				String desDir = targetPath + Util.DIR_SPLIT
						+ fileName.substring(0, lastIndex);
				desDir = desDir.replace(Util.DIR_SPLIT + Util.res, "");
				try {
					ZipFileAction.copyFileWithDir(file.getAbsolutePath(),
							desDir,
							fileName.substring(lastIndex, fileName.length()));
				} catch (IOException e) {
					Util.printDetail(e.toString());
					e.printStackTrace();
				}
			} else if (isOtherFile(fileName)) {
				int lastIndex = fileName.lastIndexOf(Util.DIR_SPLIT);
				String desDir = targetPath + Util.DIR_SPLIT
						+ fileName.substring(0, lastIndex);

				if (fileName.contains(Util.bottom)) {
					desDir = targetPath + Util.DIR_SPLIT + Util.Bottom
							+ Util.DIR_SPLIT + Util.drawable;
				} else if (fileName.contains(Util.home)) {
					desDir = targetPath + Util.DIR_SPLIT + Util.Home
							+ Util.DIR_SPLIT;
				} else if (fileName.contains(Util.preview)) {
					desDir = targetPath + Util.DIR_SPLIT + Util.Preview
							+ Util.DIR_SPLIT;
				}

				try {
					ZipFileAction.copyFileWithDir(file.getAbsolutePath(),
							desDir,
							fileName.substring(lastIndex, fileName.length()));
				} catch (IOException e) {
					Util.printDetail(e.toString());
					e.printStackTrace();
				}

			} else if (isColor(fileName)) {
				int lastIndex = fileName.lastIndexOf(Util.DIR_SPLIT);
				String desDir = targetPath + Util.DIR_SPLIT + Util.Color;
				try {
					ZipFileAction.copyFileWithDir(
							file.getAbsolutePath(),
							desDir,
							Util.Color
									+ Util.Color_JING
									+ fileName.substring(lastIndex + 1,
											fileName.length()));
				} catch (IOException e) {
					Util.printDetail(e.toString());
					e.printStackTrace();
				}

			} else if (isLock(fileName)) {
				int lastIndex = fileName.lastIndexOf(Util.DIR_SPLIT);
				String desDir = targetPath + Util.DIR_SPLIT + Util.Lock
						+ Util.DIR_SPLIT + Util.drawable;
				try {
					ZipFileAction.copyFileWithDir(file.getAbsolutePath(),
							desDir,
							fileName.substring(lastIndex, fileName.length()));
				} catch (IOException e) {
					Util.printDetail(e.toString());
					e.printStackTrace();
				}
			} else {
				String desDir = iconResPath + Util.drawable;
				fileName = file.getName();
				fileName = fileName.replace("-res-", "-");
				fileName = fileName.replace("-", "_");
				try {
					ZipFileAction.copyFileWithDir(file.getAbsolutePath(),
							desDir, fileName);
				} catch (IOException e) {
					Util.printDetail(e.toString());
					e.printStackTrace();
				}
			}
		}
	}

	private boolean isColor(String fileName) {
		return fileName.contains(Util.Color);
	}

	private boolean isLock(String fileName) {
		return fileName.contains(Util.frameworks);
	}

	private boolean isHasDir(String fileName) {
		return fileName.contains(Util.DIR_SPLIT);
	}

	private boolean isOtherFile(String fileName) {
		return fileName.contains(Util.Other);
	}

	private boolean isSpecialFile(String fileName) {
		return fileName.contains(Util.Launcher);
	}

	private boolean isDrawableArray(String fileName) {
		return fileName.contains(Util.Lock);
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
