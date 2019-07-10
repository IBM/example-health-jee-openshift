import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		DataService dataService = new DataService();
		
		JSONObject test;
		JSONObject response;
		JSONObject internalError = new JSONObject();
		
		try {
			internalError = new JSONObject("{StatusCode: 500,StatusDescription: Internal Error}");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			
			/*BufferedReader reader = new BufferedReader(new FileReader("src/apidata.json"));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			String ls = System.getProperty("line.separator");
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}
			// delete the last new line separator
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			reader.close();

			String json = stringBuilder.toString().replaceAll("\n", "");
			//System.out.println(json);
			
			
			test = new JSONObject(json);
			
			response = dataService.generate(test);
			System.out.println(response.toString());*/
			
			response = dataService.getPrescriptionsCount();
			System.out.println(response.toString());
		} catch (JSONException e) {
			e.printStackTrace();
			response = internalError;
		}

	}

}
