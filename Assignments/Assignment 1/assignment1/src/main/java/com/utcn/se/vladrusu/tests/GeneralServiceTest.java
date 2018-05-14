//package com.utcn.se.vladrusu.tests;
//
//import com.utcn.se.vladrusu.services.GeneralService;
//import com.utcn.se.vladrusu.services.IGeneralService;
//
//import junit.framework.TestCase;
//
//public class GeneralServiceTest extends TestCase {
//	
//	IGeneralService generalService = null;
//	
//	@Override
//	protected void setUp() throws Exception {
//		super.setUp();
//		generalService = new GeneralService();
//	}
//	
//	public void testLogin() {
//		// Valid credentials
//		String username = "vladrusu";
//		String password = "password";
//		
//		assertTrue(generalService.logIn(username, password));
//		
//		// Invalid username
//		username = "asdf";
//		boolean exceptionThrown = false;
//		
//		try {
//			generalService.logIn(username, password);
//		} catch (Exception e) {
//			exceptionThrown = true;
//		}
//		
//		assertTrue(exceptionThrown);
//	}
//	
//	public void testIsValidUser() {
//		assertTrue(generalService.isValidUser("employee"));
//		assertFalse(generalService.isValidUser("Some_dummy_string"));
//	}
//
//}
