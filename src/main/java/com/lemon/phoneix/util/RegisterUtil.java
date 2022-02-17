package com.lemon.phoneix.util;

import com.lemon.phoneix.pojo.Sheet1;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mgg on 2021/9/27
 */

//获取列表中的指定行、列的数据
public class RegisterUtil {
    //生成列表容器，存放excel表中的数据，因为我们是针对每张表存放一
// 个list，所以我们要创建对应的实体类Sheet1，来将表中的每行数据封装成对象存入列表中
    public static List<Sheet1> Sheet1s = new ArrayList<Sheet1>();
    //初始化指定表格中的数据，存入列表Sheet1s中
    static {
        List<Sheet1> list = ExcelUtil.load("src/main/resources/lemon_testCase.xlsx","Sheet1",Sheet1.class);
        Sheet1s.addAll(list);
        for (Sheet1 data : Sheet1s) {
            System.out.println(data.toString());
        }
    }

    //获取指定“反向”列，哪几列列值的用例的数据
    public static Object[][] getDatas(String[] cellName){
        //生成存放第一层筛选，筛选后数据的列表容器
        List<Sheet1> satisfied = new ArrayList<Sheet1>();

        //生成存放筛选出的行，所需列的二维数组
        Object[][] datas = new Object[satisfied.size()][cellName.length];
        //获取Sheet1的反射对象，以备后面需要调用反射对象的方法
        Class<Sheet1> clazz = Sheet1.class;
        for (int i = 0; i < satisfied.size(); i++) {
            //3.获取Sheet1List中的第i个case对象
            Sheet1 Sheet1 = satisfied.get(i);
            //cellNames.length是数组长度，实际业务意义是哪几列，有几列
            //4.循环遍历其中所有声明的列值
            for (int j = 0; j <cellName.length ; j++) {
                //5.拼接获取case对象获取列值得方法名
                String methodName = "get"+cellName[j];
                try {
                    //7.通过反射调用方法
                    Method method = clazz.getMethod(methodName);
                    //8.使用Sheet1List中的第i个case对象来调用对象方法，这里是获取行中每一列的列值
                    String value = (String) method.invoke(Sheet1);
                    //9.将对应行的每一列的数据存入二维数组
                    datas[i][j] = value;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return datas;
    }
//可用作验证使用
//    public static void main(String[] args) {
//
//        String[] cellName = {"Description","PhoneNum","Password","Expected"};
//        Object[][] datas = RegisterUtil.getDatas(cellName);
//        for (Object[] data : datas) {
//            for (Object datum : data) {
//                System.out.println(datum.toString());
//            }
//            System.out.println("=====================");
//        }
//    }
}