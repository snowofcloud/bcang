package com.c503.sc.jxwl.common.login;

import org.springframework.stereotype.Component;

@Component
public class AccountValue {
	private static String account = null;
	
	private static String carrierName = null;
	
	public static String getCarrierName() {
		return carrierName;
	}

	public static void setCarrierName(String carrierName) {
		AccountValue.carrierName = carrierName;
	}

	public static String getAccount() {
		return account;
	}

	public static void setAccount(String account) {
		AccountValue.account = account;
	}
	public static boolean isAccountNull(){
		return account==null?true:false;
	}
	public static boolean isCarrierNameNull(){
		return carrierName==null?true:false;
	}
}
