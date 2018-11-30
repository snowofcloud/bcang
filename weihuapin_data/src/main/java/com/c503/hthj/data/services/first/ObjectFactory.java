
package com.c503.hthj.data.services.first;

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
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GhsjzxService_QNAME = new QName("http://service.zjygj.com/", "GhsjzxService");
    private final static QName _GhsjzxServiceResponse_QNAME = new QName("http://service.zjygj.com/", "GhsjzxServiceResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.heqin
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GhsjzxService }
     */
    public GhsjzxService createGhsjzxService() {
        return new GhsjzxService();
    }

    /**
     * Create an instance of {@link GhsjzxServiceResponse }
     */
    public GhsjzxServiceResponse createGhsjzxServiceResponse() {
        return new GhsjzxServiceResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GhsjzxService }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.zjygj.com/", name = "GhsjzxService")
    public JAXBElement<GhsjzxService> createGhsjzxService(GhsjzxService value) {
        return new JAXBElement<GhsjzxService>(_GhsjzxService_QNAME, GhsjzxService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GhsjzxServiceResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.zjygj.com/", name = "GhsjzxServiceResponse")
    public JAXBElement<GhsjzxServiceResponse> createGhsjzxServiceResponse(GhsjzxServiceResponse value) {
        return new JAXBElement<GhsjzxServiceResponse>(_GhsjzxServiceResponse_QNAME, GhsjzxServiceResponse.class, null, value);
    }

}
