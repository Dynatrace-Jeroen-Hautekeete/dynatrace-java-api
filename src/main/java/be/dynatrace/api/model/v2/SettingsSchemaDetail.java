package be.dynatrace.api.model.v2;

import org.json.JSONArray;
import org.json.JSONObject;

import be.dynatrace.api.client.ApiClient;
import be.dynatrace.api.env.v2.SettingsSchemas;

public class SettingsSchemaDetail { 
	
	private String version="";
	private String schemaid="";
	private String displayname="";
	private String[] scopes= {};
	private JSONObject raw=null;
	
	
	public static SettingsSchemaDetail load(ApiClient ac,String schema) {
		try  {
			return new SettingsSchemaDetail(SettingsSchemas.getSettingsSchema(ac,schema));
		} catch (Exception e) {
			return new SettingsSchemaDetail();
		}
	}

	protected SettingsSchemaDetail(){};
	
	
	protected SettingsSchemaDetail(JSONObject schema) {
		raw=schema;
		version=schema.getString("version");
		schemaid=schema.getString("schemaId");
		displayname=schema.getString("displayName");
		
		JSONArray asa=schema.getJSONArray("allowedScopes");
		scopes=new String[asa.length()];
		
		for (int i=0;i<scopes.length;i++) {
			scopes[i]=asa.getString(i);
		}
		
	}

	public String getVersion() {
		return version;
	}

	public String getSchemaid() {
		return schemaid;
	}

	public String getDisplayname() {
		return displayname;
	}

	public String[] getScopes() {
		return scopes;
	}

	public JSONObject getRaw() {
		return raw;
	}
}
