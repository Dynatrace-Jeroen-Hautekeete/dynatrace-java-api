package be.dynatrace.api.model.v2;

import org.json.JSONObject;

public class EntityTypeSummary {
	
	private String type="";
	
	protected EntityTypeSummary(JSONObject entity) {
		type=entity.getString("type");
	}

	public String getType() {
		return type;
	}
	
}
