package com.appium.util;

import com.appium.base.Base;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Created by mgg on 2021/10/9
 */

public class SwipeUtil extends Base {

    //1.得到屏幕分辨路，X轴最大长度，Y轴最大长度
    public static int xWitdth;
    public static int yHeigth;

    static {
        xWitdth = androidDriver.manage().window().getSize().width;
        yHeigth = androidDriver.manage().window().getSize().height;
    }


    /*由于java-client版本的问题，我之前使用的是7.0.0，导致其实这里点击、滑动的操作，需要封装的很深，与课程不符合
于是我降级自己的java-client版本为5.0.0，之后如果感兴趣，还可以再重新封装，现在，暂时跟着学习
这里是首先自定义doSwipe方法
* */
    public static void doSwip(int startx , int starty , int endx , int endy , int duration ){
        //生成TouchAction操作对象
        TouchAction touchAction = new TouchAction(androidDriver);
        //转换时间为Duration对象，封装所需转换
        Duration duration1 = Duration.ofSeconds(duration);
        //看图
        touchAction.press(PointOption.point(startx, starty))
                .waitAction(WaitOptions.waitOptions(duration1))
                .moveTo(PointOption.point(endx, endy)).release();
        //开始执行
        touchAction.perform();
    }


    //通过滑动方法，向下滑
    public static void swipeDown(int duration){

        //起点坐标
        int startx = xWitdth/2;
        int starty = yHeigth * 3/4;

        //终点坐标
        int endx = xWitdth/2;
        int endy = yHeigth * 1/4;

        doSwip(startx,starty,endx,endy,duration);
    }

    //通过滑动方法，向上滑
    public static void swipeUp(int duration){

        //起点坐标
        int startx = xWitdth/2;
        int starty = yHeigth * 1/4;

        //终点坐标
        int endx = xWitdth/2;
        int endy = yHeigth * 3/4;

        doSwip(startx,starty,endx,endy,duration);
    }

    //通过滑动方法，向左滑
    public static void swipeLeft(int duration){

        //起点坐标
        int startx = xWitdth*3/4;
        int starty = yHeigth/2;

        //终点坐标
        int endx = xWitdth*1/4;
        int endy = yHeigth/2;

        doSwip(startx,starty,endx,endy,duration);
    }

    //通过滑动方法，向右滑
    public static void swipeRight(int duration ){
        //起点坐标
        int startx = xWitdth*1/4;
        int starty = yHeigth/2;

        //终点坐标
        int endx = xWitdth*3/4;
        int endy = yHeigth/2;

        doSwip(startx,starty,endx,endy,duration);
    }

//    //通过滑动方法，向右滑
//    public static void swipeRight(int duration){
//        //1.得到屏幕分辨路，X轴最大长度，Y轴最大长度
//        int xWitdth = androidDriver.manage().window().getSize().width;
//        int yHeigth = androidDriver.manage().window().getSize().height;
//
//        //起点坐标
//        int startx = xWitdth*1/4;
//        int starty = yHeigth/2;
//
//        //终点坐标
//        int endx = xWitdth*3/4;
//        int endy = yHeigth/2;
//
//        TouchAction touchAction = new TouchAction(androidDriver);
//
//        Duration swipeTime = Duration.ofSeconds(duration);
//
//        touchAction.press(startx, starty).waitAction(swipeTime).moveTo(endx, endy).release();
//        //开始执行
//        touchAction.perform();
//    }

}
