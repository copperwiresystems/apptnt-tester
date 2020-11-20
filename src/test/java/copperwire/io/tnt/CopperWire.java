package copperwire.io.tnt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import copperwire.io.tnt.orders.CreateSalesOrderTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import tntRestAutomation.ExtentReportListner;
import tntRestAutomation.Resources;
import tntRestAutomation.ReusableMethods;

import static io.restassured.RestAssured.*;

public class CopperWire extends BaseTest {

	@BeforeTest
	public void beforeTest() {
		log.info("Before Test initiated");
		log.debug("Before Test initiated");
		RestAssured.baseURI = Resources.BASE_URI;
		RestAssured.useRelaxedHTTPSValidation();
}

	@Test
	public void doLogIn() {
		RestAssured.baseURI = "http://tnt7.copperwiresystems.com";

		Response response = given().log().all().header("Content-Type", "application/json")
				.body(ReusableMethods.getLogInCredentials())
				.when().log().all().post("/api/auth/login")

				.then().log().all().assertThat().statusCode(200).extract().response();// response().seasString();
		ReusableMethods.setSessionId(response.header("Authorization"));
	}

}
