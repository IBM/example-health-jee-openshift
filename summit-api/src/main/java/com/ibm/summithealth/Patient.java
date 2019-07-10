package com.ibm.summithealth;

import java.io.Serializable;
import javax.persistence.*;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

/**
 * The persistent class for the Patients database table.
 * 
 *
 * 
{"Id":"20c47f5f-a24d-4efe-9eb9-5acf421d1958",
 * "BIRTHDATE":"1977-12-12",
 * "DEATHDATE":"",
 * "SSN":"999-44-8397",
 * "DRIVERS":"S99965159",
 * "PASSPORT":"X50372231X",
 * "PREFIX":"Mr.",
 * "FIRST":"Floyd420",
 * "LAST":"Jenkins714",
 * "SUFFIX":"",
 * "MAIDEN":"",
 * "MARITAL":"M",
 * "RACE":"white",
 * "ETHNICITY":"irish",
 * "GENDER":"M",
 * "BIRTHPLACE":"San Jose  California  US",
 * "ADDRESS":"1045 Dibbert Well",
 * "CITY":"Fresno",
 * "STATE":"California",
 * "ZIP":"93611"},
 *
 *    "HCCMAREA": {
        "CA_REQUEST_ID": "01IPAT",
        "CA_RETURN_CODE": 0,
        "CA_PATIENT_ID": 1,
        "CA_PATIENT_REQUEST": {
            "CA_INS_CARD_NUM": "9627811234",
            "CA_FIRST_NAME": "Ralph",
            "CA_LAST_NAME": "DAlmeida",
            "CA_DOB": "1980-07-11",
            "CA_ADDRESS": "34 Main Street",
            "CA_CITY": "Toronto",
            "CA_POSTCODE": "M5H 1T1",
            "CA_PHONE_MOBILE": "077-123-9987",
            "CA_EMAIL_ADDRESS": "RalphD@ibm.com",
            "CA_USERID": "ralphd",
            "CA_ADDITIONAL_DATA": ""
        }
    }
}
 */
@Entity
@Table(name="Patients")
@NamedQuery(name="Patient.countAll", query="SELECT count(p) FROM Patient p")
@NamedQuery(name="Patient.findAll", query="SELECT p FROM Patient p")
@NamedQuery(name="Patient.getPatients", query="SELECT NEW com.ibm.summithealth.Patient(p.firstName, p.lastName, p.gender, p.birthdate) FROM Patient p")
@NamedQuery(name = "Patient.findPatient", query = "SELECT p FROM Patient p WHERE "
	+ "p.patientId = :pid")
@NamedQuery(name="Patient.login", query="SELECT p FROM Patient p WHERE p.userId=:userId AND p.password = :password")
@NamedQuery(name="Patient.populations", query="select p.postcode, p.city, count(p) from Patient p group by p.city, p.postcode")
@NamedQuery(name="Patient.getPop", query="SELECT NEW com.ibm.summithealth.CityCounts(p.city, p.postcode, count(p)) FROM Patient p where not p.postcode='' group by p.city, p.postcode")

public class Patient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@JsonbTransient
	private int id;

	private String address;

	private String birthdate;

	private String city;
		
	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	private String password;
	

	@Column(name="patient_id")
	private String patientId;

	private String postcode;

	@Column(name="user_id")
	private String userId;

	private String gender;

	public Patient() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@JsonbProperty("CA_ADDRESS")
	public String getAddress() {
		return this.address;
	}

	@JsonbProperty("ADDRESS")
	public void setAddress(String address) {
		this.address = address;
	}

	@JsonbProperty("CA_DOB")
	public String getBirthdate() {
		return this.birthdate;
	}

	@JsonbProperty("BIRTHDATE")
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	
	@JsonbProperty("CA_CITY")
	public String getCity() {
		return this.city;
	}

	@JsonbProperty("CITY")
	public void setCity(String city) {
		this.city = city;
	}

	@JsonbProperty("CA_FIRST_NAME")
	public String getFirstName() {
		return this.firstName;
	}

	@JsonbProperty("FIRST")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@JsonbProperty("CA_LAST_NAME")
	public String getLastName() {
		return this.lastName;
	}
	
	@JsonbProperty("LAST")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonbProperty("PATIENTID")
	public String getPatientId() {
		return this.patientId;
	}

	@JsonbProperty("Id")
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	@JsonbProperty("CA_POSTCODE")
	public String getPostcode() {
		return this.postcode;
	}

	@JsonbProperty("ZIP")
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@JsonbProperty("CA_USERID")
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@JsonbProperty("CA_GENDER")
	public String getGender() {
		return this.gender;
	}

	@JsonbProperty("GENDER")
	public void setGender(String gender) {
		this.gender = gender;
	}

	public Patient(String firstName, String lastName, String gender, String birthdate) {
		this.birthdate = birthdate;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
	}

}
