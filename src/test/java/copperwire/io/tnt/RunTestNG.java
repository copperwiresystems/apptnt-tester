package copperwire.io.tnt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

import tntRestAutomation.EmailReport;

public class RunTestNG {

	public static void step() {
		// Create object of TestNG Class
		TestNG runner = new TestNG();

		// Create a list of String
		List<String> suitefiles = new ArrayList<String>();

		// Add xml file which you have to execute
		suitefiles.add("./sanityTestSuite.xml");

		// now set xml file for execution
		runner.setTestSuites(suitefiles);

		// finally execute the runner using run method
		runner.run();
	}

	public static void main(String[] args) {
		try {
			step();
			EmailReport.sendReport();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}