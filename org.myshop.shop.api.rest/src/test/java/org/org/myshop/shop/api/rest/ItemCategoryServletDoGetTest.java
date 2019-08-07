package org.org.myshop.shop.api.rest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myshop.shop.dao.ItemCategoryDao;
import org.myshop.shop.model.ItemCategory;
import org.org.myshop.shop.api.rest.servlet.util.IItemCategoryDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IItemCategorySerializer;

import com.fasterxml.jackson.core.JsonProcessingException;

public class ItemCategoryServletDoGetTest {

	private static final String TEST_JSON_STRING = "{}";
	
	private ItemCategoryServlet itemCategoryServlet;
	
	@Mock
	private IItemCategorySerializer itemCategorySerializerMock;
	
	@Mock
	private ItemCategoryDao itemCategoryDaoMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	private PrintWriter printWriterMock;
	
	@Mock
	private List<ItemCategory> itemCategoryListMock;
	
	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		
		itemCategoryServlet = new ItemCategoryServlet();
		itemCategoryServlet.setItemCategoryDao(itemCategoryDaoMock);
		itemCategoryServlet.setItemCategorySerializer(itemCategorySerializerMock);
		
		when(itemCategoryDaoMock.read()).thenReturn(itemCategoryListMock);
		when(itemCategorySerializerMock.serializerList(itemCategoryListMock)).thenReturn(TEST_JSON_STRING);
		when(responseMock.getWriter()).thenReturn(printWriterMock);
	}

	@Test
	public void testSuccess() throws IOException {
		itemCategoryServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testEmptyList() throws IOException {
		when(itemCategoryDaoMock.read()).thenReturn(Collections.<ItemCategory>emptyList());
		
		itemCategoryServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_NO_CONTENT);
	}
	
	@Test
	public void testNullList() throws IOException{
		when(itemCategoryDaoMock.read()).thenReturn(null);
		
		itemCategoryServlet.doGet(requestMock, responseMock);
		
		verify(responseMock).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}