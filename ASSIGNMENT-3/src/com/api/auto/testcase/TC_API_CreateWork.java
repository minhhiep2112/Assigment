package com.api.auto.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.api.auto.utils.PropertiesFileUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TC_API_CreateWork {
	
	
	String baseURL = PropertiesFileUtils.getProperties("baseURL");
	String createWorkPath = PropertiesFileUtils.getProperties("createWorkPath");
	
	String createWork_body = "{\r\n"
			+ "    \"nameWork\": \"WORK\",   \r\n"
			+ "    \"experience\": \"EXP\",  \r\n"
			+ "    \"education\": \"EDU\"    \r\n"
			+ "}";
	
	String nameWork = "Thủ quỹ";
	String experience = "2 năm";
	String education = "THPT";
	
	String tokenValue = PropertiesFileUtils.getToken("token");
	
	Response createWork_response;
	
	
	@BeforeClass
	public void init() {
		
		
		RequestSpecification createWork_specifications = RestAssured.given();
		createWork_specifications.header("content-type","application/json");
		createWork_specifications.header("token",tokenValue);
		createWork_specifications.body(createWork_body.replace("WORK", nameWork).replace("EXP", experience).replace("EDU", education));
				
		createWork_specifications.baseUri(baseURL);
		createWork_specifications.basePath(createWorkPath);
		createWork_specifications.log().all();
				
		createWork_response = createWork_specifications.post();
		
						
		System.out.println(createWork_response.statusCode());
		System.out.println(createWork_response.asPrettyString());
			
	}
	
	@Test(priority = 0)
	public void TC01_Validate201Created() {
		
		// kiểm chứng status code 201
		assertEquals(createWork_response.statusCode(), 201,"Error - Status code không đúng!");		
	}
	
	@Test(priority = 1)
	public void TC02_ValidateWorkId() {
		
		// kiểm chứng id
		assertTrue(createWork_response.asPrettyString().contains("id"),"Error - Response không có field id!");
	}
	
	@Test(priority = 2)
	public void TC03_ValidateNameOfWorkMatched() {
		
		// kiểm chứng tên công việc có giống lúc tạo
		assertEquals(createWork_response.jsonPath().getString("nameWork"), nameWork, "Error - tên công việc trả về không đúng!");
	}
	
	@Test(priority = 3)
	public void TC04_ValidateExperienceMatched() {
		
		// kiểm chứng kinh nghiệm có giống lúc tạo
		assertEquals(createWork_response.jsonPath().getString("experience"), experience, "Error - kinh nghiệm trả về không đúng!");
	}

	@Test(priority = 4)
	public void TC05_ValidateEducationMatched() {
		
        // kiểm chứng học vấn nhận được có giống lúc tạo
        assertEquals(createWork_response.jsonPath().getString("education"), education, "Error - học vấn trả về không đúng!");   
	}

	
	
	
	
}
