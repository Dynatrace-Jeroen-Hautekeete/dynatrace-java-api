package be.dynatrace.api.model.v2;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import be.dynatrace.api.client.ApiClient;
import be.dynatrace.api.env.v2.SettingsObjects;

public class SettingsObjectList {

	// private int totalcount=0;
	private Map<String,SettingsObjectSummary> items=null;
	
	protected SettingsObjectList(){}
	

	public static SettingsObjectList load(ApiClient ac,String[] schemas,String[] scopes) {
		// DEBUG
		//System.out.println("loading objects for "+ApiUtil.arrayToCsv(schemas)+":"+ApiUtil.arrayToCsv(scopes));
		
		try  {
			return new SettingsObjectList(SettingsObjects.getSettingsObjects(ac,schemas,scopes));
		} catch (Exception e) {
			// DEBUG
			e.printStackTrace(System.err);
			return new SettingsObjectList();
		}
	}

	public static SettingsObjectList load(ApiClient ac,String schema) {
		// DEBUG
		//System.out.println("loading objects for "+ApiUtil.arrayToCsv(schemas)+":"+ApiUtil.arrayToCsv(scopes));
		
		try  {
			return new SettingsObjectList(SettingsObjects.getSettingsObjects(ac,schema));
		} catch (Exception e) {
			// DEBUG
			e.printStackTrace(System.err);
			return new SettingsObjectList();
		}
	}
	
	protected SettingsObjectList(JSONArray objects) {
		
		// DEBUG
		// System.out.println(objects);
		
		items=new HashMap<String,SettingsObjectSummary>();
		
		// totalcount=schemas.getInt("totalCount");
		objects.forEach( (item) -> {
			SettingsObjectSummary sitem=new SettingsObjectSummary((JSONObject)item);
			items.put(sitem.getObjectid(),sitem);
			
			//DEBUG
			// System.out.println(sitem.getObjectid()+" :: "+sitem.getScope()+" :: "+sitem.getSchemaid()+" :: "+sitem.getValue());
			//System.out.println(sitem.getObjectid()+" :: "+sitem.getScope()+" :: "+sitem.getSchemaid());
			System.out.print(".");
		});				
		System.out.println();
	}


	public Map<String, SettingsObjectSummary> getItems() {
		return items;
	}
	
	public SettingsObjectSummary getItem(String id) {
		return items.get(id);
	}
	
	public boolean hasItems() {
		return (items.size()>0);
	}
}
