package com.spring.boot.mybatis.demo.controller;

import javafx.application.Application;
import org.apache.catalina.core.ApplicationContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @auther xuxq
 * @date 2019/1/31 9:04
 */


public class dom4jTest extends BaseTest {

    @Test
    public void getVoid() throws DocumentException {
        //获取解析器
        SAXReader saxReader = new SAXReader();
        //获取document文档对象
        Document document = saxReader.read("C:\\Users\\sony\\Desktop\\whzy.xml");
        //获取文档根元素
        Element rootElement = document.getRootElement();
        //根元素名称
        System.out.println(rootElement.getName());
        //获取根元素中的属性值
        System.out.println(rootElement.attributeValue("version"));

        //获取根元素下单子元素
        List<Element> elements = rootElement.elements();
        //遍历子元素
        for (Element element: elements) {
            if ("servlet".equals(element.getName())) {
                Element servletName = element.element("servlet-name");
                Element servletClass = element.element("servlet-class");
                System.out.println(servletName.getText());
                System.out.println(servletClass.getText());

            }
        }

    }
    //java.lang.IllegalStateException: Unable to find a @SpringBootConfiguration, you need to use
    //@ContextConfiguration or @SpringBootTest(classes=...) with your test

}
