package com.ibm.examplehealth;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonCollectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.PathParam;
import java.io.StringReader;
import java.lang.StringBuffer;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.*;

// [x] GET /getInfo/patients/{patID} - gets patient’s details
// [x] GET /getInfo/prescription/{patID} - gets patient’s prescriptions
// POST /healthInfo/addPatient - adds patient to db. Needed?
// [x] GET /listObs/{patID} - gets patient’s observations
// [x] POST /login/user - patient login
// GET /login/userID/{uID}/pwd/{pass} - patient login. Needed?
// POST /appointments/create - adds appt for patient Needed?
// GET /appointments/list/{patID} - gets patient’s appointments
// [x] GET /countCities - gets population of cities in db
// [x] GET /showAllergies - gets city allergy data
// [x] PUT /generate - creates and populates database
// GET /getInfo/patients - gets all patients in db
// GET /listDiseases - gets diseases of patients in db
// GET /getInfo/prescription - gets counts of patient prescriptions

@Path("/")
@Stateless
public class ExampleResource {

    @PersistenceContext
    EntityManager entityManager;
    int batchSize = 100;

    static Logger logger = Logger.getLogger("ExampleHealthAPI");
    static {
        logger.setLevel(Level.ALL);
        logger.addHandler(new ConsoleHandler());
    }

    @GET
    @Path("/v1/countCities")
    @Produces(MediaType.APPLICATION_JSON)
    public Response countCities() {

        List<CityCounts> results = entityManager.createNamedQuery("Patient.getPop", CityCounts.class).getResultList();
        /* Output format
        "ResultSet Output": [
            {
                "CITY": "Akron",
                "POSTCODE": "44223",
                "NUM_IN_CITY": 13
            }],
            "StatusCode": 200,
            "StatusDescription": "Execution Successful”
            } 
        */

        Jsonb jsonb = JsonbBuilder.create();
        String cityBlob = "{\"ResultSet Output\": " + jsonb.toJson(results)
        + ", \"StatusCode\": 200, \n \"StatusDescription\": \"Execution Successful\"}";

        JsonReader jsonReader = Json.createReader(new StringReader(cityBlob));
            
        JsonObject jresponse  = jsonReader.readObject();
        jsonReader.close();

        return Response.ok(jresponse).build();
	}

    @GET
    @Path("/v1/showAllergies")
    @Produces(MediaType.APPLICATION_JSON)
    public Response showAllergies() {
        /*
        "ResultSet Output": [
            {
                "CITY": "Albany              ",
                "POSTCODE": "12202     ",
                "PATIENT_NUM": 1437,
                "BIRTHDATE": "1961-11-26",
                "ALLERGY_START": "1991-10-08",
                "ALLERGY_STOP": null,
                "DESCRIPTION": "Allergy to fish"
            } ],
        "StatusCode": 200,
        "StatusDescription": "Execution Successful"
    }     
    */   


    // select Patients.patient_id, Patients.birthdate, Patients.city, Patients.postcode, Allergies.description, Allergies.allergy_start, Allergies.allergy_stop from Patients JOIN Allergies ON Patients.patient_id = Allergies.patient_id;

        List<AllergyList> results = entityManager.createNamedQuery("Allergy.getAllergies", AllergyList.class).getResultList();

        Jsonb jsonb = JsonbBuilder.create();
        String allergyBlob = "{\"ResultSet Output\": " + jsonb.toJson(results)
        + ", \"StatusCode\": 200, \n \"StatusDescription\": \"Execution Successful\"}";

        JsonReader jsonReader = Json.createReader(new StringReader(allergyBlob));
            
        JsonObject jresponse  = jsonReader.readObject();
        jsonReader.close();

        return Response.ok(jresponse).build();
	}

