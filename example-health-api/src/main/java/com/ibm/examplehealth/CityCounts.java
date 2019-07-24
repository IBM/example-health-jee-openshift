package com.ibm.examplehealth;
import javax.json.bind.annotation.JsonbProperty;

public class CityCounts {

    private String city;
    private String postcode;
    private Long population;

    public CityCounts(String city, String postcode, Long population) {
        this.city = city;
        this.postcode = postcode;
        this.population = population;
    }

    @JsonbProperty("CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @JsonbProperty("POSTCODE")
    public String getPostcode() {
        return postcode;
    }
    
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @JsonbProperty("NUM_IN_CITY")
    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }
}