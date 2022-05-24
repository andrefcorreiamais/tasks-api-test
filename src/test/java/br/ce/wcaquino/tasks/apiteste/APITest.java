package br.ce.wcaquino.tasks.apiteste;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass  //executa antes de instanciar a classe, por isso precisa ser static
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
		
	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200)
		;
	}

	@Test
	public void deveAdiconarTarefaComSucesso() {
		
		RestAssured.given()
			.body("{\"task\": \"Teste via API\",\"dueDate\": \"2022-05-19\"}") // data futura
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(201)
		;
	}
	
	@Test
	public void naoDeveAdiconarTarefaInvalida() {
		
		RestAssured.given()
			.body("{\"task\": \"Teste via API\",\"dueDate\": \"2020-05-19\"}") // data antiga
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))
	
		;
	}
}
