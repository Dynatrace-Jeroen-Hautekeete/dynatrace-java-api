package be.dynatrace.api.model.v2;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import be.dynatrace.api.client.ApiClient; 
import be.dynatrace.api.env.v2.SettingsSchemas;

public class SettingsSchemaList {

	private int totalcount=0;
	private Map<String,SettingsSchemaSummary> items=null;
	
	protected SettingsSchemaList(){}
	
	public static SettingsSchemaList load(ApiClient ac) {
		try  {
			return new SettingsSchemaList(SettingsSchemas.getSettingsSchemas(ac));
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return new SettingsSchemaList();
		}
	}

	protected SettingsSchemaList(JSONObject schemas) {
		
		items=new HashMap<String,SettingsSchemaSummary>();
		
		// DEBUG
		System.out.println(schemas);
		totalcount=schemas.getInt("totalCount");
		
		JSONArray rawitems=schemas.getJSONArray("items");
		
		rawitems.forEach( (item) -> {
			SettingsSchemaSummary sitem=new SettingsSchemaSummary((JSONObject)item);
			items.put(sitem.getSchemaid(),sitem);
			
			// DEBUG 
			//System.out.println(".. loading Schema: "+sitem.getSchemaid());
		});				
	}

	public int getTotalcount() {
		return totalcount;
	}

	public Map<String, SettingsSchemaSummary> getItems() {
		return items;
	}
	
	public SettingsSchemaSummary getItem(String id) {
		return items.get(id);
	}
}
