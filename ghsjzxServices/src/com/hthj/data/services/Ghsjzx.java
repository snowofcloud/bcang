
package com.hthj.data.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "Ghsjzx", targetNamespace = "http://service.zjygj.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Ghsjzx {


    /**
     * 
     * @param password
     * @param param
     * @param pageSize
     * @param page
     * @param interfaceId
     * @param userName
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "GhsjzxService")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "GhsjzxService", targetNamespace = "http://service.zjygj.com/", className = "com.hthj.data.services.GhsjzxService")
    @ResponseWrapper(localName = "GhsjzxServiceResponse", targetNamespace = "http://service.zjygj.com/", className = "com.hthj.data.services.GhsjzxServiceResponse")
    public String ghsjzxService(
            @WebParam(name = "userName", targetNamespace = "")
                    String userName,
            @WebParam(name = "password", targetNamespace = "")
                    String password,
            @WebParam(name = "page", targetNamespace = "")
                    int page,
            @WebParam(name = "pageSize", targetNamespace = "")
                    int pageSize,
            @WebParam(name = "interfaceId", targetNamespace = "")
                    String interfaceId,
            @WebParam(name = "param", targetNamespace = "")
                    String param);

}