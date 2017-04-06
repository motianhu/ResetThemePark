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

			// root file
			if (isSingleFile(fileName)) {
				if (fileName.equalsIgnoreCase(Util.language_properties)
						|| fileName.equalsIgnoreCase(Util.since_properties)) {
					FileOperator.fileChannelCopy(file, targetPath
							+ Util.DIR_SPLIT + fileName);
				} else if (fileName.equalsIgnoreCase(Util.ATTACH_JSON)) {
					String desDir = targetPath + Util.DIR_SPLIT
							+ Util.Attachment;
					try {
						ZipFileAction.copyFileWithDir(file.getAbsolutePath(),
								desDir, fileName);
					} catch (IOException e) {
						Util.printDetail(e.toString());
						e.printStackTrace();
					}
				}
				// contain Launcher
			} else if (isSpecialFile(fileName)) {
				int lastIndex = fileName.lastIndexOf(Util.DIR_SPLIT);
				String desDir = targetPath + Util.DIR_SPLIT
						+ fileName.substring(0, lastIndex);

				if (!desDir.contains(Util.DIR_SPLIT + Util.Launcher_Business
						+ Util.DIR_SPLIT)) {
					desDir = desDir.replace(Util.DIR_SPLIT + Util.res, "");
				}
				if (desDir.contains(Util.DIR_SPLIT + Util.Launcher_Bubble_L
						+ Util.DIR_SPLIT)) {
					int startIndex = desDir.lastIndexOf(Util.DIR_SPLIT
							+ Util.Launcher_Bubble_L + Util.DIR_SPLIT);
					desDir = desDir.substring(0, startIndex);
					desDir += Util.DIR_SPLIT + Util.Launcher_Bubble_W
							+ Util.DIR_SPLIT + Util.Launcher_Default
							+ Util.DIR_SPLIT;
				} else if (desDir.contains(Util.DIR_SPLIT
						+ Util.Launcher_Indicator_L + Util.DIR_SPLIT)) {
					desDir = desDir.replace(Util.DIR_SPLIT
							+ Util.Launcher_Indicator_L + Util.DIR_SPLIT,
							Util.DIR_SPLIT + Util.Launcher_Indicator_W
									+ Util.DIR_SPLIT);
				}

				try {
					ZipFileAction.copyFileWithDir(file.getAbsolutePath(),
							desDir,
							fileName.substring(lastIndex, fileName.length()));
				} catch (IOException e) {
					Util.printDetail(e.toString());
					e.printStackTrace();
				}
				// contain Other
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
				// contain Color
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

				// contain framework
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
			} else if (isAttachment(fileName)) {
				int lastIndex = fileName.lastIndexOf(Util.DIR_SPLIT);
				String desDir = targetPath + Util.DIR_SPLIT + Util.Attachment;
				try {
					ZipFileAction.copyFileWithDir(file.getAbsolutePath(),
							desDir, fileName.substring(lastIndex, fileName.length()));
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
		return fileName.contains(Util.Color + Util.DIR_SPLIT);
	}

	private boolean isLock(String fileName) {
		return fileName.contains(Util.frameworks + Util.DIR_SPLIT);
	}

	private boolean isSingleFile(String fileName) {
		return !fileName.contains(Util.DIR_SPLIT);
	}

	private boolean isOtherFile(String fileName) {
		return fileName.contains(Util.Other + Util.DIR_SPLIT);
	}

	private boolean isSpecialFile(String fileName) {
		return fileName.contains(Util.Launcher);
	}

	private boolean isAttachment(String fileName) {
		return fileName.contains(Util.Attachment + Util.DIR_SPLIT);
	}

	private boolean isDrawableArray(String fileName) {
		return fileName.contains(Util.Lock + Util.DIR_SPLIT);
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
