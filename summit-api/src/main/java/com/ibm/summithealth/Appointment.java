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

/*
 SELECT org.state, org.postcode, org.city, org.name, org.address, 
		pat.first_name, pat.last_name, app.date, app.time, app.patient_id, prov.name, prov.speciality 
		FROM Providers prov, Appointments app, Patients pat, Organizations org 
		WHERE app.provider_id=prov.organization_id 
		AND app.patient_id="bd2b67d0-f318-471d-83a6-1b130673d9f3" 
		AND pat.patient_id="bd2b67d0-f318-471d-83a6-1b130673d9f3" 
		AND org.organization_id=app.provider_id;

		Constructor field order for use in NamedQuery:

		public AppointmentList(String patient_id, String first_name, String last_name, String date, String time,
            String doc_name, String field, String office_name, String office_addr, String office_city, String office_state,
            String office_zip) {
*/
@NamedQuery(name="Appointment.getAppointments",
		query="SELECT NEW com.ibm.summithealth.AppointmentList(p.patientId, p.firstName, p.lastName, app.date, app.time, " + 
		"prov.name, prov.speciality, org.name, org.address, org.city, org.state, org.postcode) "  + 
		"FROM Patient p, Appointment app, Provider prov, Organization org " + 
		"WHERE app.patientId=:pid AND p.patientId=:pid AND app.providerId=prov.organizationId AND org.organizationId=app.providerId")

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