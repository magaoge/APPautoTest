package com.appium.util;

import com.appium.base.Base;
import com.appium.pojo.Locator;
import com.appium.pojo.Page;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mgg on 2021/10/20
 */

public class XmlUtil {

    public static List<Page> listPage = new ArrayList<Page>();

    static {
        readUILibrary();
    }

    //注：工具类中一般我们以静态来生明类
    public static void readUILibrary() {
        //解析xml文件
        //获取解析器
        SAXReader reader = new SAXReader();
        InputStream inputStream = null;
        try {
            //读取配置进入流
            inputStream = new FileInputStream(new File("D:\\project\\AppAutoTest\\src\\main\\resources\\UILibrary.xml"));
            //拿到document对象
            Document document = reader.read(inputStream);
            //获取根元素
            Element rootElement = document.getRootElement();
            //遍历子元素，根据标签，完成Page对象封装，与下面"封装"关联
            List<Element> pagesElement = rootElement.elements("Page");
            for (Element pageElement : pagesElement) {
                //获取标签的指定属性的属性值，与下面"封装"关联
                String activityName = pageElement.attributeValue("activityName");
                String pageDesc = pageElement.attributeValue("pageDesc");
                //遍历子元素，根据标签，完成UIElement对象封装
                List<Element> uiElement = pageElement.elements("locator");
                //生成，承接UIElement对象的列表
                List<Locator> uiElementList = new ArrayList<Locator>();
                for (Element element : uiElement) {
                    String by = element.attributeValue("by");
                    String value = element.attributeValue("value");
                    String keywordKeyword = element.attributeValue("desc");
                    Locator uiEle = new Locator(by, value, keywordKeyword);
                    uiElementList.add(uiEle);
                }
                //"封装"
                Page page = new Page(activityName, pageDesc, uiElementList);
                listPage.add(page);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据传入的pageKeyword，uiElmentKeyword和pages列表中Page对象的对应值进行比较，记得pages中还有列表，最终获取到的是可操的WWebElement对象元素
    public static WebElement getElementByKeyword(String pageKeyword, String uiElmentKeyword) {
        WebElement element = null;
        for (Page page : listPage) {
            if (page.getPageDesc().equals(pageKeyword)) {
                List<Locator> uiElements = page.getListLocator();
                for (Locator locator : uiElements) {
                    if (locator.getValueDesc().equals(uiElmentKeyword)) {
                        String by = locator.getLocatorBy();
                        String value = locator.getLocatorValue();
                        //
                        element = getVisibleElement(by, value);
                    }
                }
            }
        }
        return element;
    }

    //
    private static WebElement getVisibleElement(String by, String value) {
        //设置显示等待
        WebDriverWait wait = new WebDriverWait(Base.androidDriver, 20);
        WebElement element = null;
        By locater = null;
        /*这里之前我都是写的变量在前，字符串在后，但是这样可能存在一个问题,
                        如果变量值为空，则比较结果就会报空指针
                        **/
        //根据传入的类型，匹配对应的查找元素方式，这里也可以通过穿透来做选择
        if ("id".equals(by)) {
            //查找元素
            locater = By.id(value);
        }
        else if ("XPath".equals(by)) {
            //查找元素
            locater = By.xpath(value);
        }
        else {
            System.out.println("暂不支持类型！");
        }
        try {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locater));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return element;
    }

//       通过反射的方法去实现调用，更加解耦，以后需要修改的地方更少
//    private static WebElement getVisibleElement(String by, String value) {
//        WebDriverWait wait = new WebDriverWait(Base.androidDriver, 20);
//
//        try {
////            1.获得对应类的字节码
//            Class<By> clazz = By.class;
//            // 2.通过获取的寻找元素方法，调用类的方法
//            Method method = clazz.getMethod(by, String.class);
//            // 3.为寻找元素的方法，输入参数
//            By by1 = (By) method.invoke(null, value);
//            return Base.androidDriver.findElement(by1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return  null;
//    }

//    public static void main(String[] args) {
//        readUILibrary();
//        for (Page s : listPage) {
//            System.out.println(s.getPageDesc());
//            System.out.println(s.getActivityName());
//            List<Locator> locators = s.getListLocator();
//            for (Locator locator : locators) {
//                System.out.println(locator.getLocatorBy());
//                System.out.println(locator.getLocatorValue());
//                System.out.println(locator.getValueDesc());
//            }
//        }
//    }
}
