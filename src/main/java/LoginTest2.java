import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.URL;

public class LoginTest2 {

    public static AndroidDriver<WebElement> androidDriver ;


    @BeforeTest
    //提供一种思路，可不可以通过adb命令去获取设备信息，然后再把对应的值取出来，作为数据传进该方法
    public  void setUp() {

//        代码方式
//        01.创建配置对象
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
//        02.添加deviceName:确定用哪一台设备进行测试
        desiredCapabilities.setCapability("deviceName", "bf09e850");
//        03.添加platformName:标明测试平台
        desiredCapabilities.setCapability("platformName", "Android");
//        04.添加appPackage:标明要测试那个应用程序
        desiredCapabilities.setCapability("appPackage", "com.jingdong.app.mall");
//        05.添加appActivity:表示启动应用程序的入口
//        也可以简写，因为appium会实现appPackage与入口类的拼接
        desiredCapabilities.setCapability("appActivity", ".main.MainActivity");
//        desiredCapabilities.setCapability("appActivity","com.jingdong.app.mall.main.MainActivity");

//        noReset属性表示是否清楚应用的数据，如果为true表示不清除，如果为false表示清除。
//        因为有很多时候，APP会保留用户的数据信息，noReset可以清除用户信息
        desiredCapabilities.setCapability("noReset", false);

        //skipServerInstallation属性，设置为true，跳过每次启动都需要人工确认
        // 安装io.appium.uiautomator2.server.apk和io.appium.uiautomator2.server.test.apk的操作
        desiredCapabilities.setCapability("skipServerInstallation", true);

        //2.首先要打开appium，启动其服务，这样下面才可以通过appium服务IP进行操作
//        3.打开测试app程序，在初始化Android驱动的时候，需要在构造方法里面传入两个参数
//        01.appium的IP
        //02.初始化的配置对象

        try {
            androidDriver = new AndroidDriver<WebElement>(
                    new URL("http://127.0.0.1:4723/wd/hub"),
                    desiredCapabilities);
//
//            androidDriver.zoom(10,20);
//            androidDriver.pinch(10,20);
//            androidDriver.tap(1,1,1,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loginTest(){
        //3.找元素，操作元素
        try {
            Thread.sleep(2000);
            androidDriver.findElementById("com.jingdong.app.mall:id/bqd").click();
            Thread.sleep(2000);
            androidDriver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView[3]").click();
            Thread.sleep(2000);
            androidDriver.findElementById("com.lbe.security.miui:id/permission_allow_onetime_button").click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterTest
    public void tearDown(){
        //4.销毁动作
        androidDriver.close();
    }

}
