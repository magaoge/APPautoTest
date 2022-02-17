package com.appium.util;

import com.appium.base.Base;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mgg on 2021/10/11
 */
//通过代码执行cmd命令
public class CmdCompilationsUtil extends Base {

    public static List<String> execCmd(String cmd) {
//1.首先判断cmd命令不为空
        if (!cmd.isEmpty()) {
            try {
//                2.创建runtime对象，执行exec方法，runtime.exec("shell命令")，"cmd /c "是隐藏cmd窗口执行
                Runtime runtime = Runtime.getRuntime();
                Process process = runtime.exec("cmd /c " + cmd);
//                3.初始化BufferedReader对象，读取cmd执行结果
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
//                4.初始化变量，获取命令输出结果，存放读取输出信息，以供返回
                List<String> content = new ArrayList<>();
//                5.初始化变量，获取命令输出结果，循环读取输出信息
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                    content.add(line);
                }
                return content;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //获取到当前连接PC端的所有的设备udid
    public static List<String> getUdidList(){

        List<String> content = execCmd("adb devices");
        //1.生成空的数组承接设备udid
        List<String> udidList = new ArrayList<>();
        //2.遍历命令执行后结果的数组集
        for (int i = 0; i < content.size() ; i++) {
            try {
                //3.跳过第一行行头信息和最后一行空数据
                if (i!=0 && i!=content.size()-1){
                    //4.根据空格仅获取设备的udid
                    String[] deviceInfos = content.get(i).split("\t");
                    udidList.add(deviceInfos[0]);
                }
            } catch (Exception e) {
                //5.如果appium是第一次启动还会有两行不用的信息被捕捉报错，需要跳过
                i = i+2;
            }
        }
        return udidList;
    }

    //调用系统中的程序
    public static void sendSysKeys( int key){
        androidDriver.pressKeyCode(key);
    }

}
