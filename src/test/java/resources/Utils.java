package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Utils {
	JsonData data = new JsonData();
	public static RequestSpecification request_login;
	public static RequestSpecification request_car;
	public static RequestSpecification request_car_auth;
	public static RequestSpecification delete_car;
	public static RequestSpecification get_item;
	
	public static String input(String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("src/test/java/resources/dataSet.properties");
		prop.load(fis);
		return prop.getProperty(key);
	}
	
	public RequestSpecification requestLogin(String email, String password) throws IOException {
		request_login = new RequestSpecBuilder().setBaseUri(input("baseUrl"))
				.setContentType(ContentType.JSON)
				.setBody(data.loginPayload(input(email),input(password)))
				.build();
		return request_login;
	}
	
	public RequestSpecification requestCar() throws IOException {
		request_car = new RequestSpecBuilder().setBaseUri(input("baseUrl"))
				.setContentType(ContentType.JSON)
				.build();
		return request_car;
	}
	
	public RequestSpecification requestCarAuth(String authToken, String id_prod) throws IOException {
		request_car_auth = new RequestSpecBuilder().setBaseUri(input("baseUrl"))
				.setContentType(ContentType.JSON)
				.addHeader("Authorization", authToken)
				.setBody(data.addItemPayload(input(id_prod), 1))
				.build();
		return request_car_auth;
	}
	
	public RequestSpecification deleteCar(String authToken) throws IOException {
		delete_car = new RequestSpecBuilder().setBaseUri(input("baseUrl"))
				.setContentType(ContentType.JSON)
				.addHeader("Authorization", authToken)
				.build();
		return delete_car;
	}
	
	public RequestSpecification visualizarItem(String att1, String value1, String att2, String value2) throws IOException {
		get_item = new RequestSpecBuilder().setBaseUri(input("baseUrl"))
				.setContentType(ContentType.JSON)
				.addQueryParam(input(att1), input(value1))
				.addQueryParam(input(att2), input(value2))
				.build();
		return get_item;
	}
}
