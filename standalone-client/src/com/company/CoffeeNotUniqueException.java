
package com.company;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "CoffeeNotUniqueException", targetNamespace = "http://company.com/")
public class CoffeeNotUniqueException
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private CoffeeNotUniqueFault faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public CoffeeNotUniqueException(String message, CoffeeNotUniqueFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public CoffeeNotUniqueException(String message, CoffeeNotUniqueFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.company.CoffeeNotUniqueFault
     */
    public CoffeeNotUniqueFault getFaultInfo() {
        return faultInfo;
    }

}
