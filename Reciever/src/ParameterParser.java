import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;


public class ParameterParser {
	public Map<Long,Integer> parse(JsonObject request) throws Exception {

		Map<Long,Integer> parameterListe;
		JsonArray parameterArray = null;
		Iterator<JsonValue> oarameterArrayIterator;
		
		String parameterType;
		int parameterValue;

		parameterListe = new HashMap<Long,Integer>();
		parameterArray = request.getJsonArray("Parameter");
		oarameterArrayIterator = parameterArray.iterator();

		while (oarameterArrayIterator.hasNext()) {
			JsonObject currentObject = (JsonObject) oarameterArrayIterator.next();
			long parameterPosition = currentObject.getInt("position");
			if (parameterPosition < 0L) {
				break;
			}
			parameterType = currentObject.getString("type");
			parameterValue = currentObject.getInt("value");
			switch (parameterType) {
			case "int":
				parameterListe.put(Long.valueOf(parameterPosition), Integer.valueOf(parameterValue));
				break;
			default:
				System.out.println("Type not supported ");
				throw new Exception("Not Supported Parameter found");
			}
		}
		
		return parameterListe;

	}
}