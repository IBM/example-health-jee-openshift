import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataService {
	MySql mysql;
	
	public DataService() {
		mysql = new MySql();
	}
	
	public JSONObject generate(JSONObject data) throws JSONException {
		JSONArray patientCredentials = mysql.generate(data);
		return new JSONObject("{ResultSet Output: " + patientCredentials.toString() + ",StatusCode: 200,StatusDescription: Execution Successful}");
	}
	
	public JSONObject getPatientInfo(int patientId) throws JSONException {
		JSONObject patientInfo = mysql.getPatientInfo(patientId);
		int returnCode = 0;
		if (patientInfo.get("CA_FIRST_NAME").equals("")) {
			returnCode = 1;
		}
		return new JSONObject("{HCCMAREA: {" + 
				"CA_REQUEST_ID: 01IPAT," + 
				"CA_RETURN_CODE: " + returnCode + "," + 
				"CA_PATIENT_ID: " + patientId + "," + 
				"CA_PATIENT_REQUEST: " + patientInfo.toString() +
				"}}");
	}
	
	public JSONObject getPatientPrescriptions(int patientId) throws JSONException {
		JSONArray patientPrescriptions = mysql.getPatientPrescriptions(patientId);
		int returnCode = 0;
		if (patientPrescriptions.length() == 0) {
			returnCode = 1;
		}
		return new JSONObject("{GETMEDO: {" +
				"CA_RETURN_CODE: " + returnCode + "," + 
				"CA_PATIENT_ID: " + patientId + "," + 
				"CA_LIST_MEDICATION_REQUEST: {CA_MEDICATIONS: " + patientPrescriptions.toString() +
				"}}}");
	}
	
	public JSONObject getPatientObservations(int patientId) throws JSONException {
		JSONArray patientObservations = mysql.getPatientObservations(patientId);

		return new JSONObject("{" +
				"StatusCode: 200," + 
				"StatusDescription: Execution Successful," +
				"ResultSet Output: " + patientObservations.toString() +
				"}");
	}
	
	public JSONObject login(String userId, String password) throws JSONException {
		String patientId = mysql.login(userId, password);
		return new JSONObject("{" +
				"StatusCode: 200," + 
				"StatusDescription: Execution Successful," +
				"ResultSet Output: [" + (patientId != null ? "{PATIENTID: " + patientId + "}" : "") + "]" +
				"}");
	}
	
	public JSONObject getCityPopulations() throws JSONException {
		JSONArray cityPopulations = mysql.countCities();
		return new JSONObject("{" +
				"StatusCode: 200," + 
				"StatusDescription: Execution Successful," +
				"ResultSet Output: " + cityPopulations.toString() +
				"}");
	}
	
	public JSONObject getAllergies() throws JSONException {
		JSONArray allergies = mysql.getAllergies();
		return new JSONObject("{" +
				"StatusCode: 200," + 
				"StatusDescription: Execution Successful," +
				"ResultSet Output: " + allergies.toString() +
				"}");
	}
	
	public JSONObject getPatients() throws JSONException {
		JSONArray patients = mysql.getPatients();
		return new JSONObject("{" +
				"StatusCode: 200," +
				"StatusDescription: Execution Successful," +
				"ResultSet Output: " + patients.toString() +
				"}");
	}
	
	public JSONObject getDiseases() throws JSONException {
		JSONObject diseases = mysql.getDiseases();
		return new JSONObject("{" +
				"StatusCode: 200," +
				"StatusDescription: Execution Successful," +
				"ResultSet Output: [" + diseases.toString() +
				"]}"); 
	}
	
	public JSONObject getPrescriptionsCount()  throws JSONException {
		JSONArray prescriptions = mysql.getPrescriptionsCount();
		return new JSONObject("{" +
				"StatusCode: 200," +
				"StatusDescription: Execution Successful," +
				"ResultSet Output: [" + prescriptions.toString() +
				"]}");
	}
}
