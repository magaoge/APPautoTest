package com.appium.util;

import java.lang.reflect.Method;
import java.util.List;

public class ExcelBaseUtil {
    //通过拿到所需行列表，将读取指定列的数据取出、封装（这一动作需要通过反射调用对应表对象的方法进行取值）进新的二维数组中
    public static Object[][] assembleDatas(List<Object> satisfied, String[] cellName, Class clazz){
        Object[][] datas = new Object[satisfied.size()][cellName.length];
        for (int i = 0; i < satisfied.size(); i++) {
            //3.获取registerDataList中的第i个case对象
            Object registerData = satisfied.get(i);
            //cellNames.length是数组长度，实际业务意义是哪几列，有几列
            //4.循环遍历其中所有声明的列值
            for (int j = 0; j <cellName.length ; j++) {
                //5.拼接获取case对象获取列值得方法名
                String methodName = "get"+cellName[j];
                try {
                    //7.通过反射调用方法
                    Method method = clazz.getMethod(methodName);
                    //8.使用registerDataList中的第i个case对象来调用对象方法，这里是获取行中每一列的列值
                    String value = (String) method.invoke(registerData);
                    //9.将对应行的每一列的数据存入二维数组
                    datas[i][j] = value;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return datas;
    }
}
