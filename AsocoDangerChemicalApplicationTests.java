package com.c503.hthj.asoco.dangerchemical.waste;

import com.c503.hthj.asoco.dangerchemical.waste.entity.SludgeRecord;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AsocoDangerChemicalApplicationTests {

	@Test
	public void contextLoads() throws Exception {

		//获取当前系统时间
		//SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式

//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date date1 = new Date();
//		String format = df.format(date1);
//		System.out.println(format);//2018-11-10 15:06:49
//
//		Date date11 = date1;
//		System.out.println(date1);//Sat Nov 10 15:06:49 CST 2018
//
//		String s = date1.toString();
//		System.out.println(date1.toString());//Sat Nov 10 15:06:49 CST 2018

		//System.out.println("此刻时间："+df.format(date1));// new Date()为获取当前系统时间
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

		//复制数组，比较数组
//		int[] i = new int[8];
//		int[] j = new int[10];
//		Arrays.fill(i,11);
//		System.out.println("i="+i.toString());//方法不对
//		System.out.println("ii="+Arrays.toString(i));
//		Arrays.fill(j,88);
//		System.out.println("j="+j.toString());
//		System.out.println("jj="+Arrays.toString(j));
//		//复制数组
//		System.arraycopy(i,0,j,0,i.length);
//		System.out.println("jjj="+Arrays.toString(j));
//		int[] k = new int[12];
//		Arrays.fill(k,122);
//		System.out.println("k="+Arrays.toString(k));
//		System.arraycopy(i,0,k,1,5);
//		System.out.println("k="+Arrays.toString(k));

//		int[] i = new int[10];Arrays.fill(i,11);
//		int[] j = new int[10];Arrays.fill(j,11);
//		System.out.println(Arrays.equals(i,j));

		//生成表格
//		HSSFWorkbook workbook = new HSSFWorkbook();  // 创建一个excel
//		// excel生成过程: excel-->sheet-->row-->cell
//		HSSFSheet sheet = workbook.createSheet("test"); // 为excel创建一个名为test的sheet页
//
//		HSSFRow row = sheet.createRow(0); // 创建一行,
//
//		HSSFCell cellB2 = row.createCell(0); // 在B2位置创建一个单元格
//		HSSFCell cellB3 = row.createCell(1); // 在B3位置创建一个单元格
//		cellB2.setCellValue("单元格B2"); // B2单元格填充内容
//		cellB3.setCellValue("单元格B3"); // B3单元格填充内容
//
//		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 单元格样式
//		Font fontStyle = workbook.createFont(); // 字体样式
//		fontStyle.setBold(true); // 加粗
//		fontStyle.setFontName("黑体"); // 字体
//		fontStyle.setFontHeightInPoints((short) 11); // 大小
//		// 将字体样式添加到单元格样式中
//		cellStyle.setFont(fontStyle);
//		// 边框，居中
//		cellStyle.setAlignment(HorizontalAlignment.CENTER);
//		cellStyle.setBorderBottom(BorderStyle.THIN);
//		cellStyle.setBorderLeft(BorderStyle.THIN);
//		cellStyle.setBorderRight(BorderStyle.THIN);
//		cellStyle.setBorderTop(BorderStyle.THIN);
//		cellB2.setCellStyle(cellStyle); // 为B2单元格添加样式
//
//		// 合并单元格
//		CellRangeAddress cra =new CellRangeAddress(1, 3, 1, 3); // 起始行, 终止行, 起始列, 终止列
//		sheet.addMergedRegion(cra);
//
//		// 使用RegionUtil类为合并后的单元格添加边框
//		RegionUtil.setBorderBottom(1, cra, sheet); // 下边框
//		RegionUtil.setBorderLeft(1, cra, sheet); // 左边框
//		RegionUtil.setBorderRight(1, cra, sheet); // 有边框
//		RegionUtil.setBorderTop(1, cra, sheet); // 上边框
//
//		// 输出到本地
//		String excelName = "/myExcel.xls";
//		FileOutputStream out = null;
//		try {
//			out = new FileOutputStream(excelName);
//			workbook.write(out);
//			out.flush();
//			out.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (out != null)
//				try {
//					out.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			out = null;
//		}
		Date date = new Date();
		System.out.println(date.toString());
		Calendar c = Calendar.getInstance();
		c.setTime(date);   //设置当前日期
		c.add(Calendar.DATE, 1); //日期加1天
//     c.add(Calendar.DATE, -1); //日期减1天
		date = c.getTime();
		System.out.println(date.toString());


	}


}
