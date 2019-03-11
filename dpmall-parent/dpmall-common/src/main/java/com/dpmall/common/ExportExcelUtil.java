package com.dpmall.common;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExportExcelUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Logger LOG = Logger.getLogger(ExportExcelUtil.class);


    public static void exportExcle (String sheetName,Map<String, String> headline ,List<Map<String, Object>> contents,OutputStream out) throws IOException {

        //创建HSSFWorkbook对象(excel的文档对象)
        SXSSFWorkbook wb = new SXSSFWorkbook(1000);

        //建立新的sheet对象（excel的表单）
        SXSSFSheet sheet=wb.createSheet(sheetName);

        //单元格样式(表头)
        CellStyle headStyle = wb.createCellStyle();
        headStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

        //设置字体(表头字体)
        Font headFont = wb.createFont();
        headFont.setFontHeightInPoints((short) 12);//设置字体高度
        headFont.setColor(HSSFColor.BLACK.index);//设置颜色
        headFont.setFontName("微软雅黑");
        headStyle.setFont(headFont);//设置表头字体样式

        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        SXSSFRow row=sheet.createRow(0);
        //创建表头
        SXSSFCell cell=null;
        int index = 0;
        for (String head: headline.values()){
            cell = row.createCell(index);
            cell.setCellValue(head);
            cell.setCellStyle(headStyle);
            index++;
        }

        //设置字体(内容字体)
        Font  contentFont = wb.createFont();
        contentFont.setFontHeightInPoints((short) 10);//设置字体高度
        contentFont.setColor(HSSFColor.BLACK.index);//设置颜色
        contentFont.setFontName("微软雅黑");
        CellStyle contentStyle = wb.createCellStyle();
        contentStyle.setFont(contentFont);//设置内容字体样式

        LOG.info("------------StartTime:"+sdf.format(new Date())+"---------------");

        for (int i = 0;i<contents.size();i++){
            row = sheet.createRow(i+1);//从第二行开始创建
             Map<String, Object> rowData = contents.get(i);
             int j = 0;
            for (final String headkey : headline.keySet()) {//根据表头获取相关的值
                SXSSFCell cellcontent = row.createCell(j);
                if (rowData.get(headkey) != null) {
                    cellcontent.setCellStyle(contentStyle);
                    cellcontent.setCellValue(String.valueOf(rowData.get(headkey)));
                } else {
                    cellcontent.setCellValue("");
                }
                j++;
            }
        }

        LOG.info("导出" + sheetName + "成功！");
        LOG.info("------------endTime:"+sdf.format(new Date())+"---------------");

        try {
            wb.write(out);
        } catch (final IOException e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                LOG.error(e.getMessage());
            }
        }


//
//        // 第六步，将文件输出到指定位置
//        FileOutputStream stream = new FileOutputStream("C:/Users/Administrator/Desktop/"+sheetName+".xls");//本项目classpth的下面，即项目目录下面
//        wb.write(stream);
//        stream.close();

    }

}