    @GET
    @Path("/v1/getInfo/patients")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatients() {
        List<Patient> results = entityManager.createNamedQuery("Patient.getPatients", Patient.class).getResultList();
        Jsonb jsonb = JsonbBuilder.create();
        String patientBlob = "{\"ResultSet Output\": " + jsonb.toJson(results)
        + ", \"StatusCode\": 200, \n \"StatusDescription\": \"Execution Successful\"}";
        
        JsonReader jsonReader = Json.createReader(new StringReader(patientBlob));
            
        JsonObject jresponse  = jsonReader.readObject();
        jsonReader.close();
		return Response.ok(jresponse).build();
    }

	@GET
    @Path("/v1/getInfo/patients/{patId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatient(@PathParam("patId") String patId) {
        List<Patient> results = entityManager.createNamedQuery("Patient.findPatient", Patient.class)
                .setParameter("pid", patId)
                .getResultList();
        Jsonb jsonb = JsonbBuilder.create();
        logger.info("Found this many patients with id " + patId + " = " + results.size());
        int returnCode = 0;
        if (results.size() == 0) {
            returnCode=1;
            // return Response.ok("No patients found.").build();
        }

        String patientBlob = "{\"HCCMAREA\": {" + 
        " \"CA_REQUEST_ID\" : \"01IPAT\"," + 
        " \"CA_RETURN_CODE\": " + returnCode + "," + 
        " \"CA_PATIENT_ID\": \"" + patId + "\"," + 
        " \"CA_PATIENT_REQUEST\": " + (returnCode==0 ? jsonb.toJson(results.get(0)) : "\"\"") +
        "}}";

        logger.info("Patient blob: " + patientBlob);

        JsonReader jsonReader = Json.createReader(new StringReader(patientBlob));
            
        JsonObject jresponse  = jsonReader.readObject();
        jsonReader.close();
		return Response.ok(jresponse).build();
	}

    @GET
    @Path("/v1/appointments/list/{patId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response appointments(@PathParam("patId") String patId) {
        List<AppointmentList> results = entityManager.createNamedQuery("Appointment.getAppointments", AppointmentList.class)
                .setParameter("pid", patId)
                .getResultList();
        Jsonb jsonb = JsonbBuilder.create();

        
        String appointmentBlob = "{\"ResultSet Output\": " + jsonb.toJson(results)
        + ", \"StatusCode\": 200, \n \"StatusDescription\": \"Execution Successful\"}";

        int returnCode = 0;
        if (results.size() == 0) {
            returnCode=1;
        }
        
        logger.info("Appointment blob: " + appointmentBlob);

        JsonReader jsonReader = Json.createReader(new StringReader(appointmentBlob));
            
        JsonObject jresponse  = jsonReader.readObject();
        jsonReader.close();
		return Response.ok(jresponse).build();
	}

    @GET
    @Path("/v1/getInfo/prescription/{patId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrescriptions(@PathParam("patId") String patId) {
        List<Prescription> results = entityManager.createNamedQuery("Prescription.getPrescription", Prescription.class)
                .setParameter("pid", patId)
                .getResultList();
        Jsonb jsonb = JsonbBuilder.create();
        logger.info("Found this many prescriptions with id " + patId + " = " + results.size());
        int returnCode = 0;
        if (results.size() == 0) {
            returnCode=1;
            // return Response.ok("No prescriptions found.").build();
        }

        String prescriptionBlob = "{\"GETMEDO\": {" + 
        " \"CA_REQUEST_ID\" : \"01IPAT\"," + 
        " \"CA_RETURN_CODE\": " + returnCode + "," + 
        " \"CA_PATIENT_ID\": \"" + patId + "\"," + 
        " \"CA_LIST_MEDICATION_REQUEST\": { \"CA_MEDICATIONS\": " + jsonb.toJson(results) + "}}}";

        logger.info("Prescription blob: " + prescriptionBlob);

        JsonReader jsonReader = Json.createReader(new StringReader(prescriptionBlob));
            
        JsonObject jresponse  = jsonReader.readObject();
        jsonReader.close();
		return Response.ok(jresponse).build();
    }

