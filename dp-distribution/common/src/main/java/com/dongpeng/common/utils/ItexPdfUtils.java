package com.dongpeng.common.utils;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * pdf工具类
 */
public class ItexPdfUtils {
    private static final Logger LOG=LoggerFactory.getLogger(ItexPdfUtils.class);

    public static ByteArrayOutputStream createPdfFromTemplate(String templatePath){
        try {
            //创建一个PdfReader 对象打开模板文件
            PdfReader pdfReader=new PdfReader(templatePath);

            //创建一个PdfStamper对象来向模板中添加数据
            //输出到文件
            String filename="test";
            PdfStamper pdfStamper=new PdfStamper(pdfReader, new FileOutputStream(filename));

            //输出到缓冲区
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            //PdfStamper pdfStamper=new PdfStamper(pdfReader, baos);

            //从PdfStamper对象中得到模板中全部的表单域
            AcroFields acroFields=pdfStamper.getAcroFields();

            //将数据填入模板
            acroFields.setField("", "");

            //去除pdf中的表单，这使的pdf文档不能再次编辑，但也减少了文档的大小
            pdfStamper.setFormFlattening(true);
            //关闭PdfStamper对象即在fileName路径处创建了pdf文档
            pdfStamper.close();
            pdfReader.close();

            return baos;
        } catch (IOException e) {
            LOG.error("创建PdfReader出错", e.getMessage());
            return null;
        } catch (DocumentException e) {
            LOG.error("创建PdfStamper异常", e.getMessage());
            return null;
        }
    }

    public static void main(String[] args){
        ItexPdfUtils.createPdfFromTemplate("");
    }
}
