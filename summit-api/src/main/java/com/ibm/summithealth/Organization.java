package com.ibm.summithealth;

import java.io.Serializable;
import javax.persistence.*;
import javax.json.bind.annotation.JsonbProperty;


/**
 * The persistent class for the Organizations database table.
 * 
 */
@Entity
@Table(name="Organizations")
@NamedQuery(name="Organization.findAll", query="SELECT o FROM Organization o")
public class Organization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@JsonbProperty("Id")
	@Column(name="organization_id")
	private String organizationId;

	@JsonbProperty("NAME")
	private String name;

	@JsonbProperty("ADDRESS")
	private String address;

	@JsonbProperty("CITY")
	private String city;

	@JsonbProperty("STATE")
	private String state;

	@JsonbProperty("ZIP")
	@Column(name="postcode")
	private String postcode;

	public Organization() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
}