
package com.twc.bps.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.twc.bps.webservice package. 
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

    private final static QName _AbstractObject_QNAME = new QName("http://www.twc.com/BPS/WebService", "AbstractObject");
    private final static QName _WebapiRequest_QNAME = new QName("http://www.twc.com/BPS/WebService", "webapiRequest");
    private final static QName _WebapiResponse_QNAME = new QName("http://www.twc.com/BPS/WebService", "webapiResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.twc.bps.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AbstractObjectList }
     * 
     */
    public AbstractObjectList createAbstractObjectList() {
        return new AbstractObjectList();
    }

    /**
     * Create an instance of {@link AbstractObject }
     * 
     */
    public AbstractObject createAbstractObject() {
        return new AbstractObject();
    }

    /**
     * Create an instance of {@link WebapiRequest }
     * 
     */
    public WebapiRequest createWebapiRequest() {
        return new WebapiRequest();
    }

    /**
     * Create an instance of {@link WebapiResponse }
     * 
     */
    public WebapiResponse createWebapiResponse() {
        return new WebapiResponse();
    }

    /**
     * Create an instance of {@link Property }
     * 
     */
    public Property createProperty() {
        return new Property();
    }

    /**
     * Create an instance of {@link Arguments }
     * 
     */
    public Arguments createArguments() {
        return new Arguments();
    }

    /**
     * Create an instance of {@link Data }
     * 
     */
    public Data createData() {
        return new Data();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.twc.com/BPS/WebService", name = "AbstractObject")
    public JAXBElement<AbstractObject> createAbstractObject(AbstractObject value) {
        return new JAXBElement<AbstractObject>(_AbstractObject_QNAME, AbstractObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebapiRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.twc.com/BPS/WebService", name = "webapiRequest")
    public JAXBElement<WebapiRequest> createWebapiRequest(WebapiRequest value) {
        return new JAXBElement<WebapiRequest>(_WebapiRequest_QNAME, WebapiRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WebapiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.twc.com/BPS/WebService", name = "webapiResponse")
    public JAXBElement<WebapiResponse> createWebapiResponse(WebapiResponse value) {
        return new JAXBElement<WebapiResponse>(_WebapiResponse_QNAME, WebapiResponse.class, null, value);
    }

}
