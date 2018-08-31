
package com.twc.bps.webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RequestMode"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;whiteSpace value="collapse"/&gt;
 *     &lt;enumeration value="asynchronous"/&gt;
 *     &lt;enumeration value="synchronous"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "RequestMode")
@XmlEnum
public enum RequestMode {

    @XmlEnumValue("asynchronous")
    ASYNCHRONOUS("asynchronous"),
    @XmlEnumValue("synchronous")
    SYNCHRONOUS("synchronous");
    private final String value;

    RequestMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RequestMode fromValue(String v) {
        for (RequestMode c: RequestMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
