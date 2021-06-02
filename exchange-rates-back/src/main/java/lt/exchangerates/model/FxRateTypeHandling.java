//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.06.03 at 12:06:23 AM EEST 
//


package lt.exchangerates.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FxRateTypeHandling.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FxRateTypeHandling"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="LT"/&gt;
 *     &lt;enumeration value="EU"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "FxRateTypeHandling")
@XmlEnum
public enum FxRateTypeHandling {


    /**
     * Foreign exchange rate in accordance to the Law on Accounting
     * 
     */
    LT,

    /**
     * Foreign exchange rate by date of publication
     * 
     */
    EU;

    public String value() {
        return name();
    }

    public static FxRateTypeHandling fromValue(String v) {
        return valueOf(v);
    }

}
