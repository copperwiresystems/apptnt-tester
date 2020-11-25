package copperwire.io.listeners;

import java.io.IOException;

import org.testng.IExecutionListener;

import tntRestAutomation.EmailReport;

public class EmailExecutionListener implements IExecutionListener {

	public void onExecutionStart() {
		// Anything before starting suite
	}

	public void onExecutionFinish() {
		// System.out.println("Notify by mail, TestNG is finished");
		try {
			EmailReport.sendReport();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
