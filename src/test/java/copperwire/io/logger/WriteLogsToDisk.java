package copperwire.io.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UncheckedIOException;

import org.apache.commons.io.output.WriterOutputStream;
import org.testng.ITestContext;
import org.testng.ITestListener;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;

public class WriteLogsToDisk {

	public final String logFolder;

	public WriteLogsToDisk(String logFolder) {
		// this.logFolder = "";
		File dir = new File(logFolder);
		if (!dir.exists()) {
			// noinspection ResultOfMethodCallIgnored
			dir.mkdirs();
		}
		if (logFolder.endsWith("/")) {
			this.logFolder = logFolder.substring(0, logFolder.length() - 1);
		} else {
			this.logFolder = logFolder;
		}
	}
}