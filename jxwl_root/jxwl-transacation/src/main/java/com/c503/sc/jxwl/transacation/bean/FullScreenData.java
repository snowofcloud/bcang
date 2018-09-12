package com.c503.sc.jxwl.transacation.bean;

import java.util.Date;

import com.c503.sc.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

public class FullScreenData extends BaseEntity{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2703929643495276068L;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date release_date;
	
	private String title;
	
	

	private String title_img_path;
	
	
	public Date getRelease_date() {
		return release_date;
	}


	public void setRelease_date(Date release_date) {
		this.release_date = release_date;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getTitle_img_path() {
		return title_img_path;
	}


	public void setTitle_img_path(String title_img_path) {
		this.title_img_path = title_img_path;
	}


	@Override
	protected Object getBaseEntity() {
		return this;
	}
	
	@Override
	public String toString() {
		return "FullScreenData [release_date=" + release_date + ", title="
				+ title + ", title_img_path=" + title_img_path + "]";
	}


}
