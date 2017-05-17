package generator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import utilities.FileManager;

public class SkeletonGenerator {

	public static void createSkeleton(String fileName)
			throws FileNotFoundException, IOException, ParseException {
		System.out.println("#############################");
		System.out.println("Start - IDL_Parser.");
		System.out.println();

		// Read IDL File
		System.out.println("::: read IDL from file: " + fileName + " :::");
		String jsonText = FileManager.readEntirefile(fileName);
		System.out.println(jsonText);

		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(jsonText);
		String objectName = (String) json.get("ObjectName");
		JSONArray methodes = (JSONArray) json.get("Functions");

		// Fill Description Arrays
		List<String> methodeNames = new ArrayList<>();
		Map<String, Map<Integer, Map<String, String>>> methodeParameterMap = new HashMap<>();
		Map<String, String> methodeReturnMap = new HashMap<>();

		for (Object obj : methodes) {
			JSONObject jsonObj = (JSONObject) obj;
			String methodeName = (String) jsonObj.get("Function_Name");
			methodeNames.add(methodeName);
			JSONArray parameterArray = (JSONArray) jsonObj.get("Parameter");
			Map<Integer, Map<String, String>> parameterPositionMap = new HashMap<>();

			for (Object parameterObj : parameterArray) {
				JSONObject jsonParameterObj = (JSONObject) parameterObj;
				HashMap<String, String> parameterDescriptionMap = new HashMap<>();

				System.out.println(jsonParameterObj);
				System.out.println(jsonParameterObj.get("position"));
				Integer positionInteger = Integer
						.parseInt(jsonParameterObj.get("position").toString());
				parameterDescriptionMap.put("type",
						(String) jsonParameterObj.get("type"));
				parameterDescriptionMap.put("name",
						(String) jsonParameterObj.get("name"));

				parameterPositionMap.put(positionInteger,
						parameterDescriptionMap);
			}
			methodeParameterMap.put(methodeName, parameterPositionMap);
			methodeReturnMap.put(methodeName,
					(String) jsonObj.get("Returntype"));
		}

		// Read Plain Text Skeleton
		System.out.println();
		fileName = "idl/plaintext/plain_text_skeleton.txt";
		System.out.println(
				"::: read plaintext stub from file: " + fileName + " :::");

		String plainTextSkeleton = FileManager.readEntirefile(fileName);
		System.out.println(plainTextSkeleton);

		// Read Plain Text Methoden
		System.out.println();
		fileName = "idl/plaintext/plain_text_methode.txt";
		System.out.println(
				"::: read plaintext methode from file: " + fileName + " :::");

		String plainTextMethode = FileManager.readEntirefile(fileName);
		System.out.println(plainTextMethode);

		// Create Methode Strings and Class String
		StringBuffer parametersBuffer;
		StringBuffer executeParametsBuffer;
		StringBuffer methodesBuffer = new StringBuffer();

		for (String methodeName : methodeNames) {
			parametersBuffer = new StringBuffer();
			Map<Integer, Map<String, String>> parameterPositionMap = methodeParameterMap
					.get(methodeName);
			int i = 1;
			Map<String, String> parameter = parameterPositionMap
					.get(new Integer(i++));

			while (parameter != null) {
				if (i > 2) {
					parametersBuffer.append(", ");
				}

				parametersBuffer.append(parameter.get("type"));
				parametersBuffer.append(" ");
				parametersBuffer.append(parameter.get("name"));

				parameter = parameterPositionMap.get(new Integer(i++));
			}
			String returnType = methodeReturnMap.get(methodeName);
			String returnStatement = "";

			// Here every supported return type has to be listed
			switch (returnType) {
			case "int":
				returnStatement = "return 0;";
				break;
			case "long":
				returnStatement = "return 0L;";
				break;
			case "String":
				returnStatement = "return null;";
				break;
			}

			methodesBuffer.append(String.format(plainTextMethode, returnType,
					methodeName, parametersBuffer.toString(),methodeName, returnStatement));
		}
		
		// Switch case
		StringBuffer methodSelectBuffer = new StringBuffer();

		for (String methodeName : methodeNames) {
			parametersBuffer = new StringBuffer();
			executeParametsBuffer = new StringBuffer();
			
			Map<Integer, Map<String, String>> parameterPositionMap = methodeParameterMap
					.get(methodeName);
			int i = 1;
			Map<String, String> parameter = parameterPositionMap
					.get(new Integer(i++));
            int q = 1;
			while (parameter != null) {
				if (i > 2) {
					executeParametsBuffer.append(", ");
				}
				
				executeParametsBuffer.append(parameter.get("name")+"_"+methodeName);
				
				parametersBuffer.append(parameter.get("type"));
				parametersBuffer.append(" ");
				parametersBuffer.append(parameter.get("name"));
				parametersBuffer.append("_"+methodeName+" = ");
				switch (parameter.get("type")) {
				case "int":
					parametersBuffer.append("(Integer)parameterList.get("+q+"L);");
					break;
				case "long":
					parametersBuffer.append("(Long)parameterList.get("+q+"L);");
					break;
				case "String":
					parametersBuffer.append("(String)parameterList.get("+q+"L);");
					break;
				}
				parametersBuffer.append("\n\t");
				parameter = parameterPositionMap.get(new Integer(i++));
				q++;
			}
			String returnType = methodeReturnMap.get(methodeName);
			String returnStatement = "";

			// Here every supported return type has to be listed
			switch (returnType) {
			case "int":
				returnStatement = "response.add(\"ReturnValue\", %s);";
				break;
			case "long":
				returnStatement = "response.add(\"ReturnValue\", %s);";
				break;
			case "String":
				returnStatement = "response.add(\"ReturnValue\", %s);";
				break;
			}

			methodSelectBuffer.append(String.format(
					"case \"%s\": \n\t %s \n\t %s result_%s = this.imp.%s(%s);\n\t" + returnStatement + "\n\tbreak;\n\t",
					methodeName,parametersBuffer.toString(), returnType, methodeName, methodeName, executeParametsBuffer.toString(),
					("result_" + methodeName)));
		}

		// Generate Methode and Class Strings
		System.out.println("::: Generate the class files :::");

		System.out.println("Now generating : " + objectName
				+ "Skeleton" + ".java");
		String interfaceString = String.format(plainTextSkeleton,
				(objectName + "Skeleton"),
				("Interface" + objectName),
				("Interface" + objectName),
				(objectName + "Skeleton"), 
				("Interface" + objectName),
				(objectName + "Skeleton"), 
				methodSelectBuffer.toString());

		FileManager.writeToFile(objectName+"Skeleton", interfaceString);

		System.out.println();
		System.out.println("End - IDL_Parser");
		System.out.println("###########################");

	}

}
