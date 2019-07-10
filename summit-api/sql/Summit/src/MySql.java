import java.sql.DriverManager;
import java.sql.ResultSet;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.Statement;

public class MySql {
	private Connection connect = null;
    private Statement statement = null;
    private String connectionHostUrl=null;
    private String user=null;
    private String password=null;
    private String db=null;
    private String driver=null;
	private ResultSetMetaData meta = null;
    
    public MySql() {

    		connectionHostUrl="jdbc:mysql://sl-us-south-1-portal.51.dblayer.com:23411";
    		user="admin";
    		password="BXGCYZWXFMSIFTHD";
    		db="SummitHealth";
    		driver="com.mysql.cj.jdbc.Driver";
	}
    
    
    public void connectToDB() throws Exception {
    	try {
    		// Load the MySQL driver
            Class.forName(driver);
            // Setup the connection with the db and given credentials
            connect = DriverManager.getConnection(connectionHostUrl + "/" + db, user, password);
        } catch (Exception e) {
			e.printStackTrace();
        }
    }
    
    // Close the resultSet, statement, and connection
    public void close() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
			e.printStackTrace();
        }
    }
    
    public JSONArray generate(JSONObject data) {
    		if (executeUpdate("CREATE DATABASE " + db)) {
    			if (createTables()) {
    				try {
						boolean isAllergyTablePopulated = populateAllergyTable((JSONArray) data.get("allergies"));
						JSONArray patientCredentials = populatePatientsTable((JSONArray) data.get("patients"));
						boolean isPrescriptionsTablePopulated = populatePrescriptionsTable((JSONArray) data.get("medications"));
						boolean isObservationsTablePopulated = populateObservationsTable((JSONArray) data.get("observations"));
						boolean isAppointmentsTablePopulated = populateAppointmentsTable((JSONArray) data.get("encounters"));
						boolean isProvidersTablePopulated = populateProvidersTable((JSONArray) data.get("providers"));
						boolean isOrganizationsTablePopulated = populateOrganizationsTable((JSONArray) data.get("organizations"));
						
						if (isAllergyTablePopulated && patientCredentials.length() > 0 && isPrescriptionsTablePopulated && isObservationsTablePopulated && isAppointmentsTablePopulated && isProvidersTablePopulated && isOrganizationsTablePopulated) {
							return patientCredentials;
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
    			} else {
    				
    			}
    		} else {
    			
    		}
    		
    		return new JSONArray();
    }
    
    private boolean populateOrganizationsTable(JSONArray organizations) {
    		boolean allSuccess = true;
    		
    		for (int organization = 0; organization < organizations.length(); organization++) {
    			try {
    				JSONObject currentOrganization = (JSONObject) organizations.get(organization);
    				
    				allSuccess = allSuccess && executeUpdate("INSERT INTO Organizations VALUES (null,"
    						+ "\"" + currentOrganization.get("Id") + "\","
    						+ "\"" + currentOrganization.get("NAME") + "\","
    						+ "\"" + currentOrganization.get("ADDRESS") + "\","
    						+ "\"" + currentOrganization.get("CITY") + "\","
    						+ "\"" + currentOrganization.get("STATE") + "\","
    						+ "\"" + currentOrganization.get("ZIP") + "\");");
    			} catch (JSONException e) {
    				e.printStackTrace();
    				allSuccess = false;
    			}
    		}
    		
    		return allSuccess;
    }
    
    private boolean populateProvidersTable(JSONArray providers) {
    		boolean allSuccess = true;
    		
    		for (int provider = 0; provider < providers.length(); provider++) {
    			try {
    				JSONObject currentProvider = (JSONObject) providers.get(provider);
    				
    				allSuccess = allSuccess && executeUpdate("INSERT INTO Providers VALUES (null,"
    						+ "\"" + currentProvider.get("Id") + "\","
    						+ "\"" + currentProvider.get("ORGANIZATION") + "\","
    						+ "\"" + currentProvider.get("NAME").toString().replaceAll("[0-9]", "") + "\","
    						+ "\"" + currentProvider.get("SPECIALITY") + "\");");
    			} catch (JSONException e) {
    				e.printStackTrace();
    				allSuccess = false;
    			}
    		}
    		
    		return allSuccess;
    }

    private boolean populateAppointmentsTable(JSONArray appointments) {
    		boolean allSuccess = true;
    		
    		for (int appointment = 0; appointment < appointments.length(); appointment++) {
    			try {
    				JSONObject currentAppointment = (JSONObject) appointments.get(appointment);
    				
    				allSuccess = allSuccess && executeUpdate("INSERT INTO Appointments VALUES (null,"
    						+ "\"" + currentAppointment.get("Id") + "\","
    						+ "\"" + currentAppointment.get("START").toString().substring(0,10) + "\","
    						+ "\"" + currentAppointment.get("START").toString().substring(11,19) + "\","
    						+ "\"" + currentAppointment.get("PATIENT") + "\","
    						+ "\"" + currentAppointment.get("PROVIDER") + "\");");
    			} catch (JSONException e) {
    				e.printStackTrace();
    				allSuccess = false;
    			}
    		}
    		
    		return allSuccess;
    }

	private boolean populateObservationsTable(JSONArray observations) {
		boolean allSuccess = true;
		
		for (int observation = 0; observation < observations.length(); observation++) {
			try {
				JSONObject currentObservation = (JSONObject) observations.get(observation);
				
				if (currentObservation.get("TYPE").toString().equals("numeric")) {
					allSuccess = allSuccess && executeUpdate("INSERT INTO Observations (id, date, code, description, numeric_value, units, patient_id) VALUES (null,"
							+ "\"" + currentObservation.get("DATE") + "\","
							+ "\"" + currentObservation.get("CODE") + "\","
							+ "\"" + currentObservation.get("DESCRIPTION") + "\","
							+ "\"" + currentObservation.get("VALUE") + "\","
							+ "\"" + currentObservation.get("UNITS") + "\","
							+ "\"" + currentObservation.get("PATIENT") + "\");");
				} else {
					allSuccess = allSuccess && executeUpdate("INSERT INTO Observations (id, date, code, description, character_value, units, patient_id) VALUES (null,"
							+ "\"" + currentObservation.get("DATE") + "\","
							+ "\"" + currentObservation.get("CODE") + "\","
							+ "\"" + currentObservation.get("DESCRIPTION") + "\","
							+ "\"" + currentObservation.get("VALUE") + "\","
							+ "\"" + currentObservation.get("UNITS") + "\","
							+ "\"" + currentObservation.get("PATIENT") + "\");");
				}
			} catch (JSONException e) {
				e.printStackTrace();
				allSuccess = false;
			}
		}
		
		return allSuccess;
	}

	private boolean populatePrescriptionsTable(JSONArray prescriptions) {
		boolean allSuccess = true;
		
		for (int prescription = 0; prescription < prescriptions.length(); prescription++) {
			try {
				JSONObject currentPrescription = (JSONObject) prescriptions.get(prescription);
				String[] description = currentPrescription.get("DESCRIPTION").toString().replaceAll("(NDA[0-9]+)|(\\.)","").split("[0-9]+");
				allSuccess = allSuccess && executeUpdate("INSERT INTO Prescriptions VALUES (null,"
						+ "\"" + currentPrescription.get("CODE") + "\","
						+ "\"" + (description[0].length() > 0 ? description[0] : description[1].trim().substring(description[1].trim().indexOf(" "))) + "\","
						+ "\"" + currentPrescription.get("PATIENT") + "\","
						+ "\"" + currentPrescription.get("REASONDESCRIPTION") + "\");");
			} catch (JSONException e) {
				e.printStackTrace();
				allSuccess = false;
			}
		}
		
		return allSuccess;
	}

	private JSONArray populatePatientsTable(JSONArray patients) {
		JSONArray credentials = new JSONArray();
		for (int patient = 0; patient < patients.length(); patient++) {
			try {
				JSONObject currentPatient = (JSONObject) patients.get(patient);
				String patientCredential = currentPatient.get("FIRST").toString().replaceAll("[0-9]", "").toLowerCase() + currentPatient.get("LAST").toString().replaceAll("[0-9]", "").toLowerCase().charAt(0);
				executeUpdate("INSERT INTO Patients VALUES (null,"
						+ "\"" + currentPatient.get("Id") + "\","
						+ "\"" + currentPatient.get("FIRST").toString().replaceAll("[0-9]", "") + "\","
						+ "\"" + currentPatient.get("LAST").toString().replaceAll("[0-9]", "") + "\","
						+ "\"" + currentPatient.get("BIRTHDATE") + "\","
						+ "\"" + currentPatient.get("GENDER") + "\","
						+ "\"" + currentPatient.get("ADDRESS") + "\","
						+ "\"" + currentPatient.get("CITY") + "\","
						+ "\"" + currentPatient.get("ZIP") + "\","
						+ "\"" + patientCredential + "\","
						+ "\"" + patientCredential + "\");");
				credentials.put(new JSONObject("{CA_USERID: "+ patientCredential + ",CA_USERPWD: "+ patientCredential + "}"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return credentials;
	}

	private boolean populateAllergyTable(JSONArray allergies) {
		boolean allSuccess = true;
		
		for (int allergy = 0; allergy < allergies.length(); allergy++) {
			try {
				JSONObject currentAllergy = (JSONObject) allergies.get(allergy);
				allSuccess = allSuccess && executeUpdate("INSERT INTO Allergies VALUES (null,"
						+ "\"" + currentAllergy.get("PATIENT") + "\","
						+ "\"" + currentAllergy.get("START") + "\","
						+ "\"" + currentAllergy.get("STOP") + "\","
						+ "\"" + currentAllergy.get("DESCRIPTION") + "\");");
			} catch (JSONException e) {
				e.printStackTrace();
				allSuccess = false;
			}
		}
		
		return allSuccess;
	}


	private boolean executeUpdate(String query) {
	    	try {
	    		connectToDB();            
	    		statement = connect.createStatement();
	    		statement.executeUpdate(query);	            
	    		close();
	    		return true;
	    	} catch (java.sql.SQLException e) {
	    		e.printStackTrace();
	    		close();
	    		return e.getMessage().contains("exists");
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    		close();
	    		return false;
	    }
	}
	
	private JSONArray executeQuery(String query) {
		ResultSet resultSet = null;
		JSONObject current;
		JSONArray result = new JSONArray();
		try {
	    		connectToDB();            
	    		statement = connect.createStatement();
	    		resultSet = statement.executeQuery(query);
	    		meta = resultSet.getMetaData();
			while (resultSet.next()) { 
				current = new JSONObject();
		        	for (int i = 1; i <= meta.getColumnCount(); i++) {
		        		current.append(meta.getColumnLabel(i), resultSet.getString(meta.getColumnLabel(i)));
		        	}
		        	result.put(current);
			}
	    		close();
	    		return result;
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    		close();
	    		return result;
	    }
	}
	
	private boolean createTables() {
		boolean isPatientTableCreated = executeUpdate("CREATE TABLE Patients ("
				+ "id int NOT NULL AUTO_INCREMENT,"
				+ " patient_id varchar(36),"
				+ " first_name varchar(10),"
				+ " last_name varchar(20),"
				+ " birthdate varchar(10),"
				+ " gender varchar(2),"
				+ " address varchar(50),"
				+ " city varchar(30),"
				+ " postcode varchar(10),"
				+ " user_id varchar(10),"
				+ " password varchar(50),"
				+ " PRIMARY KEY (id));");
		
		boolean isAllergiesTableCreated = executeUpdate("CREATE TABLE Allergies ("
				+ "id int NOT NULL AUTO_INCREMENT,"
				+ " patient_id varchar(36),"
				+ " allergy_start varchar(10),"
				+ " allergy_stop varchar(10),"
				+ " description varchar(50),"
				+ " PRIMARY KEY (id));");
		
		boolean isPrescriptionsTableCreated = executeUpdate("CREATE TABLE Prescriptions ("
				+ "id int NOT NULL AUTO_INCREMENT,"
				+ " medication_id varchar(10),"
				+ " drug_name varchar(50),"
				+ " patient_id varchar(36),"
				+ " reason varchar(100),"
				+ " PRIMARY KEY (id));");
		
		boolean isObservationsTableCreated = executeUpdate("CREATE TABLE Observations ("
				+ "id int NOT NULL AUTO_INCREMENT,"
				+ " date varchar(10),"
				+ " code varchar(8),"
				+ " description varchar(100),"
				+ " numeric_value varchar(10),"
				+ " character_value varchar(30),"
				+ " units varchar(22),"
				+ " patient_id varchar(36),"
				+ " PRIMARY KEY (id));");
		
		boolean isAppointmentsTableCreated = executeUpdate("CREATE TABLE Appointments ("
				+ "id int NOT NULL AUTO_INCREMENT,"
				+ " appointment_id varchar(36),"
				+ " date varchar(10),"
				+ " time varchar(10),"
				+ " patient_id varchar(36),"
				+ " provider_id varchar(36),"
				+ " PRIMARY KEY (id));");
		
		boolean isProvidersTableCreated = executeUpdate("CREATE TABLE Providers ("
				+ "id int NOT NULL AUTO_INCREMENT,"
				+ " provider_id varchar(36),"
				+ " organization_id varchar(36),"
				+ " name varchar(30),"
				+ " speciality varchar(30),"
				+ " PRIMARY KEY (id));");
		
		boolean isOrganizationsTableCreated = executeUpdate("CREATE TABLE Organizations ("
				+ "id int NOT NULL AUTO_INCREMENT,"
				+ " organization_id varchar(36),"
				+ " name varchar(200),"
				+ " address varchar(50),"
				+ " city varchar(30),"
				+ " state varchar(3),"
				+ " postcode varchar(10),"
				+ " PRIMARY KEY (id));");
		
		return isPatientTableCreated && isAllergiesTableCreated && isPrescriptionsTableCreated && isObservationsTableCreated && isAppointmentsTableCreated && isProvidersTableCreated && isOrganizationsTableCreated;
	}

	public JSONObject getPatientInfo(int patientId) {
		JSONObject result;
		try {
			JSONObject patientData = executeQuery("select * from SummitHealth.Patients where id="+patientId).getJSONObject(0);
			
			if (patientData.length() > 0) {
				result = new JSONObject("{"
						+ "CA_FIRST_NAME: " + ((JSONArray) patientData.get("first_name")).get(0) + ","
						+ "CA_LAST_NAME: " + ((JSONArray) patientData.get("last_name")).get(0) + ","
						+ "CA_DOB: " + ((JSONArray) patientData.get("birthdate")).get(0) + ","
						+ "CA_GENDER: " + ((JSONArray) patientData.get("gender")).get(0) + ","
						+ "CA_ADDRESS: " + ((JSONArray) patientData.get("address")).get(0) + ","
						+ "CA_CITY: " + ((JSONArray) patientData.get("city")).get(0) + ","
						+ "CA_POSTCODE: " + ((JSONArray) patientData.get("postcode")).get(0) + ","
						+ "CA_USERID: " + ((JSONArray) patientData.get("user_id")).get(0)
						+ "}");
			} else {
				result = new JSONObject("{"
						+ "CA_FIRST_NAME: \"\","
						+ "CA_LAST_NAME: \"\","
						+ "CA_DOB: \"\","
						+ "CA_GENDER: \"\","
						+ "CA_ADDRESS: \"\","
						+ "CA_CITY: \"\","
						+ "CA_POSTCODE: \"\","
						+ "CA_USERID: \"\""
						+ "}");
			}
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
			return new JSONObject();
		}
	}
	
	public JSONArray getPatients() {
		JSONArray result = new JSONArray();
		try {
			JSONArray patients = executeQuery("select first_name, last_name, gender, birthdate from SummitHealth.Patients");
			for (int patient = 0; patient < patients.length(); patient++) {
				JSONObject currentPatient = (JSONObject) patients.get(patient);
				result.put(new JSONObject("{"
						+ "CA_FIRST_NAME: " + ((JSONArray) currentPatient.get("first_name")).get(0) + ","
						+ "CA_LAST_NAME: " + ((JSONArray) currentPatient.get("last_name")).get(0) + ","
						+ "CA_DOB: " + ((JSONArray) currentPatient.get("birthdate")).get(0) + ","
						+ "CA_GENDER: " + ((JSONArray) currentPatient.get("gender")).get(0) + ","
						+ "}"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public JSONObject getDiseases() {
		JSONObject result = new JSONObject();
		int totalAsthmaPatients = 0;
		int totalDiabetesPatients = 0;
		
		try {
			JSONArray reasons = executeQuery("select distinct patient_id, reason from SummitHealth.Prescriptions");
			int totalPatients = Integer.valueOf(((JSONArray) ((JSONObject) executeQuery("select count(patient_id) from SummitHealth.Patients").get(0)).get("count(patient_id)")).get(0).toString());
			for (int reason = 0; reason < reasons.length(); reason++) {
				String currentReason = ((JSONArray) ((JSONObject) reasons.get(reason)).get("reason")).getString(0);
				if (currentReason.toLowerCase().contains("asthma")) {
					totalAsthmaPatients += 1;
				} else if (currentReason.toLowerCase().contains("diabetes")) {
					totalDiabetesPatients += 1;
				}
			}
			
			result = new JSONObject("{"
					+ "PATIENTS: " + totalPatients + ","
					+ "DIABETES: " + totalDiabetesPatients + ","
					+ "ASTHMA: " + totalAsthmaPatients
					+ "}");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public JSONArray getPatientPrescriptions(int patientId) {
		JSONArray result = new JSONArray();
		try {
			JSONObject patientData = executeQuery("select patient_id from SummitHealth.Patients where id="+patientId).getJSONObject(0);
			JSONArray patientPrescriptionsData = executeQuery("select * from SummitHealth.Prescriptions where patient_id=\""+((JSONArray) patientData.get("patient_id")).get(0) + "\"");
			
			for (int prescription = 0; prescription < patientPrescriptionsData.length(); prescription++) {
				result.put(new JSONObject("{"
						+ "CA_DRUG_NAME: " + ((JSONArray) ((JSONObject) patientPrescriptionsData.get(prescription)).get("drug_name")).get(0) + ","
						+ "CA_MEDICATION_ID: " + ((JSONArray) ((JSONObject) patientPrescriptionsData.get(prescription)).get("medication_id")).get(0) + ","
						+ "}"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public JSONArray getPrescriptionsCount() {
		JSONArray result = new JSONArray();
		try {
			JSONArray prescriptions = executeQuery("select distinct drug_name from SummitHealth.Prescriptions");
			for (int prescription = 0; prescription < prescriptions.length(); prescription++) {
				JSONArray currentPrescriptionCount = executeQuery("select count(distinct drug_name, patient_id) from SummitHealth.Prescriptions where drug_name=\"" +  ((JSONArray) ((JSONObject) prescriptions.get(prescription)).get("drug_name")).get(0).toString() + "\"");
				result.put(new JSONObject("{"
						+ "DRUG_NAME: " + ((JSONArray) ((JSONObject) prescriptions.get(prescription)).get("drug_name")).get(0).toString() + ","
						+ "TOTAL_PATIENTS: " + ((JSONArray) ((JSONObject) currentPrescriptionCount.get(0)).get("count(distinct drug_name, patient_id)")).get(0).toString()
						+ "}"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	public JSONArray getPatientObservations(int patientId) {
		JSONArray result = new JSONArray();
		try {
			JSONObject patientData = executeQuery("select patient_id from SummitHealth.Patients where id="+patientId).getJSONObject(0);
			JSONArray patientObservationsData = executeQuery("select * from SummitHealth.Observations where patient_id=\""+((JSONArray) patientData.get("patient_id")).get(0) + "\"");
			
			for (int prescription = 0; prescription < patientObservationsData.length(); prescription++) {
				JSONObject currentPrescription = ((JSONObject) patientObservationsData.get(prescription));
				result.put(new JSONObject("{"
						+ "PATIENTID: " + patientId + ","
						+ "DATEOFOBSERVATION: " + (!((JSONArray) currentPrescription.get("date")).isNull(0) ? ((JSONArray) currentPrescription.get("date")).get(0) : "\"\"") + ","
						+ "CODE: " + (!((JSONArray) currentPrescription.get("code")).isNull(0) ? ((JSONArray) currentPrescription.get("code")).get(0) : "\"\"") + ","
						+ "DESCRIPTION: \"" + (!((JSONArray) currentPrescription.get("description")).isNull(0) ? ((JSONArray) currentPrescription.get("description")).get(0) : "") + "\","
						+ "NUMERICVALUE: " + (!((JSONArray) currentPrescription.get("numeric_value")).isNull(0) ? ((JSONArray) currentPrescription.get("numeric_value")).get(0) : "\"\"") + ","
						+ "CHARACTERVALUE: \"" + (!((JSONArray) currentPrescription.get("character_value")).isNull(0) ? ((JSONArray) currentPrescription.get("character_value")).get(0) : "") + "\","
						+ "UNITS: \"" + (!((JSONArray) currentPrescription.get("units")).isNull(0) ? ((JSONArray) currentPrescription.get("units")).get(0) : "") + "\""
						+ "}"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public String login(String userId, String password) {
		try {
			JSONArray patientId = executeQuery("select id from SummitHealth.Patients  where user_id=\"" + userId + "\" and password=\"" + password + "\"");
			if (patientId.length() > 0) {
				return ((JSONArray) patientId.getJSONObject(0).get("id")).get(0).toString();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public JSONArray countCities() {
		JSONArray result = new JSONArray();
		try {
			JSONArray cities = executeQuery("select distinct city, postcode from SummitHealth.Patients");
			
			for (int city = 0; city < cities.length(); city++) {
				JSONObject currentCity = ((JSONObject) cities.get(city));
				JSONObject cityPopulations = executeQuery("select count(city) from SummitHealth.Patients where city=\"" +  ((JSONArray) currentCity.get("city")).get(0) + "\"").getJSONObject(0);
				result.put(new JSONObject("{"
						+ "CITY: " + ((JSONArray) currentCity.get("city")).get(0) + ","
						+ "POSTCODE: " + ((JSONArray) currentCity.get("postcode")).get(0) + ","
						+ "NUM_IN_CITY: " + ((JSONArray) cityPopulations.get("count(city)")).get(0) + ","
						+ "}"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	public JSONArray getAllergies() {
		JSONArray result = new JSONArray();
		try {
			JSONArray patients = executeQuery("select * from SummitHealth.Patients");
			
			for (int patient = 0; patient < patients.length(); patient++) {
				JSONObject currentPatient = ((JSONObject) patients.get(patient));
				JSONArray currentPatientAllergies = executeQuery("select * from SummitHealth.Allergies where patient_id=\"" + ((JSONArray) currentPatient.get("patient_id")).get(0) + "\"");
				for (int allergy = 0; allergy < currentPatientAllergies.length(); allergy++) {
					JSONObject currentPatientAllergy = ((JSONObject) currentPatientAllergies.get(allergy));
					result.put(new JSONObject("{"
							+ "CITY: " + ((JSONArray) currentPatient.get("city")).get(0) + ","
							+ "POSTCODE: " + ((JSONArray) currentPatient.get("postcode")).get(0) + ","
							+ "PATIENT_NUM: " + ((JSONArray) currentPatient.get("id")).get(0) + ","
							+ "BIRTHDATE: " + ((JSONArray) currentPatient.get("birthdate")).get(0) + ","
							+ "ALLERGY_START: " + ((JSONArray) currentPatientAllergy.get("allergy_start")).get(0) + ","
							+ "ALLERGY_STOP: \"" + ((JSONArray) currentPatientAllergy.get("allergy_stop")).get(0) + "\","
							+ "DESCRIPTION: " + ((JSONArray) currentPatientAllergy.get("description")).get(0)
							+ "}"));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

}
