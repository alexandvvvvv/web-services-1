
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
@WebService(name = "CoffeeWebService", targetNamespace = "http://company.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface CoffeeWebService {


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
    @Action(input = "http://company.com/CoffeeWebService/getFilteredCoffeesRequest", output = "http://company.com/CoffeeWebService/getFilteredCoffeesResponse")
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
    @Action(input = "http://company.com/CoffeeWebService/getCoffeesRequest", output = "http://company.com/CoffeeWebService/getCoffeesResponse")
    public List<Coffee> getCoffees();

    /**
     * 
     * @param model
     * @return
     *     returns long
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "createCoffee", targetNamespace = "http://company.com/", className = "com.company.CreateCoffee")
    @ResponseWrapper(localName = "createCoffeeResponse", targetNamespace = "http://company.com/", className = "com.company.CreateCoffeeResponse")
    @Action(input = "http://company.com/CoffeeWebService/createCoffeeRequest", output = "http://company.com/CoffeeWebService/createCoffeeResponse")
    public long createCoffee(
        @WebParam(name = "model", targetNamespace = "")
        Coffee model);

    /**
     * 
     * @param id
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "deleteCoffee", targetNamespace = "http://company.com/", className = "com.company.DeleteCoffee")
    @ResponseWrapper(localName = "deleteCoffeeResponse", targetNamespace = "http://company.com/", className = "com.company.DeleteCoffeeResponse")
    @Action(input = "http://company.com/CoffeeWebService/deleteCoffeeRequest", output = "http://company.com/CoffeeWebService/deleteCoffeeResponse")
    public boolean deleteCoffee(
        @WebParam(name = "id", targetNamespace = "")
        int id);

    /**
     * 
     * @param model
     * @param id
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "updateCoffee", targetNamespace = "http://company.com/", className = "com.company.UpdateCoffee")
    @ResponseWrapper(localName = "updateCoffeeResponse", targetNamespace = "http://company.com/", className = "com.company.UpdateCoffeeResponse")
    @Action(input = "http://company.com/CoffeeWebService/updateCoffeeRequest", output = "http://company.com/CoffeeWebService/updateCoffeeResponse")
    public boolean updateCoffee(
        @WebParam(name = "id", targetNamespace = "")
        int id,
        @WebParam(name = "model", targetNamespace = "")
        Coffee model);

}
