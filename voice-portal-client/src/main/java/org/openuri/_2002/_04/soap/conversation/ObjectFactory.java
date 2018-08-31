
package org.openuri._2002._04.soap.conversation;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.openuri._2002._04.soap.conversation package. 
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

    private final static QName _StartHeader_QNAME = new QName("http://www.openuri.org/2002/04/soap/conversation/", "StartHeader");
    private final static QName _ContinueHeader_QNAME = new QName("http://www.openuri.org/2002/04/soap/conversation/", "ContinueHeader");
    private final static QName _CallbackHeader_QNAME = new QName("http://www.openuri.org/2002/04/soap/conversation/", "CallbackHeader");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.openuri._2002._04.soap.conversation
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link StartHeader }
     * 
     */
    public StartHeader createStartHeader() {
        return new StartHeader();
    }

    /**
     * Create an instance of {@link ContinueHeader }
     * 
     */
    public ContinueHeader createContinueHeader() {
        return new ContinueHeader();
    }

    /**
     * Create an instance of {@link CallbackHeader }
     * 
     */
    public CallbackHeader createCallbackHeader() {
        return new CallbackHeader();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StartHeader }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.openuri.org/2002/04/soap/conversation/", name = "StartHeader")
    public JAXBElement<StartHeader> createStartHeader(StartHeader value) {
        return new JAXBElement<StartHeader>(_StartHeader_QNAME, StartHeader.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContinueHeader }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.openuri.org/2002/04/soap/conversation/", name = "ContinueHeader")
    public JAXBElement<ContinueHeader> createContinueHeader(ContinueHeader value) {
        return new JAXBElement<ContinueHeader>(_ContinueHeader_QNAME, ContinueHeader.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CallbackHeader }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.openuri.org/2002/04/soap/conversation/", name = "CallbackHeader")
    public JAXBElement<CallbackHeader> createCallbackHeader(CallbackHeader value) {
        return new JAXBElement<CallbackHeader>(_CallbackHeader_QNAME, CallbackHeader.class, null, value);
    }

}
