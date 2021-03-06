
package com.company;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "CoffeeEEWebService", targetNamespace = "http://company.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface CoffeeEEWebService {


    /**
     * 
     * @param filter
     * @return
     *     returns java.util.List<com.company.Coffee>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getFilteredCoffees", targetNamespace = "http://company.com/", className = "com.company.GetFilteredCoffees")
    @ResponseWrapper(localName = "getFilteredCoffeesResponse", targetNamespace = "http://company.com/", className = "com.company.GetFilteredCoffeesResponse")
    @Action(input = "http://company.com/CoffeeEEWebService/getFilteredCoffeesRequest", output = "http://company.com/CoffeeEEWebService/getFilteredCoffeesResponse")
    public List<Coffee> getFilteredCoffees(
        @WebParam(name = "filter", targetNamespace = "")
        CoffeeFilter filter);

    /**
     * 
     * @return
     *     returns java.util.List<com.company.Coffee>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCoffees", targetNamespace = "http://company.com/", className = "com.company.GetCoffees")
    @ResponseWrapper(localName = "getCoffeesResponse", targetNamespace = "http://company.com/", className = "com.company.GetCoffeesResponse")
    @Action(input = "http://company.com/CoffeeEEWebService/getCoffeesRequest", output = "http://company.com/CoffeeEEWebService/getCoffeesResponse")
    public List<Coffee> getCoffees();

}
