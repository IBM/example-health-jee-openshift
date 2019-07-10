package com.ibm.summithealth;

import java.io.Serializable;
import javax.persistence.*;
import javax.json.bind.annotation.JsonbProperty;


/**
 * The persistent class for the Providers database table.
 * 
 */
@Entity
@Table(name="Providers")
@NamedQuery(name="Provider.findAll", query="SELECT p FROM Provider p")
public class Provider implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@JsonbProperty("Id")
	@Column(name="provider_id")
	private String providerId;

	@JsonbProperty("ORGANIZATION")
	@Column(name="organization_id")
	private String organizationId;

	@JsonbProperty("NAME")
	private String name;

	@JsonbProperty("SPECIALITY")
	private String speciality;

	public Provider() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProviderId() {
		return this.providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpeciality() {
		return this.speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
}