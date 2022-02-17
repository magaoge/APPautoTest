package com.appium.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;

import static com.appium.util.XmlUtil.getElementByKeyword;

/**
 * Created by mgg on 2021/10/9
 */

public class Base{

    //02.初始化的配置对象
    public static AndroidDriver<WebElement> androidDriver;


    @Parameters({"deviceName","platformName","appPackage","appActivity","applicationName","URL"})
    @BeforeTest
    public void initializationDriver(String deviceName,String platformName,String appPackage,String appActivity,String applicationName,String URL){
//        1.找到对应设备
//        代码方式
//        01.创建配置对象
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
//        02.添加deviceName:确定用哪一台设备进行测试
        desiredCapabilities.setCapability("deviceName", deviceName);
//        03.添加platformName:标明测试平台
        desiredCapabilities.setCapability("platformName", platformName);
//        04.添加appPackage:标明要测试那个应用程序
        desiredCapabilities.setCapability("appPackage", appPackage);
//        05.添加appActivity:表示启动应用程序的入口
//        也可以简写，因为appium会实现appPackage与入口类的拼接
        desiredCapabilities.setCapability("appActivity", appActivity);
//        desiredCapabilities.setCapability("appActivity","com.jingdong.app.mall.main.MainActivity");

//        noReset属性表示是否清楚应用的数据，如果为true表示不清除，如果为false表示清除。
//        因为有很多时候，APP会保留用户的数据信息，noReset可以清除用户信息
        desiredCapabilities.setCapability("noReset", false);

        //skipServerInstallation属性，设置为true，跳过每次启动都需要人工确认
        // 安装io.appium.uiautomator2.server.apk和io.appium.uiautomator2.server.cases.apk的操作
        desiredCapabilities.setCapability("skipServerInstallation", true);

        desiredCapabilities.setCapability("applicationName", applicationName);
        try {
            androidDriver = new AndroidDriver<WebElement>(
                    new URL(URL),
                    desiredCapabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @AfterTest
    public void releaseDriver(){
        try {
            androidDriver.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //点击行为操作类
    public void clickElement(String pageKeyword, String uiElmentKeyword ){
        getElementByKeyword(pageKeyword,uiElmentKeyword).click();
    }

    //输入行为操作类
    public void sendKeyElement(String pageKeyword, String uiElmentKeyword ,String keyWord){
        getElementByKeyword(pageKeyword,uiElmentKeyword).sendKeys(keyWord);
    }
}
