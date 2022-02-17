import com.appium.base.Base;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.Set;

/**
 * Created by mgg on 2021/10/10
 */

public class HybridTest extends Base {

    public static void testHybrid() throws InterruptedException {
        //获取所有context句柄
        Set<String> contextHandles = androidDriver.getContextHandles();
        System.out.println(contextHandles);
        //这一部分是哪里需要从app句柄切换到web浏览器句柄的时候就放在前面，进行驱动句柄切换
        //或者是在上面两行代码，获取到context句柄之后，就可以知道句柄名称，再直接将名称写入
//        下面驱动句柄切换的部分就好
        for (String contextHandle : contextHandles) {
            if (contextHandle.contains("WEBVIEW")){
                //驱动句柄切换,但是这里我并没有尝试，不知道会不会存在作用域的问题
                androidDriver.context(contextHandle);
                //注，最好进行一个等待，因为APP中的web跳转是很慢的
                Thread.sleep(5000);
            };
        }

// 如果找不到对应定位的标签，那么我们可以尝试获取页面源码进
// 行查找核对,有些页面跳转并不是页面句柄，而是iframe嵌套
        androidDriver.getPageSource();

        //在我们的webview中，多个页面，也是有多个句柄的，这与我们前面web自动化对应浏览器窗口句柄所说的一样
        Set<String> windowHandles = androidDriver.getWindowHandles();
        System.out.println(androidDriver.getWindowHandles());
        for (String windowHandle : windowHandles) {
            androidDriver.switchTo().window(windowHandle);
            Thread.sleep(5000);
            String pageSource = androidDriver.getPageSource();
            if (pageSource.contains("所需要定位的页面的信息")){
                break;
            }
        }
    }
}
