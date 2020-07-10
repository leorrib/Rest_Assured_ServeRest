package cenariosTeste;

import org.testng.annotations.Test;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import resources.JsonData;
import resources.Utils;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

public class LoginEndpointTests extends Utils {
	JsonData data = new JsonData();
	RequestSpecification request_login;
	ValidatableResponse response;

	@Test
	public void loginEmptyData() throws IOException {

		request_login = given().spec(requestLogin("empty","empty"));

		response = request_login.when().post("/login")
				.then()
				.assertThat()
				.statusCode(400)
				.body("error.details[0].email", equalTo("\"email\" is not allowed to be empty"))
				.body("error.details[0].password", equalTo("\"password\" is not allowed to be empty"));
	}

	@Test
	public void loginInvalidUser() throws IOException {

		request_login = given().spec(requestLogin("invalidEmail","invalidPassword"));

		response = request_login.when().post("/login")
				.then()
				.assertThat()
				.statusCode(401)
				.body("message", equalTo("Email e/ou senha inválidos"));
	}

	@Test
	public void loginUser() throws IOException {

		request_login = given().spec(requestLogin("validEmail","validPassword"));

		response = request_login.when().post("/login")
				.then()
				.assertThat()
				.statusCode(200)
				.body("message", equalTo("Login realizado com sucesso"));
	}
}
