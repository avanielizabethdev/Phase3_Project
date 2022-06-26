package apiChaining;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndToEndTest {
	
	Response response;
	String BaseURI = "http://54.89.89.130:8088/employees";
	
	@Test
	public void testEC2()
	{
				
		response = GetMethodAll();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		response = PostMethod("Tissa","Eli", "1000","tissa@abc.com");
		Assert.assertEquals(response.getStatusCode(), 201);
		JsonPath Jpath =response.jsonPath();
		int EmpId = Jpath.get("id");
	    System.out.println("ID: "+EmpId);
	    
	    response = PutMethod(EmpId, "Ammu","Eli", "6000","abc@abc.com");
	    Assert.assertEquals(response.getStatusCode(), 200);
	    Jpath =response.jsonPath();
	   Assert.assertEquals(Jpath.get("firstName"), "Ammu");
	    
	    response = DeleteMethod(EmpId);
	    Assert.assertEquals(response.getStatusCode(), 200);
	    Assert.assertEquals(response.getBody().asString(),"");
	    
	    response = GetMethod(EmpId);
		Assert.assertEquals(response.getStatusCode(), 400);
		Jpath =response.jsonPath();
		Assert.assertEquals(Jpath.get("message"), "Entity Not Found");
		
	
	}
	
	
	public Response GetMethodAll() {
		
		RestAssured.baseURI = BaseURI;
		RequestSpecification request = RestAssured.given();
		Response response = request.get();
		
		return response;
	}
	
	public Response PostMethod(String FirstName,String LastName, String Salary, String Email) {
		
		RestAssured.baseURI = BaseURI;
		
		JSONObject jobj = new JSONObject();
		jobj.put("firstName", FirstName);
		jobj.put("lastName", LastName);
		jobj.put("salary", Salary);
		jobj.put("email", Email);
		
		RequestSpecification request = RestAssured.given();
		
		Response response = request.contentType(ContentType.JSON)
									.accept(ContentType.JSON)
									.body(jobj.toString())
									.post();
		
		return response;
		
	}
	
	public Response PutMethod(int EmpId, String FirstName,String LastName, String Salary, String Email) {
		
		RestAssured.baseURI = BaseURI;
		
		JSONObject jobj = new JSONObject();
		jobj.put("firstName", FirstName);
		jobj.put("lastName", LastName);
		jobj.put("salary", Salary);
		jobj.put("email", Email);
		
		RequestSpecification request = RestAssured.given();
		
		Response response = request.contentType(ContentType.JSON)
									.accept(ContentType.JSON)
									.body(jobj.toString())
									.put("/" +EmpId);
		
		return response;
		
	}
	
	public Response DeleteMethod(int EmpId) {
		
		RestAssured.baseURI = BaseURI;
		RequestSpecification request = RestAssured.given();
		Response response = request.delete("/"+ EmpId);
	
		return response;
	}
	
	public Response GetMethod(int EmpId) {
		
		RestAssured.baseURI = BaseURI;
		RequestSpecification request = RestAssured.given();
		Response response = request.get("/"+ EmpId);
		
		return response;
	}

}
