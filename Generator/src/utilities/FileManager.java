package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileManager {
	
	 public static final String GENERATED_PATH = "idl/generated/";
	 public static final String GENERATED_TYPE = ".java";
	
	public static void writeToFile(String objectName, String classString)
			throws IOException {
		String fileName;
		fileName = GENERATED_PATH + objectName + GENERATED_TYPE;
		PrintWriter writer = new PrintWriter(
				new FileWriter(new File(fileName)));

		writer.print(classString);
		writer.flush();
		writer.close();
	}

	public static String readEntirefile(String fileName)
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
