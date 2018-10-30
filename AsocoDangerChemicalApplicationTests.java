package com.c503.hthj.asoco.dangerchemical.waste;

import com.c503.hthj.asoco.dangerchemical.waste.entity.SludgeRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AsocoDangerChemicalApplicationTests {

	@Test
	public void contextLoads() throws Exception {

		//获取当前系统时间
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
//		Date date1 = new Date();
//		//String format = df.format(date1);
//
//		System.out.println("此刻时间："+df.format(date1));// new Date()为获取当前系统时间
		//20181029201410
		//20181029201519

		//日期转换
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//		Date date1 = new Date();
//		String format = df.format(date1);
//
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date utilDate = sdf.parse(format);
//		eco.setTransferTime(utilDate);



//		String time = "2017-10-19";
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//yyyy-mm-dd, 会出现时间不对, 因为小写的mm是代表: 秒
//		Date utilDate = sdf.parse(time);
//		System.out.println(utilDate);//查看utilDate的值
//		Date date = new java.sql.Date(utilDate.getTime());
//		System.out.println(date);//查看date的值
		/**
		 * BigDecimal加法应用
		 * */
//		BigDecimal AAA = new BigDecimal("36.21202" );
//		AAA=AAA.add(new BigDecimal("45.14456"));
//		System.out.println(AAA);

//		final String A="1234";
//		System.out.println(A);

		//位数不够 高位自动补0
//		int str1=102;
//		DecimalFormat df=new DecimalFormat("00000");
//		String str2=df.format(str1);
//		System.out.println(str2);

		//取百分数
		double d=0.21343254;
		//获取格式化对象
		NumberFormat nt = NumberFormat.getPercentInstance();
		//设置百分数精确度2即保留两位小数
		nt.setMinimumFractionDigits(2);

		String format = nt.format(d);
		System.out.println(format);
		
		//取小数
		int a = 1;
		int b = 100;
		double percent = (double)a/b;
		System.out.println(percent);


	}


}
