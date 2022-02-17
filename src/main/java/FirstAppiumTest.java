import com.appium.base.Base;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;


import java.io.IOException;
import java.net.URL;
import java.time.Duration;

/**
 * Created by mgg on 2021/10/8
 */

public class FirstAppiumTest  {

    public static AndroidDriver<WebElement> androidDriver = null;

    public static void main(String[] args) {

//        1.找到对应设备
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
        // 安装io.appium.uiautomator2.server.apk和io.appium.uiautomator2.server.cases.apk的操作
        desiredCapabilities.setCapability("skipServerInstallation", true);

        //配置uiautomationName为UIautomator2，可以更快的运行程序
        desiredCapabilities.setCapability(MobileCapabilityType.APPLICATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);

        //2.首先要打开appium，启动其服务，这样下面才可以通过appium服务IP进行操作
//        3.打开测试app程序，在初始化Android驱动的时候，需要在构造方法里面传入两个参数
//        01.appium的IP
        //02.初始化的配置对象

        try {
            androidDriver = new AndroidDriver<WebElement>(
                    new URL("http://127.0.0.1:4723/wd/hub"),
                    desiredCapabilities);
            //3.找元素，操作元素
            Thread.sleep(2000);
            sendSysKeys(25);
//            重置APP数据,如果不是每次都需要重新初始化，我觉得没必要，反正自己练习的时候还要每次登陆，很麻烦，或者抽象封装一下初始化动作
            androidDriver.resetApp();
//            判断APP是否已安装
            System.out.println(androidDriver.isAppInstalled("com.jingdong.app.mall"));
//            获取当前页面源码
            System.out.println(androidDriver.getPageSource());
//            获取当前页面的运行类名
            System.out.println(androidDriver.currentActivity());
//            获取设备的连接配置
            System.out.println(androidDriver.getCapabilities());
//            根据设备配置的key值，来获取value
            Object deviceName = androidDriver.getCapabilities().getCapability("deviceName");
            System.out.println(deviceName);
//            获取设备时间
            System.out.println(androidDriver.getDeviceTime());
//            获取设备DPI
            System.out.println(androidDriver.getDisplayDensity());
//            获取屏幕横竖屏状态,竖屏PORTRAIT,横屏LANDSCAPE
            System.out.println("横竖屏："+androidDriver.getOrientation());

//            androidDriver.findElementById("com.jingdong.app.mall:id/bqd").click();

//            swipeDown(5);

//        4.销毁动作
            androidDriver.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*由于java-client版本的问题，我之前使用的是7.0.0，导致其实这里点击、滑动的操作，需要封装的很深，与课程不符合
于是我降级自己的java-client版本为5.0.0，之后如果感兴趣，还可以再重新封装，现在，暂时跟着学习
这里是首先自定义doSwipe方法
* */
    private static void doSwipe(int startx, int starty, int endx, int endy, int duration) {
            //生成TouchAction操作对象
            TouchAction touchAction = new TouchAction(androidDriver);
            //转换时间为Duration对象，封装所需转换
            Duration swipeTime = Duration.ofSeconds(duration);
            //看图
            touchAction.press(PointOption.point(startx, starty))
                    .waitAction(WaitOptions.waitOptions(swipeTime))
                    .moveTo(PointOption.point(endx, endy)).release();
            //开始执行
            touchAction.perform();
        }


    //通过滑动方法，向下滑
    public static void swipeDown(int duration){
        int xWitdth = androidDriver.manage().window().getSize().width;
        int yHeigth = androidDriver.manage().window().getSize().height;
        //起点坐标
        int startx = xWitdth/2;
        int starty = yHeigth * 3/4;

        //终点坐标
        int endx = xWitdth/2;
        int endy = yHeigth * 1/4;
        doSwipe(startx,starty,endx,endy,duration);
    }

    //调用系统中的程序
    public static void sendSysKeys(int key){
        androidDriver.pressKeyCode(key);
    }

}