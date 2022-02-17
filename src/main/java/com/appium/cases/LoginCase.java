package com.appium.cases;

import com.appium.base.Base;
import com.appium.testngretry.TestngRetry;
import com.appium.util.SearcheUtil;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.appium.util.XmlUtil.getElementByKeyword;

/**
 * Created by mgg on 2021/10/11
 */

public class LoginCase extends Base {

    @Test(priority = 0)
    //注：如果没有priority属性，那么我们的代码是按照被Test注释标注方法名首字母，字典排序来执行的
    public void openApp(){
        try {
            Thread.sleep(2000);
            //二次解耦后
            clickElement("登陆","同意协议");
//            getElementByKeyword("登陆","同意协议").click();
//            androidDriver.findElementById("com.jingdong.app.mall:id/bqd").click();
            Thread.sleep(2000);
            getElementByKeyword("登陆","同意定位").click();
//            androidDriver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.TextView[3]").click();
            Thread.sleep(2000);
            getElementByKeyword("登陆","仅在使用中允许定位").click();
//            androidDriver.findElementById("com.lbe.security.miui:id/permission_allow_onetime_button").click();

            Thread.sleep(2000);
            getElementByKeyword("登陆","进入搜索页面").click();
//            androidDriver.findElementByXPath("//android.view.View[@content-desc=\"发现\"]").click();
            //点击搜索
            Thread.sleep(2000);
            getElementByKeyword("搜索","点击搜索框").click();
//            androidDriver.findElementById("com.jd.lib.Discovery.feature:id/dc").click();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    @Test(dataProvider = "getSearchData",priority = 1)
    public void searchKeyword(String searchKeyword){
        //点击搜索，输入关键字
        try {
            Thread.sleep(2000);
            WebElement search = getElementByKeyword("搜索","输入搜索值");
//            WebElement search = androidDriver.findElementById("com.jd.lib.meme.feature:id/id");
            search.sendKeys(searchKeyword);
            search.clear();

            TestngRetry.retryCount = 1;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    @DataProvider
    public Object[] [] getSearchData(){
        String[] cellName = {"SearchKeyword"};
        Object[][] searchDatas = SearcheUtil.getSearchDatas(cellName);
        return searchDatas;
    }

}
