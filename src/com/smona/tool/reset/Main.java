package com.smona.tool.reset;

import com.smona.tool.reset.action.ClassificationAction;
import com.smona.tool.reset.action.IAction;
import com.smona.tool.reset.action.TogetherAction;
import com.smona.tool.reset.action.TrimAction;
import com.smona.tool.reset.action.UnzipAction;
import com.smona.tool.reset.util.Logger;
import com.smona.tool.reset.util.Util;

public class Main {

	public static void main(String[] args) {
		Logger.init();
		String encode = System.getProperty("file.encoding");
		println(encode);
		String path = System.getProperty("user.dir");
		println(path);
		execute(path);
	}

	//解压-->聚合-->归类-->微调
	private static void execute(String filePath) {
		IAction unzip = new UnzipAction();
		unzip.execute(filePath);

		IAction together = new TogetherAction();
		together.execute(filePath);

		IAction classification = new ClassificationAction();
		classification.execute(filePath);
		
		IAction trim = new TrimAction();
		trim.execute(filePath);
	}

	private static void println(String msg) {
		Util.printDetail(msg);
	}
}
