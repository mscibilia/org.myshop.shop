package org.myshop.shop.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static java.util.Collections.EMPTY_LIST;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.myshop.shop.model.Customer;
import org.myshop.shop.model.PostedSalesOrder;

import org.org.myshop.shop.jpa.model.CustomerEntity;
import org.org.myshop.shop.jpa.model.PostedSalesOrderEntity;

public class JpaPostedSalesOrderDaoTest {

	private final static String TEST_POSTED_SALES_ORDER_ID = "TEST_POSTED_SALES_ORDER_ID";
	private final static Date TEST_POSTED_SALES_ORDER_CREATED = new Date(0);
	
	private static final String TEST_CUSTOMER_ID = "TEST_CUSTOMER_ID";
	private static final String TEST_CUSTOMER_NAME = "TEST_CUSTOMER_NAME";
	
	@Mock
	private EntityManagerFactory factoryMock;
	
	@Mock
	private EntityManager entityManagerMock;
	
	@Mock
	private EntityTransaction entityTransactionMock;
	
	@Mock
	private Query queryMock;
	
	@Mock
	private PostedSalesOrder orderMock;
	
	@Mock
	private PostedSalesOrderEntity orderEntityMock;
	
	@Mock
	private Customer customerMock;
	
	@Mock
	private CustomerEntity customerEntityMock;
	
	@Mock
	private JpaPostedSalesOrderDao postedSalesOrderDaoMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		when(factoryMock.createEntityManager()).thenReturn(entityManagerMock);
		when(entityManagerMock.getTransaction()).thenReturn(entityTransactionMock);
		when(entityManagerMock.createNamedQuery(JpaPostedSalesOrderDao.READ_QUERY_NAME)).thenReturn(queryMock);
		
		when(entityManagerMock.find(PostedSalesOrderEntity.class, TEST_POSTED_SALES_ORDER_ID)).thenReturn(orderEntityMock);
		when(orderEntityMock.toPostedSalesOrder()).thenReturn(orderMock);
		
		when(orderMock.getId()).thenReturn(TEST_POSTED_SALES_ORDER_ID);
		when(orderMock.getCreated()).thenReturn(TEST_POSTED_SALES_ORDER_CREATED);
		when(orderMock.getCustomer()).thenReturn(customerMock);
		
		when(customerMock.getId()).thenReturn(TEST_CUSTOMER_ID);
		when(customerMock.getName()).thenReturn(TEST_CUSTOMER_NAME);
		
		when(orderEntityMock.getId()).thenReturn(TEST_POSTED_SALES_ORDER_ID);
		when(orderEntityMock.getCreated()).thenReturn(TEST_POSTED_SALES_ORDER_CREATED);
		when(orderEntityMock.getCustomer()).thenReturn(customerEntityMock);
		
		when(customerEntityMock.getId()).thenReturn(TEST_CUSTOMER_ID);
		when(customerEntityMock.getName()).thenReturn(TEST_CUSTOMER_NAME);
		
		postedSalesOrderDaoMock = new JpaPostedSalesOrderDao(factoryMock);
		
	}
	
	@Test
	public void testCreate() {
		postedSalesOrderDaoMock.create(orderMock);
		
		verify(entityTransactionMock).begin();
		
		ArgumentCaptor<PostedSalesOrderEntity> postedSalesOrderEntityArgumentCaptor = ArgumentCaptor.forClass(PostedSalesOrderEntity.class);
		verify(entityManagerMock).persist(postedSalesOrderEntityArgumentCaptor.capture());
		
		PostedSalesOrderEntity capturedPostedSalesOrderEntity = postedSalesOrderEntityArgumentCaptor.getValue();
		
		assertEquals(capturedPostedSalesOrderEntity.getId(), TEST_POSTED_SALES_ORDER_ID);
		assertEquals(capturedPostedSalesOrderEntity.getCreated(), TEST_POSTED_SALES_ORDER_CREATED);
		assertEquals(capturedPostedSalesOrderEntity.getCustomer().getId(), TEST_CUSTOMER_ID);
		assertEquals(capturedPostedSalesOrderEntity.getCustomer().getName(), TEST_CUSTOMER_NAME);
		
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testRead() {
		when(queryMock.getResultList()).thenReturn(EMPTY_LIST);

		List<PostedSalesOrder> orderList = postedSalesOrderDaoMock.read();
		
		verify(entityManagerMock).createNamedQuery(JpaPostedSalesOrderDao.READ_QUERY_NAME);
		verify(queryMock).getResultList();
		
		assertNotNull(orderList);
	}
	
	@Test
	public void testGet() {
		orderMock = postedSalesOrderDaoMock.get(TEST_POSTED_SALES_ORDER_ID);
		
		verify(entityManagerMock).find(PostedSalesOrderEntity.class, TEST_POSTED_SALES_ORDER_ID);
		
		assertNotNull(orderMock);
		
		assertEquals(orderMock.getId(), TEST_POSTED_SALES_ORDER_ID);
		assertEquals(orderMock.getCreated(), TEST_POSTED_SALES_ORDER_CREATED);
		assertEquals(orderMock.getCustomer().getId(), TEST_CUSTOMER_ID);
		assertEquals(orderMock.getCustomer().getName(), TEST_CUSTOMER_NAME);
	}
	
	@Test
	public void testUpdate() {
		orderMock = postedSalesOrderDaoMock.update(orderMock);
		
		verify(entityTransactionMock).begin();
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testDelete() {
		postedSalesOrderDaoMock.delete(orderMock);
		
		verify(entityTransactionMock).begin();
		
		ArgumentCaptor<PostedSalesOrderEntity> postedSalesOrderEntityArgumentCaptor = ArgumentCaptor.forClass(PostedSalesOrderEntity.class);
		verify(entityManagerMock).remove(postedSalesOrderEntityArgumentCaptor.capture());
		
		verify(entityTransactionMock).commit();
	}
	
	@Test
	public void testGet_failed() {
		when(entityManagerMock.find(PostedSalesOrderEntity.class, TEST_POSTED_SALES_ORDER_ID)).thenThrow(new NullPointerException());
		
		orderMock = postedSalesOrderDaoMock.get(TEST_POSTED_SALES_ORDER_ID);
		
		assertNull(orderMock);
	}
	
	@Test
	public void testRead_failed() {
		when(queryMock.getResultList()).thenThrow(new PersistenceException());
		
		List<PostedSalesOrder> orderList = postedSalesOrderDaoMock.read();
		
		assertNull(orderList);
	}
	
	@Test
	public void testUpdate_failed() {
		when(postedSalesOrderDaoMock.update(orderMock)).thenReturn(null);
		
		orderMock = postedSalesOrderDaoMock.update(orderMock);
		
		assertNull(orderMock);
	}
}
