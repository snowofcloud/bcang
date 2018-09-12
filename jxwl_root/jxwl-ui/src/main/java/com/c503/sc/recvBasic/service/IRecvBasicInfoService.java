package com.c503.sc.recvBasic.service;

/**
 * 同步基础数据到业务系统中的t_enterprise、和auth系统中的t_sys_organization
 * 
 * @author Administrator
 */
public interface IRecvBasicInfoService {

	/**
	 * 
	 * @throws Exception
	 */
	void syncBasicData() throws Exception;
}
