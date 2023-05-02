package com.api.auto.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.api.auto.utils.PropertiesFileUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TC_API_Login {
	
	String username = PropertiesFileUtils.getProperties("account");
	String password = PropertiesFileUtils.getProperties("password");
	String baseURL = PropertiesFileUtils.getProperties("baseURL");
	String loginpath = PropertiesFileUtils.getProperties("loginPath");

	Response login_response;
	
	
	@BeforeClass
	public void init() {
		
		
		RequestSpecification login_specifications = RestAssured.given();
		login_specifications.header("content-type","application/json");
		login_specifications.body("{\"account\":\"UNAME\",\"password\":\"UPASS\"}".replace("UNAME", username).replace("UPASS", password));
		login_specifications.baseUri(baseURL);
		login_specifications.basePath(loginpath);
		login_specifications.log().all();
				
		login_response = login_specifications.post();
		
						
		System.out.println(login_response.statusCode());
		System.out.println(login_response.asPrettyString());
			
	}
	
	@Test(priority = 0)
	public void TC01_Validate200Ok() {
		
		// kiểm tra status code
		assertEquals(login_response.statusCode(), 200,"Error - Status code không đúng!");
		
	}
	
	@Test(priority = 1)
	public void TC02_ValidateID() {
		
		// kiểm tra trường id
		assertTrue(login_response.asString().contains("id"), "Error - Response không có trường id!");
	}
	
	
	@Test(priority = 2)
	public void TC03_ValidateMessage() {
		
		// kiểm tra trường message
		assertTrue(login_response.asString().contains("message"),"Error - Không có trường message!");
		assertEquals(login_response.jsonPath().getString("message"), "Đăng nhập thành công", "Error - Nội dung message không đúng!");
	}
	
	@Test(priority = 3)
	public void TC04_ValidateToken(){
		
		// kiểm tra token
		assertTrue(login_response.asString().contains("token"), "Error - Response không có trường token!");
		PropertiesFileUtils.saveToken("token", login_response.jsonPath().getString("token"));
	}
	
	@Test(priority = 4)
	public void TC05_ValidateUserType() {
		
		// kiểm tra user type
		assertTrue(login_response.asString().contains("type"), "Error - Response không có trường type!");
		assertEquals(login_response.jsonPath().getString("user.type"), "UNGVIEN", "Error - Nội dung trường type không đúng!");
	}
	
	@Test(priority = 5)
	public void TC06_ValidateAccount() {
		
		// kiểm tra thông tin user
		assertTrue(login_response.asString().contains("user"), "Error - Repsonse không có chứa thông tin user!");
		assertTrue(login_response.asString().contains("account"), "Error - Repsonse không có chứa thông tin account!");
		assertTrue(login_response.asString().contains("password"), "Error - Repsonse không có chứa thông tin password!");
		assertEquals(login_response.jsonPath().getString("user.account"), username,"Error - Username trả về không khớp!");
		assertEquals(login_response.jsonPath().getString("user.password"), password,"Error - Mật khẩu trả về không khớp!");
		
	}
	
	
	
	
	
	
	
	
	
	
}
