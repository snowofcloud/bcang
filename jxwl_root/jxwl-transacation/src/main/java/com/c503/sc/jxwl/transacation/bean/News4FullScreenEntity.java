/**
 * 文件名：LeaveMessageEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.bean;

import java.text.ParseException;
import java.util.Date;

import com.c503.sc.base.entity.BaseEntity;
import com.c503.sc.utils.basetools.C503DateUtils;

/**
 * 
 * 新闻对象，应用于大屏展示
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-9-20]
 * @see
 * @since
 */
public class News4FullScreenEntity extends BaseEntity implements Comparable {

	/*
	 * public static void main(String[] args) throws ParseException { String str
	 * = "2017-03-30"; String dateStr = "2016-07-30"; Date date =
	 * C503DateUtils.strToDate(str,"yyyy-MM-dd"); Date date1 =
	 * C503DateUtils.strToDate(dateStr,"yyyy-MM-dd");
	 * System.out.println(date.compareTo(date1)); }
	 */
	/** 序列号 */
	private static final long serialVersionUID = 8468325966364289479L;

	/** ID */
	private String id;

	/** 题目 */
	private String title;

	/** 链接 */
	private String url;

	/** 时间 */
	private String date;

	/**
	 * 〈一句话功能简述〉获取id 〈功能详细描述〉
	 * 
	 * @return id
	 * @see [类、类#方法、类#成员]
	 */
	public String getId() {
		return id;
	}

	/**
	 * 〈一句话功能简述〉设置id 〈功能详细描述〉
	 * 
	 * @param id
	 *            id
	 * @see [类、类#方法、类#成员]
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 〈一句话功能简述〉获取title 〈功能详细描述〉
	 * 
	 * @return title
	 * @see [类、类#方法、类#成员]
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 〈一句话功能简述〉设置title 〈功能详细描述〉
	 * 
	 * @param title
	 *            title
	 * @see [类、类#方法、类#成员]
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 〈一句话功能简述〉获取url 〈功能详细描述〉
	 * 
	 * @return url
	 * @see [类、类#方法、类#成员]
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 〈一句话功能简述〉设置url 〈功能详细描述〉
	 * 
	 * @param url
	 *            url
	 * @see [类、类#方法、类#成员]
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 〈一句话功能简述〉获取date 〈功能详细描述〉
	 * 
	 * @return date
	 * @see [类、类#方法、类#成员]
	 */
	public String getDate() {
		return date;
	}

	/**
	 * 〈一句话功能简述〉设置date 〈功能详细描述〉
	 * 
	 * @param date
	 *            date
	 * @see [类、类#方法、类#成员]
	 */
	public void setDate(String date) {
		this.date = date;
	}

	@Override
	protected Object getBaseEntity() {
		return this;
	}

	@Override
	public int compareTo(Object o) {
		News4FullScreenEntity entity = (News4FullScreenEntity) o;
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = C503DateUtils.strToDate(this.date, "yyyy-MM-dd");
			date2 = C503DateUtils.strToDate(entity.getDate(), "yyyy-MM-dd");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return date1.compareTo(date2);
	}
}
