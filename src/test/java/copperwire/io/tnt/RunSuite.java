package copperwire.io.tnt;

import java.util.ArrayList;
import java.util.List;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;


public class RunSuite {

	public static void main(String[] args) {

		TestNG testNG = new TestNG();
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		
		
		for(String x : args) {
			System.out.println("Received args : "+x);
		}

		// pass the name of Suite, Name of the groups to be executed & name of test
		suites.add(createSuite("fetchOrderDetailsTestSuite.xml"));

		testNG.setSuiteThreadPoolSize(1);
		testNG.setXmlSuites(suites);

		testNG.run();
	}

	private static XmlSuite createSuite(String suiteName/* , String groupName, String testName */) {
		List<XmlClass> clazzes = new ArrayList<XmlClass>();
		Class x = null;
		XmlSuite suite = new XmlSuite();
		suite.setName(suiteName);
		// suite.setParallel(XmlSuite.PARALLEL_NONE);

//		LinkedHashMap<String, String> suiteParams = new LinkedHashMap<String, String>();
		// Put in the parameters out here which are required @ suite level
//		suiteParams.put("SuiteKey1", "SuiteValue1");
//		suite.setParameters(suiteParams);

		List<String> listeners = new ArrayList<String>();
		listeners.add("copperwire.io.listeners.TestAnnotationTransformerListener");
		listeners.add("copperwire.io.listeners.EmailExecutionListener");
		suite.setListeners(listeners);

		XmlTest test = new XmlTest(suite);
		test.setName("TEST_NAME_DUMMY");

		switch (suiteName) {
		case "sanityTestSuite.xml":
			x = copperwire.io.tnt.orders.CreateSalesOrderAndVerifyOrdersTest.class;
			test.addParameter("upto_stage", "GET_ORDER");
			break;
		default:
			x = copperwire.io.tnt.orders.GetOrderByIdTest.class;
			test.addParameter("order_id", "136");
			test.addParameter("upto_stage", "GET_ORDER");
			break;
		}

		// This is your class under test
		XmlClass clazz = new XmlClass(x);
		clazzes.add(clazz);
		test.setClasses(clazzes);
		List<XmlTest> tests = new ArrayList<XmlTest>();
		tests.add(test);
		suite.setTests(tests);
		return suite;
	}
}