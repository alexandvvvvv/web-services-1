package com.company;

public class CreateOrUpdateCoffeeRequest {

    private Integer cost;
    private String country;
    private String name;
    private String sort;
    private Integer strength;
    private String image;

    public CreateOrUpdateCoffeeRequest(){}
    public CreateOrUpdateCoffeeRequest(String name, String country, int cost, String sort, int strength, String image) {
        this.cost = cost;
        this.country = country;
        this.name = name;
        this.sort = sort;
        this.strength = strength;
        this.image = image;
    }


    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
