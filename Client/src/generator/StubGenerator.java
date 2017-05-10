package generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class StubGenerator {

	public static final String idl_filename = "idl/idls/MoveGripper.json";

	public static void main(String[] args)
			throws FileNotFoundException, IOException, ParseException {
		System.out.println("#############################");
		System.out.println("Start - IDL_Parser.");
		System.out.println();

		// Read IDL File
		String fileName = idl_filename;
		System.out.println("::: read IDL from file: " + fileName + " :::");
		String jsonText = readEntirefile(fileName);
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

		// Read Plain Text Stub
		System.out.println();
		fileName = "idl/plaintext/plain_text_stub.txt";
		System.out.println(
				"::: read plaintext stub from file: " + fileName + " :::");

		String plainTextStub = readEntirefile(fileName);
		System.out.println(plainTextStub);

		// Read Plain Text Methoden
		System.out.println();
		fileName = "idl/plaintext/plain_text_methode.txt";
		System.out.println(
				"::: read plaintext methode from file: " + fileName + " :::");

		String plainTextMethode = readEntirefile(fileName);
		System.out.println(plainTextMethode);

		// Create Methode Strings and Class String
		StringBuffer parametersBuffer;
		StringBuffer methodesBuffer = new StringBuffer();
		StringBuffer jsonBuilderBuffer = new StringBuffer();

		for (String methodeName : methodeNames) {
			parametersBuffer = new StringBuffer();
			jsonBuilderBuffer = new StringBuffer();
			Map<Integer, Map<String, String>> parameterPositionMap = methodeParameterMap
					.get(methodeName);
			int i = 1;
			Map<String, String> parameter = parameterPositionMap
					.get(new Integer(i++));

			while (parameter != null) {
				if (i > 2) {
					parametersBuffer.append(", ");
				}
				
				jsonBuilderBuffer.append(getParameterJsonBuilderString(i-1,parameter.get("type"),parameter.get("name")));

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
					methodeName, parametersBuffer.toString(),methodeName,jsonBuilderBuffer, returnStatement));
		}

		// Generate Methode and Class Strings
		System.out.println("::: Generate the class files :::");

		System.out.println("Now generating : " + "Example4_" + objectName
				+ "Stub" + ".java");
		String interfaceString = String.format(plainTextStub,
				(objectName + "Stub"), ("Interface" + objectName),
				(objectName + "Stub"), ("Interface" + objectName),
				methodesBuffer.toString());

		writeToFile(objectName+"Stub", interfaceString);

		System.out.println();
		System.out.println("End - IDL_Parser");
		System.out.println("###########################");

	}

	private static String getParameterJsonBuilderString(int position, String type,
			String name) {
		String plaintext =	"abuilder.add(factory.createObjectBuilder().add(\"position\","+ position+").add(\"type\",\" "+type+"\").add(\"value\",\" "+name+"\"));";
				
		return plaintext;
	}

	private static void writeToFile(String objectName, String classString)
			throws IOException {
		String fileName;
		fileName = "idl/generated/" + objectName + ".java";
		PrintWriter writer = new PrintWriter(
				new FileWriter(new File(fileName)));

		writer.print(classString);
		writer.flush();
		writer.close();
	}

	private static String readEntirefile(String fileName)
			throws FileNotFoundException, IOException {
		BufferedReader reader = new BufferedReader(
				new FileReader(new File(fileName)));

		String line = "";
		StringBuffer buffer = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
			buffer.append("\n");
		}

		reader.close();
		String jsonText = buffer.toString();
		return jsonText;
	}

}
