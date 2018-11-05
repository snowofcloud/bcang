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
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

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
//		Date utilDate = df.parse(format);
//		System.out.println(utilDate);



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
//		double d=0.21343254;
//		//获取格式化对象
//		NumberFormat nt = NumberFormat.getPercentInstance();
//		//设置百分数精确度2即保留两位小数
//		nt.setMinimumFractionDigits(2);
//
//		String format = nt.format(d);
//		System.out.println(format);

		//整型相除取小数
//		int a = 1;
//		int b = 100;
//		double percent = (double)a/b;
//		System.out.println(percent);


		// BigDecimal相除取小数
//		BigDecimal a = BigDecimal.valueOf(1).divide(BigDecimal.valueOf(3),6,BigDecimal.ROUND_HALF_UP);
//		double v = a.doubleValue();
//		System.out.println(v);


		//String 转BigDecimal
//		String str1="2.30";
//		BigDecimal bd=new BigDecimal(str1);
//		System.out.println(bd);


//		//switch()语句应用
//		Scanner sc = new Scanner(System.in);
//		int month = sc.nextInt();
//		switch(month) {
//			case 1://case穿透作用
//			case 2:
//			case 12:
//				System.out.println("冬季");
//				break;
//			case 3:
//			case 4:
//			case 5:
//				System.out.println("春季");
//				break;
//			case 6:
//			case 7:
//			case 8:
//				System.out.println("夏季");
//				break;
//			case 9:
//			case 10:
//			case 11:
//				System.out.println("秋季");
//				break;
//			default:
//				System.out.println("你输入的月份有误");
//		}
		int[] i = new int[8];
		int[] j = new int[10];
		Arrays.fill(i,11);
		System.out.println("i="+i.toString());//方法不对
		System.out.println("ii="+Arrays.toString(i));
		Arrays.fill(j,88);
		System.out.println("j="+j.toString());
		System.out.println("jj="+Arrays.toString(j));
		//复制数组
		System.arraycopy(i,0,j,0,i.length);
		System.out.println("jjj="+Arrays.toString(j));
		int[] k = new int[12];
		Arrays.fill(k,122);
		System.out.println("k="+Arrays.toString(k));
		System.arraycopy(i,0,k,1,5);
		System.out.println("k="+Arrays.toString(k));




	}


}
