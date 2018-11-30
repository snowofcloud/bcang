
package com.c503.hthj.data.services.second;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.xuxueqiang package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CbjbxxService_QNAME = new QName("http://service.zjygj.com/",
            "CbjbxxService");
    private final static QName _CbjbxxServiceResponse_QNAME = new QName("http://service.zjygj.com/",
            "CbjbxxServiceResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xuxueqiang
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CbjbxxService }
     */
    public CbjbxxService createCbjbxxService() {
        return new CbjbxxService();
    }

    /**
     * Create an instance of {@link CbjbxxServiceResponse }
     */
    public CbjbxxServiceResponse createCbjbxxServiceResponse() {
        return new CbjbxxServiceResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CbjbxxService }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.zjygj.com/", name = "CbjbxxService")
    public JAXBElement<CbjbxxService> createCbjbxxService(CbjbxxService value) {
        return new JAXBElement<CbjbxxService>(_CbjbxxService_QNAME, CbjbxxService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CbjbxxServiceResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.zjygj.com/", name = "CbjbxxServiceResponse")
    public JAXBElement<CbjbxxServiceResponse> createCbjbxxServiceResponse(CbjbxxServiceResponse value) {
        return new JAXBElement<CbjbxxServiceResponse>(_CbjbxxServiceResponse_QNAME, CbjbxxServiceResponse.class,
                null, value);
    }

}
