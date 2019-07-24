package com.ibm.summithealth;
import javax.json.bind.annotation.JsonbProperty;

/*
JSON response format:

  {
    "PATIENTID": 1,
    "FIRSTNAME": "Ralph               ",
    "LASTNAME": "Dalmeida            ",
    "APPT_DATE": "2019-02-21",
    "APPT_TIME": "02.00.00",
    "DR_NAME": "Aaron Stone                   ",
    "MED_FIELD": "Internal Medicine   ",
    "OFF_NAME": "Toronto Medical               ",
    "OFF_ADDR": "555 14th Street               ",
    "OFF_CITY": "Toronto             ",
    "OFF_STATE": "ON",
    "OFF_ZIP": "M5H 1T1   "
},
 */

 public class AppointmentList {

    private String PATIENTID;
    private String FIRSTNAME;
    private String LASTNAME;
    private String APPT_DATE;
    private String APPT_TIME;
    private String DR_NAME;
    private String MED_FIELD;
    private String OFF_NAME;
    private String OFF_ADDR;
    private String OFF_CITY;
    private String OFF_STATE;    
    private String OFF_ZIP;

    public AppointmentList(String patient_id, String first_name, String last_name, String date, String time,
            String doc_name, String field, String office_name, String office_addr, String office_city, String office_state,
            String office_zip) {
        PATIENTID = patient_id;
        FIRSTNAME = first_name;
        LASTNAME = last_name;
        APPT_DATE = date;
        APPT_TIME = time;
        DR_NAME = doc_name;
        MED_FIELD = field;
        OFF_NAME = office_name;
        OFF_ADDR = office_addr;
        OFF_CITY = office_city;
        OFF_STATE = office_state;
        OFF_ZIP = office_zip;
    }

    public String getPATIENTID() {
        return PATIENTID;
    }

    public void setPATIENTID(String pATIENTID) {
        PATIENTID = pATIENTID;
    }

    public String getFIRSTNAME() {
        return FIRSTNAME;
    }

    public void setFIRSTNAME(String fIRSTNAME) {
        FIRSTNAME = fIRSTNAME;
    }

    public String getLASTNAME() {
        return LASTNAME;
    }

    public void setLASTNAME(String lASTNAME) {
        LASTNAME = lASTNAME;
    }

    public String getAPPT_DATE() {
        return APPT_DATE;
    }

    public void setAPPT_DATE(String aPPT_DATE) {
        APPT_DATE = aPPT_DATE;
    }

    public String getAPPT_TIME() {
        return APPT_TIME;
    }

    public void setAPPT_TIME(String aPPT_TIME) {
        APPT_TIME = aPPT_TIME;
    }

    public String getDR_NAME() {
        return DR_NAME;
    }

    public void setDR_NAME(String dR_NAME) {
        DR_NAME = dR_NAME;
    }

    public String getMED_FIELD() {
        return MED_FIELD;
    }

    public void setMED_FIELD(String mED_FIELD) {
        MED_FIELD = mED_FIELD;
    }

    public String getOFF_NAME() {
        return OFF_NAME;
    }

    public void setOFF_NAME(String oFF_NAME) {
        OFF_NAME = oFF_NAME;
    }

    public String getOFF_ADDR() {
        return OFF_ADDR;
    }

    public void setOFF_ADDR(String oFF_ADDR) {
        OFF_ADDR = oFF_ADDR;
    }

    public String getOFF_CITY() {
        return OFF_CITY;
    }

    public void setOFF_CITY(String oFF_CITY) {
        OFF_CITY = oFF_CITY;
    }

    public String getOFF_STATE() {
        return OFF_STATE;
    }

    public void setOFF_STATE(String oFF_STATE) {
        OFF_STATE = oFF_STATE;
    }

    public String getOFF_ZIP() {
        return OFF_ZIP;
    }

    public void setOFF_ZIP(String oFF_ZIP) {
        OFF_ZIP = oFF_ZIP;
    }
}