package com.ibm.examplehealth;

import java.io.Serializable;
import javax.persistence.*;
import javax.json.bind.annotation.JsonbProperty;

/**
 * The persistent class for the Observations database table.
 * "ResultSet Output": [
        {
            "PATIENTID": 1,
            "DATEOFOBSERVATION": "2018-05-03",
            "CODE": "11111-0 ",
            "DESCRIPTION": "Tobacco smoking status NHIS",
            "NUMERICVALUE": null,
            "CHARACTERVALUE": "Former smoker",
            "UNITS": null
        }],
    "StatusCode": 200,
    "StatusDescription": "Execution Successful"
}
 */
@Entity
@Table(name="Observations")
@NamedQuery(name="Observation.findAll", query="SELECT o FROM Observation o")
@NamedQuery(name = "Observation.getObservations", query = "SELECT o FROM Observation o WHERE "
    + "o.patientId = :pid")

public class Observation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@JsonbProperty("CODE")
	private String code;

	private String date;

	@JsonbProperty("DESCRIPTION")
	private String description;

	@Transient
	@JsonbProperty("VALUE")
	public String jsonValue;

	@Transient
	@JsonbProperty("TYPE")
	public String type;

	@Column(name="numeric_value")
	private String numericValue;

	@Column(name="character_value")
	private String characterValue;

	@JsonbProperty("PATIENT")
	@Column(name="patient_id")
	private String patientId;

	@JsonbProperty("UNITS")
	private String units;

	public Observation() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@JsonbProperty("CHARACTERVALUE")
	public String getCharacterValue() {
		return this.characterValue;
	}

	public void setCharacterValue(String characterValue) {
		this.characterValue = characterValue;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@JsonbProperty("DATEOFOBSERVATION")
	public String getDate() {
		return this.date;
	}

	@JsonbProperty("DATE")
	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonbProperty("NUMERICVALUE")
	public String getNumericValue() {
		return this.numericValue;
	}

	public void setNumericValue(String numericValue) {
		this.numericValue = numericValue;
	}

	public String getPatientId() {
		return this.patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getUnits() {
		return this.units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

}
