package com.eulerity.hackathon.imagefinder;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.mockito.Mockito;

import com.google.gson.Gson;

public class ImageFinderTest {

	public HttpServletRequest request;
	public HttpServletResponse response;
	public StringWriter sw;
	public HttpSession session;

	@Before
	public void setUp() throws Exception {
		request = Mockito.mock(HttpServletRequest.class);
		response = Mockito.mock(HttpServletResponse.class);
    sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
		Mockito.when(response.getWriter()).thenReturn(pw);
		Mockito.when(request.getRequestURI()).thenReturn("/home");
		Mockito.when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/home"));
		session = Mockito.mock(HttpSession.class);
		Mockito.when(request.getSession()).thenReturn(session);
	}
	
  @Test
  public void test() throws IOException, ServletException {
		Mockito.when(request.getServletPath()).thenReturn("/home");
		Mockito.when(request.getParameter("url")).thenReturn("test");
		new ImageFinder().doPost(request, response);
		Assert.assertEquals(new Gson().toJson(ImageFinder.testImages), sw.toString());
  }

	@Test
	public void testValid() throws IOException, ServletException {
		String urlToTest1 = "https://www.joejuice.com/";
		Mockito.when(request.getServletPath()).thenReturn("/home");
		Mockito.when(request.getParameter("url")).thenReturn(urlToTest1);
		new ImageFinder().doPost(request, response);
		Assert.assertNotEquals(new Gson().toJson(new String[]{}), sw.toString());
	}
}



