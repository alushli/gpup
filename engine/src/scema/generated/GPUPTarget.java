//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.11.09 at 09:32:12 AM IST 
//


package scema.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element ref="{}GPUP-User-Data" minOccurs="0"/>
 *         &lt;element ref="{}GPUP-Target-Dependencies" minOccurs="0"/>
 *       &lt;/all>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "GPUP-Target")
public class GPUPTarget {

    @XmlElement(name = "GPUP-User-Data")
    protected String gpupUserData;
    @XmlElement(name = "GPUP-Target-Dependencies")
    protected GPUPTargetDependencies gpupTargetDependencies;
    @XmlAttribute(name = "name", required = true)
    protected String name;

    /**
     * Gets the value of the gpupUserData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGPUPUserData() {
        return gpupUserData;
    }

    /**
     * Sets the value of the gpupUserData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGPUPUserData(String value) {
        this.gpupUserData = value;
    }

    /**
     * Gets the value of the gpupTargetDependencies property.
     * 
     * @return
     *     possible object is
     *     {@link GPUPTargetDependencies }
     *     
     */
    public GPUPTargetDependencies getGPUPTargetDependencies() {
        return gpupTargetDependencies;
    }

    /**
     * Sets the value of the gpupTargetDependencies property.
     * 
     * @param value
     *     allowed object is
     *     {@link GPUPTargetDependencies }
     *     
     */
    public void setGPUPTargetDependencies(GPUPTargetDependencies value) {
        this.gpupTargetDependencies = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
