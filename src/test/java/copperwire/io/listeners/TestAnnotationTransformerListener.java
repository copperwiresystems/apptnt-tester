package copperwire.io.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import tntRestAutomation.ReusableMethods;

public class TestAnnotationTransformerListener implements IAnnotationTransformer {

	private static boolean skipAhead = false;

	// Do not worry about calling this method as testNG calls it behind the scenes
	// before EVERY method (or test).
	// It will disable single tests, not the entire suite like SkipException
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {

		String value = annotation.getDescription();
		System.out.println("Test Method name : " + testMethod.getName() +" description "+value);
		// If we have chose not to run this test then disable it.
		if ((null != value &&  disableMe(value))) {
			System.out.println("Setting annotation Enable = false");
			annotation.setEnabled(false);
		}else {
			annotation.setEnabled(true);
		}
			

//		else if (null == value || 0 == value.length()) {
//			annotation.setEnabled(false);
//		}
	}

	// logic YOU control
	private boolean disableMe(String param) {
		boolean status=skipAhead;
		System.out.println("skipAhead = "+skipAhead);
		if (!skipAhead && ReusableMethods.getStage(param)) {
			skipAhead = true;
		}
		return status;
	}
}