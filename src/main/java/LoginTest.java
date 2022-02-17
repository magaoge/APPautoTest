import com.lemon.phoneix.util.RegisterUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AutomationName;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.net.URL;

public class LoginTest {

    public static AndroidDriver<WebElement> androidDriver ;

    @Parameters({"deviceName","platformName","appPackage","appActivity","automationName","appiumUrl"})
    @BeforeTest
    //提供一种思路，可不可以通过adb命令去获取设备信息，然后再把对应的值取出来，作为数据传进该方法
    public void setUp(String deviceName,String platformName,String appPackage,String appActivity,String automationName,String appiumUrl) {
//        1.找到对应设备
//        代码方式
//        01.创建配置对象
        System.out.println(deviceName+"\n"+deviceName+"\n"+platformName+"\n"+appPackage+"\n"+appActivity+"\n"+automationName+"\n"+appiumUrl+"\n");
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

        //配置automationName="UIAutomator2"才可以找到tose特殊元素的定位
        desiredCapabilities.setCapability("automationName", AutomationName.ANDROID_UIAUTOMATOR2);
        desiredCapabilities.setCapability("automationName", automationName);

        //skipServerInstallation属性，设置为true，跳过每次启动都需要人工确认
        //安装io.appium.uiautomator2.server.apk和io.appium.uiautomator2.server.test.apk的操作
        desiredCapabilities.setCapability("skipServerInstallation", true);

        desiredCapabilities.setCapability("appWaitActivity", appActivity);

        //2.首先要打开appium，启动其服务，这样下面才可以通过appium服务IP进行操作
//        3.打开测试app程序，在初始化Android驱动的时候，需要在构造方法里面传入两个参数
//        01.appium的IP
        //02.初始化的配置对象
        try {
            androidDriver = new AndroidDriver<WebElement>(
                    new URL(appiumUrl),
                    desiredCapabilities);
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

    @DataProvider
    public Object[][] getLoginData(){
        String[] cellName = {"Description","PhoneNum","Password","Expected"};
        Object[][] datas = RegisterUtil.getDatas(cellName);
        return datas;
    }

    @AfterTest
    public void tearDown(){
        //4.销毁动作
        androidDriver.close();
    }

}
