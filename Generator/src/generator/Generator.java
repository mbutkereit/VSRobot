package generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

public class Generator {

	public static void main(String[] args) throws FileNotFoundException,
			IOException, ParseException, org.json.simple.parser.ParseException, java.text.ParseException {
		if (args.length == 1) {
			String IDL_PATH = args[0];
			File f = new File(IDL_PATH);
			if (f.exists() && f.isFile()) {
				
				ImplementationGenerator.createImplementation(IDL_PATH);
				InterfaceGenerator.createInterface(IDL_PATH);
				SkeletonGenerator.createSkeleton(IDL_PATH);
				StubGenerator.createStub(IDL_PATH);
				
			} else {
				System.out.println("IDL existiert nicht.");
			}

		} else {
			System.out.println("Sie muessen eine Gueltige IDL uebergeben.");
		}
	}

}
