
package com.company;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createCoffee complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createCoffee">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="model" type="{http://company.com/}createOrUpdateCoffeeRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createCoffee", propOrder = {
    "model"
})
public class CreateCoffee {

    protected CreateOrUpdateCoffeeRequest model;

    /**
     * Gets the value of the model property.
     * 
     * @return
     *     possible object is
     *     {@link CreateOrUpdateCoffeeRequest }
     *     
     */
    public CreateOrUpdateCoffeeRequest getModel() {
        return model;
    }

    /**
     * Sets the value of the model property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateOrUpdateCoffeeRequest }
     *     
     */
    public void setModel(CreateOrUpdateCoffeeRequest value) {
        this.model = value;
    }

}