    @GET
    @Path("/v1/getInfo/prescription")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrescriptionsCount() {

        List<Object[]> results = entityManager.createNamedQuery("Prescription.countScripts").getResultList();
        
        StringBuffer sb = new StringBuffer();

        sb.append("[");
        for (Object[] o : results) {
            sb.append("{\"DRUG_NAME\":\"" + o[0] + "\", \"TOTAL_PATIENTS\":\"" + o[1] + "\"},");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("]");

        return Response.ok(sb.toString()).build();
    }

    @GET
    @Path("/v1/listObs/{patId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listObs(@PathParam("patId") String patId) {
        List<Observation> results = entityManager.createNamedQuery("Observation.getObservations", Observation.class)
                .setParameter("pid", patId)
                .getResultList();
        Jsonb jsonb = JsonbBuilder.create();
        logger.info("Found this many observations with id " + patId + " = " + results.size());
        int returnCode = 0;
        if (results.size() == 0) {
            returnCode=1;
        }
     
/* output
        Format of JSON output:

        "ResultSet Output": [
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
*/        
        String observationBlob = "{\"ResultSet Output\": " + jsonb.toJson(results)
        + ", \"StatusCode\": 200, \n \"StatusDescription\": \"Execution Successful\"}";

        logger.info("Observation blob: " + observationBlob);

        JsonReader jsonReader = Json.createReader(new StringReader(observationBlob));
            
        JsonObject jresponse  = jsonReader.readObject();
        jsonReader.close();
		return Response.ok(jresponse).build();
        
    }

    @GET
    @Path("/v1/listDiseases")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listDiseases() {

        long asthma = entityManager.createNamedQuery("Prescription.countAsthma", Long.class).getSingleResult();
        long diabetes = entityManager.createNamedQuery("Prescription.countDiabetes", Long.class).getSingleResult();
        long ptnt_cnt = entityManager.createNamedQuery("Patient.countAll", Long.class).getSingleResult();

        String returnBlob = "{"
        + "\"PATIENTS\": " + ptnt_cnt + ","
        + "\"DIABETES\": " + diabetes + ","
        + "\"ASTHMA\": " + asthma
        + "}";

        return Response.ok(returnBlob).build();
    }

    @POST
    @Path("/v1/login/user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String body) {
        // {"UID":username,"PASS":password}
         Jsonb jsonb = JsonbBuilder.create();
         Credentials c = jsonb.fromJson(body, Credentials.class);
        List<Patient> results = entityManager.createNamedQuery("Patient.login", Patient.class)
                .setParameter("userId", c.UID)
                .setParameter("password", c.PASS)
                .getResultList();
        logger.info("Found this many patients: " + results.size());
        int returnCode = 0;
        if (results.size() == 0) {
            returnCode=1;
        }

        /*
            "ResultSet Output": [
            {
                "PATIENTID": 1
            }
            ],
        "StatusCode": 200,
        "StatusDescription": "Execution Successful"
    }*/
        if (returnCode==1) {
            return Response.status(Status.NOT_FOUND).build();
        }
        
        String loginBlob = "{\"ResultSet Output\":" + jsonb.toJson(results) 
        + ", \"StatusCode\": 200, \n \"StatusDescription\": \"Execution Successful\"}";

        logger.info("login blob: " + loginBlob);
        JsonReader jsonReader = Json.createReader(new StringReader(loginBlob));
        JsonObject jresponse  = jsonReader.readObject();
        jsonReader.close();
		return Response.ok(jresponse).build();         
    }

    @POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/v1/generate")
    public String generate(String synthData) {
        Jsonb jsonb = JsonbBuilder.create();
        SynthData synData = jsonb.fromJson(synthData, SynthData.class);
        int cnt=0;

        logger.info("Loading " + synData.patients.size() + " patient records.");
        for (Patient ptnt : synData.patients) {
            cnt++;
            String patientFirstName = ptnt.getFirstName().replaceAll("[0-9]", "");
            String patientLastName = ptnt.getLastName().replaceAll("[0-9]", "");
            ptnt.setFirstName(patientFirstName);
            ptnt.setLastName(patientLastName);
            // logger.info("First name: " + ptnt.getFirstName());
            String patientCredential = ptnt.getFirstName().replaceAll("[0-9]", "").toLowerCase() + ptnt.getLastName().replaceAll("[0-9]", "").toLowerCase().charAt(0);
            ptnt.setPassword(patientCredential);
            ptnt.setUserId(patientCredential);
            entityManager.persist(ptnt);
            flushBatch(synData.patients.size(), cnt, "Patients");
        }
        cnt = 0;

        logger.info("Loading " + synData.providers.size() + " provider records.");
        for (Provider p : synData.providers) {
            cnt++;
            entityManager.persist(p);
            flushBatch(synData.providers.size(), cnt, "Providers");
        }
        cnt = 0;

        logger.info("Loading " + synData.organizations.size() + " organization records.");
        for (Organization o : synData.organizations) {
            cnt++;
            entityManager.persist(o);
            flushBatch(synData.organizations.size(), cnt, "Organizations");
        }
        cnt=0;

        logger.info("Loading " + synData.allergies.size() + " allergy records.");
        for (Allergy a: synData.allergies) {
            cnt++;
            entityManager.persist(a);
            flushBatch(synData.allergies.size(), cnt, "Allergies");
        }
        cnt=0;

        logger.info("Loading " + synData.medications.size() + " medication records.");
        for (Prescription p : synData.medications) {
            cnt++;
            String[] description = p.getDrugName().replaceAll("(NDA[0-9]+)|(\\.)","").split("[0-9]+");
            p.setDrugName(  description[0].length() > 0 ? description[0] : description[1].trim().substring(description[1].trim().indexOf(" ")) );
            entityManager.persist(p);
            flushBatch(synData.medications.size(), cnt, "Prescriptions");
        }
        cnt=0;

        logger.info("Loading " + synData.encounters.size() + " encounter records.");
        for (Appointment a : synData.encounters) {
            cnt++;
            String datetime = a.getDate();
            a.setDate(datetime.substring(0,10));
            a.setTime(datetime.substring(11,19));
            entityManager.persist(a);
            flushBatch(synData.encounters.size(), cnt, "Encounters");
        }
        cnt=0;

        logger.info("Loading " + synData.observations.size() + " observation records.");
        for (Observation o : synData.observations) {
            cnt++;
            if (o.type.equals("numeric")) {
                o.setNumericValue(o.jsonValue);
            } else {
                o.setCharacterValue(o.jsonValue);
            }
            entityManager.persist(o);
            flushBatch(synData.observations.size(), cnt, "Observations");
        }

        return new String("Loaded : " + (synData.observations.size() +
                                        synData.organizations.size() +
                                        synData.providers.size() +
                                        synData.encounters.size() +
                                        synData.medications.size() +
                                        synData.patients.size() +
                                        synData.allergies.size()) + " records.");
    }

    @POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/v1/testAddPatient")
    public void testAddPatient(Patient ptnt) {
        String patientFirstName = ptnt.getFirstName().replaceAll("[0-9]", "");
        String patientLastName = ptnt.getLastName().replaceAll("[0-9]", "");
        ptnt.setFirstName(patientFirstName);
        ptnt.setLastName(patientLastName);
        logger.info("First name: " + ptnt.getFirstName());
        String patientCredential = ptnt.getFirstName().replaceAll("[0-9]", "").toLowerCase() + ptnt.getLastName().replaceAll("[0-9]", "").toLowerCase().charAt(0);
        ptnt.setPassword(patientCredential);
        ptnt.setUserId(patientCredential);
		entityManager.persist(ptnt);
    }

    private void flushBatch(int size, int cnt, String type) {
        if ( (cnt % batchSize == 0) || (size == cnt) )  {
            logger.info((size - cnt) + " " + type + " remaining.");
            entityManager.flush();
            entityManager.clear();
        }
    }

}
