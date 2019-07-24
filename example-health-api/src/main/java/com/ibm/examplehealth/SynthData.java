package com.ibm.examplehealth;

import java.io.Serializable;
import javax.persistence.*;
import javax.json.bind.annotation.JsonbProperty;
import java.util.List;
import java.util.ArrayList;

public class SynthData {

	@JsonbProperty("allergies")
	public List<Allergy> allergies = new ArrayList();

	public List<Patient> patients = new ArrayList();
	public List<Observation> observations = new ArrayList();
	public List<Prescription> medications = new ArrayList();
	public List<Appointment> encounters = new ArrayList();
	public List<Provider> providers = new ArrayList();
	public List<Organization> organizations = new ArrayList();
}
