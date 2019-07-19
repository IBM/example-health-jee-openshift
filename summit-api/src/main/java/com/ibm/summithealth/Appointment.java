package com.ibm.summithealth;

import java.io.Serializable;
import javax.persistence.*;
import javax.json.bind.annotation.JsonbProperty;


/**
 * The persistent class for the Appointments database table.
 * 
 */
@Entity
@Table(name="Appointments")
@NamedQuery(name="Appointment.findAll", query="SELECT a FROM Appointment a")

@NamedQuery(name="Appointment.getAppointments", query="SELECT NEW com.ibm.summithealth.AppointmentList(p.patientId, p.birthdate, p.city, p.postcode, a.description, " + 
		"a.allergyStart, a.allergyStop) FROM Patient p JOIN Allergy a ON p.patientId = a.patientId")

public class Appointment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@JsonbProperty("Id")
	@Column(name="appointment_id")
	private String appointmentId;

	@JsonbProperty("START")
	private String date;

	private String time;

	@JsonbProperty("PATIENT")
	@Column(name="patient_id")
	private String patientId;

	@JsonbProperty("PROVIDER")
	@Column(name="provider_id")
	private String providerId;

	public Appointment() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAppointmentId() {
		return this.appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPatientId() {
		return this.patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getProviderId() {
		return this.providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
}