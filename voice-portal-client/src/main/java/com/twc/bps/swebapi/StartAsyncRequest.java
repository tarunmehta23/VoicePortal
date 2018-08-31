
package com.twc.bps.swebapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.twc.bps.webservice.WebapiRequest;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.twc.com/BPS/WebService}webapiRequest"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "webapiRequest"
})
@XmlRootElement(name = "startAsyncRequest")
public class StartAsyncRequest {

    @XmlElement(namespace = "http://www.twc.com/BPS/WebService", required = true)
    protected WebapiRequest webapiRequest;

    /**
     * Gets the value of the webapiRequest property.
     * 
     * @return
     *     possible object is
     *     {@link WebapiRequest }
     *     
     */
    public WebapiRequest getWebapiRequest() {
        return webapiRequest;
    }

    /**
     * Sets the value of the webapiRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link WebapiRequest }
     *     
     */
    public void setWebapiRequest(WebapiRequest value) {
        this.webapiRequest = value;
    }

}
