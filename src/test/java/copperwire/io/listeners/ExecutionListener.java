package copperwire.io.listeners;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import org.apache.commons.io.output.WriterOutputStream;
import org.testng.IExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import copperwire.io.logger.WriteLogsToDisk;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import tntRestAutomation.ReusableMethods;

public class ExecutionListener implements IExecutionListener, ITestListener {

	private static WriteLogsToDisk logWriter = null;
	private PrintStream printStream;
	private LogConfig originalLogConfig;

	public void onExecutionStart() {
		// Anything before starting suite
		logWriter = new WriteLogsToDisk("./logs/" + ReusableMethods.getDateWithHrMinuteSecond());
	}

	public void onExecutionFinish() {
		// RFU
	}

	public void onStart(ITestContext context) {
		originalLogConfig = RestAssured.config().getLogConfig();
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(logWriter.logFolder + "/" + context.getName() + ".log");
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		printStream = new PrintStream(new WriterOutputStream(fileWriter), true);
		RestAssured.config = RestAssured.config()
				.logConfig(LogConfig.logConfig().defaultStream(printStream).enablePrettyPrinting(true));
		
	}

	public void onFinish(ITestContext context) {
		if (printStream != null) {
			printStream.close();
		}

		if (originalLogConfig != null) {
			RestAssured.config = RestAssuredConfig.config().logConfig(originalLogConfig);
		}
	}
	
}
