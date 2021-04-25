package com.company;

public class CoffeeFilter {

    private Integer cost;
    private String country;
    private String name;
    private String sort;
    private Integer strength;

    public CoffeeFilter() {}
    public CoffeeFilter(String name, String country, Integer cost, String sort, Integer strength) {
        this.cost = cost;
        this.country = country;
        this.name = name;
        this.sort = sort;
        this.strength = strength;
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
}
