 
package com.zx.data.service;


import javax.jws.WebService;
import java.util.Map;

 
 

 
@WebService
public interface AutoWebService {

	public String[] execute(Map<String, Object> map);

}
