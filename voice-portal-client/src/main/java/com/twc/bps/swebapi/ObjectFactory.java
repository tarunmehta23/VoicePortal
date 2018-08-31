
package com.twc.bps.swebapi;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.twc.bps.swebapi package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.twc.bps.swebapi
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Request }
     * 
     */
    public Request createRequest() {
        return new Request();
    }

    /**
     * Create an instance of {@link RequestResponse }
     * 
     */
    public RequestResponse createRequestResponse() {
        return new RequestResponse();
    }

    /**
     * Create an instance of {@link StartAsyncRequest }
     * 
     */
    public StartAsyncRequest createStartAsyncRequest() {
        return new StartAsyncRequest();
    }

    /**
     * Create an instance of {@link StartAsyncRequestResponse }
     * 
     */
    public StartAsyncRequestResponse createStartAsyncRequestResponse() {
        return new StartAsyncRequestResponse();
    }

}
