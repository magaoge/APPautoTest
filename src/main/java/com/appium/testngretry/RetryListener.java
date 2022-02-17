package com.appium.testngretry;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryListener implements IAnnotationTransformer {
    @Override
    public void transform(ITestAnnotation iTestAnnotation, Class aClass, Constructor constructor, Method method) {
        //1. 得到IRetryAnalyzer对象
        IRetryAnalyzer retryAnalyzer = iTestAnnotation.getRetryAnalyzer();
        //2. setRetryAnalyzer方法去修改,这一步之后，再将类写进监听配置中，每个测试方法都会有重试的机制了
        iTestAnnotation.setRetryAnalyzer(TestngRetry.class);
    }
}
