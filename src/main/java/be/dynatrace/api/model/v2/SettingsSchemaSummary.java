package be.dynatrace.api.model.v2;

import org.json.JSONObject;
import be.dynatrace.api.client.ApiClient;

public class SettingsSchemaSummary {
	
	private String latestschemaversion="";
	private String schemaid="";
	private String displayname="";
	private SettingsSchemaDetail detail=null;
	
	protected SettingsSchemaSummary(JSONObject schema) {
		latestschemaversion=schema.getString("latestSchemaVersion");
		schemaid=schema.getString("schemaId");
		displayname=schema.getString("displayName");
	}

	public String getLatestschemaversion() {
		return latestschemaversion;
	}

	public String getSchemaid() {
		return schemaid;
	}

	public String getDisplayname() {
		return displayname;
	}

	public SettingsSchemaDetail getDetail() {
		return detail;
	}
	
	public SettingsSchemaDetail loadDetail(ApiClient ac) {
		if (detail==null)	
			detail=SettingsSchemaDetail.load(ac, schemaid);
		return detail;
	}
	
}
