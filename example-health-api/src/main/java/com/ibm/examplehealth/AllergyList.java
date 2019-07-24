package com.ibm.examplehealth;
import javax.json.bind.annotation.JsonbProperty;

public class AllergyList {

    private String patient_id;
    private String birthdate;
    private String city;
    private String postcode;
    private String description;
    private String start_date;
    private String stop_date;

    public AllergyList(String patient_id, String birthdate, String city, String postcode, String description,
            String start_date, String stop_date) {
        this.patient_id = patient_id;
        this.birthdate = birthdate;
        this.city = city;
        this.postcode = postcode;
        this.description = description;
        this.start_date = start_date;
        this.stop_date = stop_date;
    }

    @JsonbProperty("PATIENT_NUM")
    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    @JsonbProperty("BIRTHDATE")
    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
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

    @JsonbProperty("DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonbProperty("ALLERGY_START")
    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    @JsonbProperty("ALLERGY_STOP")
    public String getStop_date() {
        return stop_date;
    }

    public void setStop_date(String stop_date) {
        this.stop_date = stop_date;
    }
}