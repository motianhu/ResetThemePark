package com.smona.tool.reset.util;

import java.io.File;

public class Util {

	public static final String DIR_SPLIT = File.separator;
	public static final String REPLACE_PATH_SIGN = "-";

	public static final String SOURCE = "source";
	public static final String UNZIP = "unzip";
	public static final String TOGETHER = "together";
	public static final String CLASSIFICATION = "classification";
	public static final String TARGET = "target";

	// unzip special folder
	public static final String Icon = "Icon";
	public static final String Other = "other";
	public static final String bottom = "bottom";
	public static final String home = "home";
	public static final String preview = "preview";
	public static final String frameworks = "frameworks";
	public static final String Launcher = "Launcher";

	// compose
	public static final String drawable = "drawable";
	public static final String res = "res";
	public static final String Color_JING = "#";
	
	public static final String Attachment = "Attachment";
	public static final String Bottom = "Bottom";
	public static final String Color = "Color";
	public static final String Contacts_apk = "Contacts_apk";
	public static final String drawable_array = "drawable_array";
	public static final String GNLauncherPlus = "GNLauncherPlus";
	public static final String Home = "Home";
	public static final String Icon_Compose = "Icon";
	public static final String Lock = "Lock";
	public static final String Mms_apk = "Mms_apk";
	public static final String Package_temp_folder = "Package_temp_folder";
	public static final String Preview = "Preview";
	public static final String Settings = "Settings";
	public static final String SystemUI_apk = "SystemUI_apk";
	public static final String Thirdpart = "Thirdpart";
	public static final String Thirdpart_oversea = "Thirdpart_oversea";
	public static final String language_properties = "language.properties";
	public static final String since_properties = "since.properties";

	public static void printDetail(String msg) {
		Logger.printDetail(msg);
	}

	public static void printReport(String msg) {
		Logger.printReport(msg);
	}
}
