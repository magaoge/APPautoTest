package com.appium.util;

import com.appium.base.Base;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import java.time.Duration;

/**
 * Created by mgg on 2021/10/11
 */

public class AmplifyAndShrinkUtil extends Base {
    //放大方法，（x,y）为起点坐标
    public void zoom(int x, int y){
//     在指定驱动上生成多触操作对象
        MultiTouchAction multiTouchAction = new MultiTouchAction(androidDriver);
//        获取屏幕高度
        int yHeigth = androidDriver.manage().window().getSize().height;
        //设定y轴偏移量（也就是后面手指需要移动的终点）
        int yOffset = 100;
//        如果传入坐标y值小于偏移量（也就是指滑操作要向内操作了，对应手指在屏幕上交叉），那就令偏移量等于y值
        //如果传入坐标y值+100大于偏移量（也就是指滑操作终点要超出屏幕了），那就令偏移量等于屏幕减去y值，想不明白，不必深究
        if(y - 100 < 0 ){
            yOffset = y ;
        }else if(y + 100 > yHeigth){
            yOffset = yHeigth - y;
        }
//      设置行为0，一根手指的运动轨迹
        TouchAction touchAction0 = (new TouchAction(androidDriver))
                .press(PointOption.point(x, y))
                .moveTo(PointOption.point(0, -yOffset))
                .release();
//      设置行为1，另一根手指的运动轨迹
        TouchAction touchAction1 = (new TouchAction(androidDriver))
                .press(PointOption.point(x, y))
                .moveTo(PointOption.point(0, yOffset))
                .release();
//        添加指滑的两个操作
        multiTouchAction.add(touchAction0).add(touchAction1);
        //执行
        multiTouchAction.perform();
    }

    //缩小方法，（x,y）为终点坐标
    public void pinch(int x, int y){
//     在指定驱动上生成多触操作对象
        MultiTouchAction multiTouchAction = new MultiTouchAction(androidDriver);
//        获取屏幕高度
        int yHeigth = androidDriver.manage().window().getSize().height;
        //设定y轴偏移量（也就是后面手指需要移动的与y值的参照距离）
        int yOffset = 100;
//        如果传入坐标y值小于偏移量（对应手指在屏幕上交叉），那就令偏移量等于y值
        //如果传入坐标y值+100大于偏移量（也就是指滑操作终点要超出屏幕了），那就令偏移量等于屏幕减去y值，想不明白，不必深究
        if(y - 100 < 0 ){
            yOffset = y ;
        }else if(y + 100 > yHeigth){
            yOffset = yHeigth - y;
        }
//      设置行为0，一根手指的运动轨迹
        TouchAction touchAction0 = (new TouchAction(androidDriver))
                .press(PointOption.point(x, y-yOffset))
                .moveTo(PointOption.point(x,y))
                .release();
//      设置行为1，另一根手指的运动轨迹
        TouchAction touchAction1 = (new TouchAction(androidDriver))
                .press(PointOption.point(x, y+yOffset))
                .moveTo(PointOption.point(x,y))
                .release();
//        添加指滑的两个操作
        multiTouchAction.add(touchAction0).add(touchAction1);
        //执行
        multiTouchAction.perform();
    }

    //    多点触控（可用来测试APP多点触控崩溃的场景）
    public void tap(int fingers ,int x, int y,int duration){
//     在指定驱动上生成多触操作对象
        MultiTouchAction multiTouchAction = new MultiTouchAction(androidDriver);
//时间转换对象
        Duration swipeTime = Duration.ofSeconds(duration);
//将需要触碰的点位、手指数进行存放，放在multiTouchAction对象中
        for (int i = 0; i<fingers ; ++i){
            TouchAction touchAction = (new TouchAction(androidDriver))
                    .tap(PointOption.point(x,y))
                    .waitAction(WaitOptions.waitOptions(swipeTime));
            multiTouchAction.add(touchAction);
        }
//        执行
        multiTouchAction.perform();
    }

}
