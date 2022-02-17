package com.appium.report;

import com.appium.testngretry.TestngRetry;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.util.Date;


//TestListenerAdapter是testng提供的一个适配的监听器
public class CustomListener extends TestListenerAdapter {
    private Logger logger = Logger.getLogger(CustomListener.class);

    @Override
    //重写方法（onTestFailure:当我们的测试用例执行失败的时候）
//    tr对象当中是封装了一些测试结果放进“上下文”容器中保存，其中保存的是在测试过程中用到的，以及生成的数据
    public void onTestFailure(ITestResult tr) {
//      下面是创建我们需要生成的存放失败用例截图文件夹目录的代码，首先要一级一级的获取文件夹名
//        比如我们现在想要的存放失败用例截图文件夹为："test-output/screenshot/yyyy-MM-dd/时间戳.jpg"

        //截图存放文件夹根目录(这里因为我们在pom中添加了不需要转译html代码的配置，由于
        // 是maven插件执行，所以要通过maven test运行项目，那么生成的测试报告，在"target\\surefire-reports"文件夹内)

        if(TestngRetry.retryCount == 3) {
            String baseDir = "target" + File.separator + "surefire-reports";

//        String baseDir = "test-output";
            //截图存放文件夹
            String screenshotDir = "screenshot";
            /*
             * Context:上下文
             * getTestContext()：是获取测试结果的上下文
             * getCurrentXmlTest()：是获取当前测试结果的上下文(注：这里需要做截图笔记)
             * getName()：是获取当前测试集的name值
             * */
            String testNameDir = tr.getTestContext().getCurrentXmlTest().getName();

            //获取当前时间
            Date date = new Date();
            //格式化我们的日期对象：DateFormatUtils.format
            //比如我们每天执行一次代码，那么就以天为单位生成新的文件夹
            String dateDir = DateFormatUtils.format(date, "yyyy-MM-dd");

            //获取当前时间戳作为截图文件的文件名
            String fileName = date.getTime() + ".jpg";

            /*
             * 最终需要创建的存放失败用例截图文件夹
             * File.separator：相当于电脑中的“\”，分割文件夹层级
             * */
            String filePath = baseDir + File.separator
                    + screenshotDir + File.separator
                    + testNameDir + File.separator
                    + dateDir + File.separator
                    + fileName;
            logger.info("*************************截图存放的路径是：" + filePath);

            //获取到截图
            File file = ScreenshotUtil.saveScreenshot(filePath);

            String toBeReplaced = tr.getTestContext().getCurrentXmlTest().getParameter("apacheDocumentRoot");
            String replacement = tr.getTestContext().getCurrentXmlTest().getParameter("host");

            String imgString = getImgString(toBeReplaced, replacement, file);

//        将图片地址写到reportng中的日志中去
            Reporter.log(imgString);
            TestngRetry.retryCount = 1;
        }
    }

    //重写方法执行成功的方法，当方法执行成功的时候，进入方法，将重试值设置为1
    @Override
    public void onTestSuccess(ITestResult tr) {
        TestngRetry.retryCount = 1;
        this.onTestSuccess(tr);
    }

    /*getImgString：获取页面展示图片的html代码
    toBeReplaced：要被替换的内容
    * replacement：替换toBeReplaced的内容
    * */
    private String getImgString(String toBeReplaced,String replacement,File file) {
        //获取截图文件的绝对路径
        String absolutePath = file.getAbsolutePath();
        //将绝对路径中的部分内容替换掉，这里就得到了一个访问图片的HTTP地址
        String accessPath = null;
        try {
            accessPath = absolutePath.replace(toBeReplaced, replacement);
            logger.info("=================================================匹配路径是："+accessPath);
        } catch (Exception e) {
            logger.info("===============================没有匹配到可替换的路径信息！=================================");
            e.printStackTrace();
        }

        String img = "<img src='"+accessPath+"' height='100' width='100'><a href='"+accessPath+"' target='_blank'>点击查看大图</a></img>";

        return img;
    }

}
