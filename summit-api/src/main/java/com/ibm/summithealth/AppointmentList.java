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

    public AppointmentList(String pATIENTID, String fIRSTNAME, String lASTNAME, String aPPT_DATE, String aPPT_TIME,
            String dR_NAME, String mED_FIELD, String oFF_NAME, String oFF_ADDR, String oFF_CITY, String oFF_STATE,
            String oFF_ZIP) {
        PATIENTID = pATIENTID;
        FIRSTNAME = fIRSTNAME;
        LASTNAME = lASTNAME;
        APPT_DATE = aPPT_DATE;
        APPT_TIME = aPPT_TIME;
        DR_NAME = dR_NAME;
        MED_FIELD = mED_FIELD;
        OFF_NAME = oFF_NAME;
        OFF_ADDR = oFF_ADDR;
        OFF_CITY = oFF_CITY;
        OFF_STATE = oFF_STATE;
        OFF_ZIP = oFF_ZIP;
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