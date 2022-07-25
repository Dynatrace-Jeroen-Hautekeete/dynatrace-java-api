package be.dynatrace.api.client;

import java.util.Vector;


public class ApiUtil {
	
	public static String arrayToCsv(String[] inarray) {
		
		if (inarray.length==0) return "";
//		if (inarray.length==1) return inarray[0];

		String csv=inarray[0];
		for (int i=1;i<inarray.length;i++) {
			csv=csv+","+inarray[i];
		}
		return csv;
	}

	public static String[] vectorToArray(Vector<String> invector) {
		String[] elems = new String[invector.size()];
		for (int i=0;i< elems.length;i++) {
			elems[i]=invector.get(i);
		}
		return elems;
	}	
	
}
