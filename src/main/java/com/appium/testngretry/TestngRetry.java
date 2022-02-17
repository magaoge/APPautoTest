package com.appium.testngretry;

import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class TestngRetry implements IRetryAnalyzer {

    private Logger logger = Logger.getLogger(TestngRetry.class);

//    最大重试次数
    private  int maxRetryCount = 2;

    //  当前重试次数
    public static int retryCount = 1;

    @Override
    //这里继承了IRetryAnalyzer类，这个类是重试类，当执
    // 行失败的时候，又引用了重试机制，那么就会自动的走入retry方法
    public boolean retry(ITestResult iTestResult) {
        //iTestResult提供了方法去获取当前执行重试类的方法名
        logger.info("开始第"+retryCount+"次重试"+"测试方法为："+iTestResult.getName());
        if(retryCount  <= maxRetryCount){
            retryCount++;
            return true;
        }
        //默认为false,如果是false则代表不需要重试，如果是true则不需要重试
        return false;
    }
}
