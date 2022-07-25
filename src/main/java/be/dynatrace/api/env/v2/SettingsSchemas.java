package be.dynatrace.api.env.v2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.json.JSONArray;
import org.json.JSONObject;

import be.dynatrace.api.client.ApiClient;

public class SettingsSchemas {
	public static final String SETTINGSSCHEMAS="/api/v2/settings/schemas";
	
	public static JSONObject getSettingsSchemas(ApiClient ac) throws Exception {
		Map<String,List<String>> params=new HashMap<String,List<String>>();				
		return ac.executeJsonGetRequest(SETTINGSSCHEMAS, params);
	}

	public static JSONObject getSettingsSchema(ApiClient ac, String schemaid) throws Exception {
		Map<String,List<String>> params=new HashMap<String,List<String>>();				
		return ac.executeJsonGetRequest(SETTINGSSCHEMAS+"/"+schemaid, params);
	}

}
