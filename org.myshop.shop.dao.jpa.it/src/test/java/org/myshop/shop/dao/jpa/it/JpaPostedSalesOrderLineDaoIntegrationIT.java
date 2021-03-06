package org.myshop.shop.dao.jpa.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.myshop.shop.dao.jpa.JpaPostedSalesOrderLineDao;
import org.myshop.shop.model.Item;
import org.myshop.shop.model.PostedSalesOrderLine;

public class JpaPostedSalesOrderLineDaoIntegrationIT extends BaseItemAwareIntegrationTest{

	private JpaPostedSalesOrderLineDao postedSalesOrderLineDao;
	
	@Before
	public void setup() {
		super.setup();
		
		postedSalesOrderLineDao = new JpaPostedSalesOrderLineDao(factory);
	}
	
	@Test
	public void testCreate() {
		
		Item item = itemDao.get("test_item_id"); 	
		
		PostedSalesOrderLine line = new PostedSalesOrderLine();
		
		line.setId("test_id");
		line.setItem(item);
		line.setLineNumber(123);
		line.setPrice(123.456f);
		line.setQuantity(123);
		line.setAmmount(123);
		
		postedSalesOrderLineDao.create(line);
	}
	
	@Test
	public void testRead() {
		
		Item item = itemDao.get("test_item_id");
		
		PostedSalesOrderLine line = new PostedSalesOrderLine();
		
		line.setId("test_id_1");
		line.setItem(item);
		line.setLineNumber(123);
		line.setPrice(123.456f);
		line.setQuantity(123);
		line.setAmmount(123);
		
		postedSalesOrderLineDao.create(line);
		
		line.setId("test_id_2");
		line.setItem(item);
		line.setLineNumber(321);
		line.setPrice(123.789f);
		line.setQuantity(321);
		line.setAmmount(321);
		
		postedSalesOrderLineDao.create(line);
		
		List<PostedSalesOrderLine> lineList = postedSalesOrderLineDao.read();
		
		assertNotNull(lineList);
	}
	
	@Test
	public void testGet() {
		
		Item item = itemDao.get("test_item_id");
		
		PostedSalesOrderLine line = new PostedSalesOrderLine();
		
		line.setId("test_id_get");
		line.setItem(item);
		line.setLineNumber(123);
		line.setPrice(123.456f);
		line.setQuantity(123);
		line.setAmmount(123);
		
		postedSalesOrderLineDao.create(line);
		
		line = postedSalesOrderLineDao.get("test_id_get");
		
		assertNotNull(line);
		assertEquals(line.getId(), "test_id_get");
		assertEquals(line.getItem().getId(), "test_item_id");
		assertEquals(line.getLineNumber(), 123);
		assertEquals(0f, line.getPrice(), 123.456f);
		assertEquals(line.getQuantity(), 123);
		assertEquals(line.getAmmount(), 123);
	}
	
	@Test
	public void testUpdate() {
		
		Item item = itemDao.get("test_item_id");
		Item itemToUpdate = itemDao.get("test_item_id_2");
		
		PostedSalesOrderLine line = new PostedSalesOrderLine();
		
		line.setId("test_id_update");
		line.setItem(item);
		line.setLineNumber(123);
		line.setPrice(123.456f);
		line.setQuantity(123);
		line.setAmmount(123);
		
		postedSalesOrderLineDao.create(line);
		
		line.setItem(itemToUpdate);
		line.setLineNumber(321);
		line.setPrice(123.789f);
		line.setQuantity(321);
		line.setAmmount(321);
		
		line = postedSalesOrderLineDao.update(line);
		
		assertEquals(line.getId(), "test_id_update");
		assertEquals(line.getItem().getId(), "test_item_id_2");
		assertEquals(line.getLineNumber(), 321);
		assertEquals(0f, line.getPrice(), 123.789f);
		assertEquals(line.getQuantity(), 321);
		assertEquals(line.getAmmount(), 321);
	}
	
	@Test
	public void testDelete() {
		
		Item item = itemDao.get("test_item_id");
		
		PostedSalesOrderLine line = new PostedSalesOrderLine();
		
		line.setId("test_id_delete");
		line.setItem(item);
		line.setLineNumber(123);
		line.setPrice(123.456f);
		line.setQuantity(123);
		line.setAmmount(123);
		
		postedSalesOrderLineDao.create(line);
		
		postedSalesOrderLineDao.delete(line);
		
		line = postedSalesOrderLineDao.get("test_id_delete");
		
		assertNull(line);
	}
}
