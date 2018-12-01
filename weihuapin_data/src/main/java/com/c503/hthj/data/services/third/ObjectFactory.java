
package com.c503.hthj.data.services.third;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.heqin package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _QueryWorkInfo_QNAME = new QName("http://service.webservice.visionagent.com/", "queryWorkInfo");
    private final static QName _GetAllWorkInfoResponse_QNAME = new QName("http://service.webservice.visionagent.com/", "getAllWorkInfoResponse");
    private final static QName _QueryWorkInfoResponse_QNAME = new QName("http://service.webservice.visionagent.com/", "queryWorkInfoResponse");
    private final static QName _GetAllShipInfo_QNAME = new QName("http://service.webservice.visionagent.com/", "getAllShipInfo");
    private final static QName _GetAllWorkResponse_QNAME = new QName("http://service.webservice.visionagent.com/", "getAllWorkResponse");
    private final static QName _QueryShiInfoByNameResponse_QNAME = new QName("http://service.webservice.visionagent.com/", "queryShiInfoByNameResponse");
    private final static QName _GetAllWorkInfo_QNAME = new QName("http://service.webservice.visionagent.com/", "getAllWorkInfo");
    private final static QName _GetAllShipInfoResponse_QNAME = new QName("http://service.webservice.visionagent.com/", "getAllShipInfoResponse");
    private final static QName _GetAllWork_QNAME = new QName("http://service.webservice.visionagent.com/", "getAllWork");
    private final static QName _QueryShiInfoByName_QNAME = new QName("http://service.webservice.visionagent.com/", "queryShiInfoByName");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.heqin
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryWorkInfo }
     * 
     */
    public QueryWorkInfo createQueryWorkInfo() {
        return new QueryWorkInfo();
    }

    /**
     * Create an instance of {@link GetAllWorkInfoResponse }
     * 
     */
    public GetAllWorkInfoResponse createGetAllWorkInfoResponse() {
        return new GetAllWorkInfoResponse();
    }

    /**
     * Create an instance of {@link QueryWorkInfoResponse }
     * 
     */
    public QueryWorkInfoResponse createQueryWorkInfoResponse() {
        return new QueryWorkInfoResponse();
    }

    /**
     * Create an instance of {@link GetAllWorkResponse }
     * 
     */
    public GetAllWorkResponse createGetAllWorkResponse() {
        return new GetAllWorkResponse();
    }

    /**
     * Create an instance of {@link QueryShiInfoByNameResponse }
     * 
     */
    public QueryShiInfoByNameResponse createQueryShiInfoByNameResponse() {
        return new QueryShiInfoByNameResponse();
    }

    /**
     * Create an instance of {@link GetAllShipInfo }
     * 
     */
    public GetAllShipInfo createGetAllShipInfo() {
        return new GetAllShipInfo();
    }

    /**
     * Create an instance of {@link GetAllWorkInfo }
     * 
     */
    public GetAllWorkInfo createGetAllWorkInfo() {
        return new GetAllWorkInfo();
    }

    /**
     * Create an instance of {@link GetAllShipInfoResponse }
     * 
     */
    public GetAllShipInfoResponse createGetAllShipInfoResponse() {
        return new GetAllShipInfoResponse();
    }

    /**
     * Create an instance of {@link GetAllWork }
     * 
     */
    public GetAllWork createGetAllWork() {
        return new GetAllWork();
    }

    /**
     * Create an instance of {@link QueryShiInfoByName }
     * 
     */
    public QueryShiInfoByName createQueryShiInfoByName() {
        return new QueryShiInfoByName();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryWorkInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.visionagent.com/", name = "queryWorkInfo")
    public JAXBElement<QueryWorkInfo> createQueryWorkInfo(QueryWorkInfo value) {
        return new JAXBElement<QueryWorkInfo>(_QueryWorkInfo_QNAME, QueryWorkInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllWorkInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.visionagent.com/", name = "getAllWorkInfoResponse")
    public JAXBElement<GetAllWorkInfoResponse> createGetAllWorkInfoResponse(GetAllWorkInfoResponse value) {
        return new JAXBElement<GetAllWorkInfoResponse>(_GetAllWorkInfoResponse_QNAME, GetAllWorkInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryWorkInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.visionagent.com/", name = "queryWorkInfoResponse")
    public JAXBElement<QueryWorkInfoResponse> createQueryWorkInfoResponse(QueryWorkInfoResponse value) {
        return new JAXBElement<QueryWorkInfoResponse>(_QueryWorkInfoResponse_QNAME, QueryWorkInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllShipInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.visionagent.com/", name = "getAllShipInfo")
    public JAXBElement<GetAllShipInfo> createGetAllShipInfo(GetAllShipInfo value) {
        return new JAXBElement<GetAllShipInfo>(_GetAllShipInfo_QNAME, GetAllShipInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllWorkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.visionagent.com/", name = "getAllWorkResponse")
    public JAXBElement<GetAllWorkResponse> createGetAllWorkResponse(GetAllWorkResponse value) {
        return new JAXBElement<GetAllWorkResponse>(_GetAllWorkResponse_QNAME, GetAllWorkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryShiInfoByNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.visionagent.com/", name = "queryShiInfoByNameResponse")
    public JAXBElement<QueryShiInfoByNameResponse> createQueryShiInfoByNameResponse(QueryShiInfoByNameResponse value) {
        return new JAXBElement<QueryShiInfoByNameResponse>(_QueryShiInfoByNameResponse_QNAME, QueryShiInfoByNameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllWorkInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.visionagent.com/", name = "getAllWorkInfo")
    public JAXBElement<GetAllWorkInfo> createGetAllWorkInfo(GetAllWorkInfo value) {
        return new JAXBElement<GetAllWorkInfo>(_GetAllWorkInfo_QNAME, GetAllWorkInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllShipInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.visionagent.com/", name = "getAllShipInfoResponse")
    public JAXBElement<GetAllShipInfoResponse> createGetAllShipInfoResponse(GetAllShipInfoResponse value) {
        return new JAXBElement<GetAllShipInfoResponse>(_GetAllShipInfoResponse_QNAME, GetAllShipInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllWork }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.visionagent.com/", name = "getAllWork")
    public JAXBElement<GetAllWork> createGetAllWork(GetAllWork value) {
        return new JAXBElement<GetAllWork>(_GetAllWork_QNAME, GetAllWork.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryShiInfoByName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.webservice.visionagent.com/", name = "queryShiInfoByName")
    public JAXBElement<QueryShiInfoByName> createQueryShiInfoByName(QueryShiInfoByName value) {
        return new JAXBElement<QueryShiInfoByName>(_QueryShiInfoByName_QNAME, QueryShiInfoByName.class, null, value);
    }

}
