package utilities;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;


public class ParameterParser {
	public Map<Long,Object> parse(JsonObject request) throws Exception {

		Map<Long, Object> parameterListe;
		JsonArray parameterArray = null;
		Iterator<JsonValue> oarameterArrayIterator;
		
		String parameterType;
		String parameterValue;

		parameterListe = new HashMap<Long,Object>();
		parameterArray = request.getJsonArray("Parameter");
		oarameterArrayIterator = parameterArray.iterator();

		while (oarameterArrayIterator.hasNext()) {
			JsonObject currentObject = (JsonObject) oarameterArrayIterator.next();
			long parameterPosition = currentObject.getInt("position");
			
			if (parameterPosition < 0L) {
				break;
			}
			
			parameterType = currentObject.getString("type");
			
			switch (parameterType) {
			case "int":
				parameterListe.put(Long.valueOf(parameterPosition), (Object)currentObject.getInt("value"));
				break;
			case "long":
				parameterListe.put(Long.valueOf(parameterPosition), (Object)currentObject.getInt("value"));
				break;
			case "String":
				parameterListe.put(Long.valueOf(parameterPosition), (Object)currentObject.getString("value"));
				break;
			default:
				System.out.println("Type not supported ");
				throw new Exception("Not Supported Parameter found");
			}
		}
		
		return parameterListe;

	}
}
