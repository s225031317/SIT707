package sit707_week4;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests functions in LoginForm.
 * @author Ahsan Habib
 */
public class LoginFormTest 
{

	@Test
	public void testStudentIdentity() {
		String studentId = "225031317";
		System.out.println("Student ID: " + studentId);
		Assert.assertNotNull("Student ID is null", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = "sandesh bhatt";
		System.out.println("Student Name: " + studentName);
		Assert.assertNotNull("Student name is null", studentName);
	}
	
	@Test
    public void testFailEmptyUsernameAndEmptyPasswordAndDontCareValCode()
    {
		LoginStatus status = LoginForm.login(null, null);
		System.out.println("Test: Empty username and empty password - Login Success: " + status.isLoginSuccess());
		Assert.assertTrue( status.isLoginSuccess() == false );
    }
	
	// Decision Table Test Cases
	// Based on: Username (- = empty, W = wrong/invalid, C = correct/valid)
	//           Password (- = empty, W = wrong/invalid, C = correct/valid)
	//           Validation Code (- = empty, W = wrong/invalid, C = correct/valid)
	
	// Test Case 1: Username = -, Password = -, Validation Code = X (don't care)
	@Test
	public void testCase1_EmptyUsername_EmptyPassword() {
		LoginStatus status = LoginForm.login(null, null);
		System.out.println("Test Case 1: Empty Username, Empty Password - Result: " + status.isLoginSuccess());
		Assert.assertFalse("Expected login failure for empty username and empty password", status.isLoginSuccess());
		Assert.assertEquals("Empty Username", status.getErrorMsg());
	}
	
	// Test Case 2: Username = -, Password = W, Validation Code = X
	@Test
	public void testCase2_EmptyUsername_WrongPassword() {
		LoginStatus status = LoginForm.login(null, "wrongPass");
		System.out.println("Test Case 2: Empty Username, Wrong Password - Result: " + status.isLoginSuccess());
		Assert.assertFalse("Expected login failure for empty username and wrong password", status.isLoginSuccess());
		Assert.assertEquals("Empty Username", status.getErrorMsg());
	}
	
	// Test Case 3: Username = -, Password = C, Validation Code = X
	@Test
	public void testCase3_EmptyUsername_CorrectPassword() {
		LoginStatus status = LoginForm.login(null, "ahsan_pass");
		System.out.println("Test Case 3: Empty Username, Correct Password - Result: " + status.isLoginSuccess());
		Assert.assertFalse("Expected login failure for empty username even with correct password", status.isLoginSuccess());
		Assert.assertEquals("Empty Username", status.getErrorMsg());
	}
	
	// Test Case 4: Username = W, Password = -, Validation Code = X
	@Test
	public void testCase4_WrongUsername_EmptyPassword() {
		LoginStatus status = LoginForm.login("wrongUser", null);
		System.out.println("Test Case 4: Wrong Username, Empty Password - Result: " + status.isLoginSuccess());
		Assert.assertFalse("Expected login failure for wrong username and empty password", status.isLoginSuccess());
		Assert.assertEquals("Empty Password", status.getErrorMsg());
	}
	
	// Test Case 5: Username = W, Password = W, Validation Code = X
	@Test
	public void testCase5_WrongUsername_WrongPassword() {
		LoginStatus status = LoginForm.login("wrongUser", "wrongPass");
		System.out.println("Test Case 5: Wrong Username, Wrong Password - Result: " + status.isLoginSuccess());
		Assert.assertFalse("Expected login failure for wrong username and wrong password", status.isLoginSuccess());
		Assert.assertEquals("Credential mismatch", status.getErrorMsg());
	}
	
	// Test Case 6: Username = W, Password = C, Validation Code = X
	@Test
	public void testCase6_WrongUsername_CorrectPassword() {
		LoginStatus status = LoginForm.login("wrongUser", "ahsan_pass");
		System.out.println("Test Case 6: Wrong Username, Correct Password - Result: " + status.isLoginSuccess());
		Assert.assertFalse("Expected login failure for wrong username even with correct password", status.isLoginSuccess());
		Assert.assertEquals("Credential mismatch", status.getErrorMsg());
	}
	
	// Test Case 7: Username = C, Password = -, Validation Code = X
	@Test
	public void testCase7_CorrectUsername_EmptyPassword() {
		LoginStatus status = LoginForm.login("ahsan", null);
		System.out.println("Test Case 7: Correct Username, Empty Password - Result: " + status.isLoginSuccess());
		Assert.assertFalse("Expected login failure for correct username but empty password", status.isLoginSuccess());
		Assert.assertEquals("Empty Password", status.getErrorMsg());
	}
	
	// Test Case 8: Username = C, Password = W, Validation Code = X
	@Test
	public void testCase8_CorrectUsername_WrongPassword() {
		LoginStatus status = LoginForm.login("ahsan", "wrongPass");
		System.out.println("Test Case 8: Correct Username, Wrong Password - Result: " + status.isLoginSuccess());
		Assert.assertFalse("Expected login failure for correct username but wrong password", status.isLoginSuccess());
		Assert.assertEquals("Credential mismatch", status.getErrorMsg());
	}
	
	// Test Case 9: Username = C, Password = C, Validation Code = X
	@Test
	public void testCase9_CorrectUsername_CorrectPassword() {
		LoginStatus status = LoginForm.login("ahsan", "ahsan_pass");
		System.out.println("Test Case 9: Correct Username, Correct Password - Login Success: " + status.isLoginSuccess());
		Assert.assertTrue("Expected login success for correct username and password", status.isLoginSuccess());
		
		// Verify the validation code is returned correctly (stored in errorMsg for successful login)
		String validationCode = status.getErrorMsg();
		System.out.println("Validation Code received: " + validationCode);
		Assert.assertNotNull("Validation code should not be null", validationCode);
		Assert.assertEquals("Validation code should be 123456", "123456", validationCode);
		
		// Test validateCode with correct code
		boolean isValid = LoginForm.validateCode("123456");
		System.out.println("ValidateCode with '123456': " + isValid);
		Assert.assertTrue("Validation code should be valid", isValid);
	}
	
	// Test Case 10: Username = C, Password = C, Validation Code = W (wrong validation code after successful login)
	@Test
	public void testCase10_CorrectUsername_CorrectPassword_WrongValCode() {
		LoginStatus status = LoginForm.login("ahsan", "ahsan_pass");
		Assert.assertTrue("Expected login success for correct username and password", status.isLoginSuccess());
		System.out.println("Test Case 10: Testing with wrong validation code");
		
		// Test validateCode function with wrong validation code
		boolean isValid = LoginForm.validateCode("wrongValidationCode");
		System.out.println("ValidateCode with 'wrongValidationCode': " + isValid);
		Assert.assertFalse("Wrong validation code should be invalid", isValid);
	}
	
	// Test Case 11: Username = C, Password = C, Validation Code = C (correct validation code)
	@Test
	public void testCase11_CorrectUsername_CorrectPassword_CorrectValCode() {
		LoginStatus status = LoginForm.login("ahsan", "ahsan_pass");
		Assert.assertTrue("Expected login success for correct username and password", status.isLoginSuccess());
		System.out.println("Test Case 11: Testing with correct validation code");
		
		// Get the validation code from LoginStatus (stored in errorMsg for successful login)
		String validationCode = status.getErrorMsg();
		System.out.println("Validation Code from login: " + validationCode);
		Assert.assertNotNull("Validation code should not be null", validationCode);
		Assert.assertEquals("Validation code should be 123456", "123456", validationCode);
		
		// Test validateCode function with correct validation code
		boolean isValid = LoginForm.validateCode(validationCode);
		System.out.println("ValidateCode with correct code: " + isValid);
		Assert.assertTrue("Correct validation code should be valid", isValid);
	}
	
	// Additional test for empty validation code scenario
	@Test
	public void testEmptyValidationCode() {
		LoginStatus status = LoginForm.login("ahsan", "ahsan_pass");
		Assert.assertTrue("Expected login success for correct username and password", status.isLoginSuccess());
		System.out.println("Test: Empty validation code");
		
		boolean isValid = LoginForm.validateCode(null);
		System.out.println("ValidateCode with null: " + isValid);
		Assert.assertFalse("Null validation code should be invalid", isValid);
	}
	
	// Additional test for empty string validation code
	@Test
	public void testEmptyStringValidationCode() {
		LoginStatus status = LoginForm.login("ahsan", "ahsan_pass");
		Assert.assertTrue("Expected login success for correct username and password", status.isLoginSuccess());
		System.out.println("Test: Empty string validation code");
		
		boolean isValid = LoginForm.validateCode("");
		System.out.println("ValidateCode with empty string: " + isValid);
		Assert.assertFalse("Empty string validation code should be invalid", isValid);
	}
	
	// Additional test for wrong validation code after successful login
	@Test
	public void testWrongValidationCodeAfterLogin() {
		LoginStatus status = LoginForm.login("ahsan", "ahsan_pass");
		Assert.assertTrue("Expected login success for correct username and password", status.isLoginSuccess());
		System.out.println("Test: Wrong validation code after login");
		
		boolean isValid = LoginForm.validateCode("incorrectCode");
		System.out.println("ValidateCode with 'incorrectCode': " + isValid);
		Assert.assertFalse("Incorrect validation code should be invalid", isValid);
	}
}