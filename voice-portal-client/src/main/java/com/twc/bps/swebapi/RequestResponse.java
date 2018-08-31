
package com.twc.bps.swebapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.twc.bps.webservice.WebapiResponse;


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
 *         &lt;element ref="{http://www.twc.com/BPS/WebService}webapiResponse"/&gt;
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
    "webapiResponse"
})
@XmlRootElement(name = "requestResponse")
public class RequestResponse {

    @XmlElement(namespace = "http://www.twc.com/BPS/WebService", required = true)
    protected WebapiResponse webapiResponse;

    /**
     * Gets the value of the webapiResponse property.
     * 
     * @return
     *     possible object is
     *     {@link WebapiResponse }
     *     
     */
    public WebapiResponse getWebapiResponse() {
        return webapiResponse;
    }

    /**
     * Sets the value of the webapiResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link WebapiResponse }
     *     
     */
    public void setWebapiResponse(WebapiResponse value) {
        this.webapiResponse = value;
    }

}
