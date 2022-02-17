package com.appium.util;


import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by mgg on 2021/7/5
 */

//通用读取数据的工具类
public class ExcelUtil {

    //二、作用：读取数据，将所需表中的所有数据对象放入集合
    public static <T> List<T> load(String filePath, String sheetName, Class<T> clazz) {

        List<T> list = new ArrayList<T>();

        try {
            //1.获取Workbook对象，即将整个excol文件读取
            Workbook sheets = WorkbookFactory.create(new File(filePath));
            //2.获取sheet对象，即要操作整个excol文件中的哪个表
            Sheet sheet = sheets.getSheet(sheetName);
            //3.获取表行头信息
            Row titleRow = sheet.getRow(0);
            //4.获取表列数
            int lastCellNum = titleRow.getLastCellNum();

            //5.声明数组，承接表行头信息
            String[] fields = new String[lastCellNum];

            //6.将表行头信息依照所需的格式取出，封装进数组中
            for (int i = 0; i < lastCellNum; i++) {
                Cell cell = titleRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                String titleValues = cell.getStringCellValue();

                titleValues = titleValues.substring(0,titleValues.indexOf("（"));
                fields[i] = titleValues;
            }

            //7.获取表中所有行数，但是方法有一个问题就是，如果表有效数据末尾中有删除的信息，存在空行也会被统计在内
            int lastRowNum = sheet.getLastRowNum();
            //8.根据行头信息，访问反射类方法，为变量赋值，封装对象
            for (int i = 1; i <= lastRowNum; i++) {
                //9.循环创建Case对象，承接表中每行的数据
                T obj = clazz.newInstance();
                //10.获取表中第i行的所有信息
                Row dataRow = sheet.getRow(i);
                //11.由于刚才所说的统计问题，对行信息是否为空进行判断，新增判断方法，是空行的话，则循环下一行
                if(dataRow == null || isEmptyRow(dataRow)){
                    continue;
                }
                //12.获取每行的列数据，封装入数据对象
                for (int j = 0; j < lastCellNum ; j++) {
                    /*首次循环为取第2行（看i的值）第1列的值
                    第2次循环为取第2行（看i的值）第2列的值
                    ......
                    */
                    Cell cellValue = dataRow.getCell(j,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cellValue.setCellType(CellType.STRING);
                    //12.取到每行中，每一列的数据值
                    String value = cellValue.getStringCellValue();
                    //13.根据行头信息，访问反射类方法，为变量赋值，封装对象
                    String methodName = "set"+fields[j];
                    Method method = clazz.getMethod(methodName, String.class);
                    //14.反射类对象cs通过映射调用反射类对应方法，将取到的对应值赋值变量
                    method.invoke(obj,value);
                }
//                //循环封装对象进入cases集合
//                  但是这里存在耦合，解耦
//                if(obj instanceof Case){
//                    CaseUtil.cases.add((Case) obj);
//                }else if(obj instanceof Rase){
//                    RaseUtil.rases.add((Rase) obj);
//                }
                //15.每完成一行的赋值后，将该行赋值对象放入集合中
                list.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
            return list;
    }


    //7.判断行头信息是否为空值,只要有一个值不为空则都会返回false，全为空则返回true
    private static boolean isEmptyRow(Row dataRow) {
        //7.1根据表格行头获取表格有效列的列数
        int lastCellNum = dataRow.getLastCellNum();
        //7.2循环判断每一列的列值是否为空
        for (int i = 0; i <lastCellNum ; i++) {
            //7.3获取第i列，并且设定第i列如果检测到null值，设置为空处理
            Cell cell = dataRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            //7.4设置列值为String数据类型
            cell.setCellType(CellType.STRING);
            //7.5获取列值
            String stringCellValue = cell.getStringCellValue();
            //7.6对列值进行空值判断，如果不为空，返回false
            if (stringCellValue !=null && stringCellValue.trim().length()>0){
                return false;
            }
        }
        return true;
    }

}
