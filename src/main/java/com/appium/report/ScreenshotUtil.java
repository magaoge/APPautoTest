package com.appium.report;

import com.appium.base.Base;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;


import java.io.File;

public class ScreenshotUtil {
    private static Logger logger = Logger.getLogger(ScreenshotUtil.class);
    public static File saveScreenshot(String filePath) {
        /*WebDriver中有一个负责截图的方法getScreenshotAs，但是这个方法是隶属WebDriver子类的，所以需要强转，明确的指定是哪个浏览器驱动
        ，然后才能调用getScreenshotAs方法
         */
            File screenshot = null;
            //判断驱动属于哪个浏览器，这里因为我只用了一个，所以不再进行多余的判断
            if (Base.androidDriver instanceof AndroidDriver) {
                //强转驱动指定浏览器
                AndroidDriver androidDriver = (AndroidDriver) Base.androidDriver;
                /*OutputType.FILE：指定获取截图的输出形式为FILE（文件形式）
                 * 这里获取的就是File文件对象*/
                screenshot = androidDriver.getScreenshotAs(OutputType.FILE);
            }

            //生成目标文件
            File destFile = new File(filePath);

        /*org.apache.commons.io包下
            FileUtils工具类的copyDirectory方法，两个参数分别为原文件、目标文件
            这个方法是一个复制的操作，执行后destFile就是错误截图
        * */
            try {
                FileUtils.copyFile(screenshot, destFile);
            } catch (Exception e) {
                logger.info("**********************************照片保存失败===================");
                e.printStackTrace();
            }
            return destFile;
    }
}
