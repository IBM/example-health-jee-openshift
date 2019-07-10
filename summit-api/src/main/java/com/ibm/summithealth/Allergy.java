package com.ibm.summithealth;

import java.io.Serializable;
import javax.persistence.*;
import javax.json.bind.annotation.JsonbProperty;

/**
 * The persistent class for the Allergies database table.
 * 
 */
@Entity
@Table(name="Allergies")
@NamedQuery(name="Allergy.findAll", query="SELECT a FROM Allergy a")
@NamedQuery(name="Allergy.getAllergies", query="SELECT NEW com.ibm.summithealth.AllergyList(p.patientId, p.birthdate, p.city, p.postcode, a.description, " + 
		"a.allergyStart, a.allergyStop) FROM Patient p JOIN Allergy a ON p.patientId = a.patientId")

public class Allergy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@JsonbProperty("START")
	@Column(name="allergy_start")
	private String allergyStart;

	@JsonbProperty("STOP")
	@Column(name="allergy_stop")
	private String allergyStop;
	
	@JsonbProperty("DESCRIPTION")
	private String description;

	@JsonbProperty("PATIENT")
	@Column(name="patient_id")
	private String patientId;

	public Allergy() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAllergyStart() {
		return this.allergyStart;
	}

	public void setAllergyStart(String allergyStart) {
		this.allergyStart = allergyStart;
	}

	public String getAllergyStop() {
		return this.allergyStop;
	}

	public void setAllergyStop(String allergyStop) {
		this.allergyStop = allergyStop;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPatientId() {
		return this.patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

}
