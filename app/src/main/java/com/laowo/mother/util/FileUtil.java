package com.laowo.mother.util;

import android.os.Environment;

import com.laowo.mother.MoApplication;

import java.io.File;

/**
 * 文件工具类
 * Created by xsh on 2016/8/12.
 */
public class FileUtil {

    // ------------------------------------数据的缓存目录--------------------------------------------
    public static String SDCARD_PATH;// SD卡路径
    public static String LOCAL_PATH;// 本地路径,即/data/data/目录下的程序私有目录
    public static String CURRENT_PATH;// 当前的路径,如果有SD卡的时候当前路径为SD卡，如果没有的话则为程序的私有目录
    public static final String ROOT_NAME = "mother";// 项目根目录名

    // ------------------------------------项目内各文件夹--------------------------------------------
    public static File ROOT_PATH;//项目根目录

    static {
        init();
    }

    public static void init() {
        SDCARD_PATH = Environment.getExternalStorageDirectory().getPath(); //SD卡路径
        LOCAL_PATH = MoApplication.mInstance.getApplicationContext().getFilesDir().getAbsolutePath();//获取data/data/下的程序私有目录

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//如果SD卡挂载，则使用SD卡目录作为数据存储路径
            CURRENT_PATH = SDCARD_PATH;
        } else {
            CURRENT_PATH = LOCAL_PATH;
        }

        ROOT_PATH = new File(CURRENT_PATH + File.separator + ROOT_NAME);
        if (!ROOT_PATH.exists())
            ROOT_PATH.mkdirs();
    }

}
