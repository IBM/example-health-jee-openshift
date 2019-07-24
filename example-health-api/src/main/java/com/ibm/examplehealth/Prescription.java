package com.ibm.examplehealth;

import java.io.Serializable;
import javax.persistence.*;
import javax.json.bind.annotation.JsonbProperty;

/**
 * The persistent class for the Prescriptions database table.
 * 
 * patientPrescriptions(@PathParam(patID))
Path: GET /getInfo/prescription/{patID}
Calls: dataService.getPatientPrescriptions(patID)
Returns: {
    "GETMEDO": {
        "CA_RETURN_CODE": 0,
        "CA_PATIENT_ID": 1,
        "CA_LIST_MEDICATION_REQUEST": {
            "CA_MEDICATIONS": [
                {
                    "CA_MEDICATION_ID": 1000001,
                    "CA_DRUG_NAME": "Metoprolol",
                    "CA_STRENGTH": "100 mg",
                    "CA_AMOUNT": 1,
                    "CA_ROUTE": "oral route",
                    "CA_FREQUENCY": "every 12 hours",
                    "CA_IDENTIFIER": "redtablet",
                    "CA_TYPE": "bp"
                }
            ]
        }
    }
}
 */
@Entity
@Table(name="Prescriptions")
@NamedQuery(name="Prescription.findAll", query="SELECT p FROM Prescription p")
@NamedQuery(name = "Prescription.getPrescription", query = "SELECT p FROM Prescription p WHERE "
	+ "p.patientId = :pid")

@NamedQuery(name = "Prescription.countAsthma", query = "select count(distinct pr.patientId) from  Prescription pr where pr.reason like '%asthma%'")
@NamedQuery(name = "Prescription.countDiabetes", query = "select count(distinct pr.patientId) from  Prescription pr where pr.reason like '%diabetes%'")

@NamedQuery(name = "Prescription.countScripts", query =  "select Pr.drugName, count(distinct pr.patientId) from Prescription pr, Patient p where p.patientId = pr.patientId group by pr.drugName")


public class Prescription implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;


	@Column(name="drug_name")
	private String drugName;

	
	@Column(name="medication_id")
	private String medicationId;

	@JsonbProperty("PATIENT")
	@Column(name="patient_id")
	private String patientId;

	@JsonbProperty("REASONDESCRIPTION")
	@Column(name="reason")
	private String reason;

	public Prescription() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@JsonbProperty("CA_DRUG_NAME")
	public String getDrugName() {
		return this.drugName;
	}

	@JsonbProperty("DESCRIPTION")
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	@JsonbProperty("CA_MEDICATION_ID")
	public String getMedicationId() {
		return this.medicationId;
	}

	@JsonbProperty("CODE")
	public void setMedicationId(String medicationId) {
		this.medicationId = medicationId;
	}

	public String getPatientId() {
		return this.patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
