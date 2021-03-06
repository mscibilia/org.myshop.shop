package org.myshop.shop.dao.jpa.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.myshop.shop.dao.jpa.JpaPostedPurchaseOrderLineDao;
import org.myshop.shop.model.Item;
import org.myshop.shop.model.PostedPurchaseOrderLine;

public class JpaPostedPurchaseOrderLineDaoIntegrationIT extends BaseItemAwareIntegrationTest {

	private JpaPostedPurchaseOrderLineDao postedPurchaseOrderLineDao;
	
	@Before
	public void setup() {
		super.setup();
		
		postedPurchaseOrderLineDao = new JpaPostedPurchaseOrderLineDao(factory);
	}
	
	@Test
	public void testCreate() {
		
		Item item = itemDao.get(TEST_ITEM_ID); 	
		
		PostedPurchaseOrderLine line = new PostedPurchaseOrderLine();
		
		line.setId("test_id");
		line.setItem(item);
		line.setLineNumber(123);
		line.setPrice(123.456f);
		line.setQuantity(123);
		line.setAmmount(123);
		
		postedPurchaseOrderLineDao.create(line);
	}
	
	@Test
	public void testRead() {
		
		Item item = itemDao.get(TEST_ITEM_ID);
		
		PostedPurchaseOrderLine line = new PostedPurchaseOrderLine();
		
		line.setId("test_id_1");
		line.setItem(item);
		line.setLineNumber(123);
		line.setPrice(123.456f);
		line.setQuantity(123);
		line.setAmmount(123);
		
		postedPurchaseOrderLineDao.create(line);
		
		line.setId("test_id_2");
		line.setItem(item);
		line.setLineNumber(321);
		line.setPrice(123.789f);
		line.setQuantity(321);
		line.setAmmount(321);
		
		postedPurchaseOrderLineDao.create(line);
		
		List<PostedPurchaseOrderLine> lineList = postedPurchaseOrderLineDao.read();
		
		assertNotNull(lineList);
	}
	
	@Test
	public void testGet() {
		
		Item item = itemDao.get(TEST_ITEM_ID);
		
		PostedPurchaseOrderLine line = new PostedPurchaseOrderLine();
		
		line.setId("test_id_get");
		line.setItem(item);
		line.setLineNumber(123);
		line.setPrice(123.456f);
		line.setQuantity(123);
		line.setAmmount(123);
		
		postedPurchaseOrderLineDao.create(line);
		
		line = postedPurchaseOrderLineDao.get("test_id_get");
		
		assertNotNull(line);
		assertEquals(line.getId(), "test_id_get");
		assertEquals(line.getItem().getId(), TEST_ITEM_ID);
		assertEquals(line.getLineNumber(), 123);
		assertEquals(0f, line.getPrice(), 123.456f);
		assertEquals(line.getQuantity(), 123);
		assertEquals(line.getAmmount(), 123);
	}
	
	@Test
	public void testUpdate() {
		
		Item item = itemDao.get(TEST_ITEM_ID);
		
		Item itemToUpdate = itemDao.get(TEST_ITEM_ID_2);
		
		PostedPurchaseOrderLine line = new PostedPurchaseOrderLine();
		
		line.setId("test_id_update");
		line.setItem(item);
		line.setLineNumber(123);
		line.setPrice(123.456f);
		line.setQuantity(123);
		line.setAmmount(123);
		
		postedPurchaseOrderLineDao.create(line);
		
		line.setItem(itemToUpdate);
		line.setLineNumber(321);
		line.setPrice(123.789f);
		line.setQuantity(321);
		line.setAmmount(321);
		
		line = postedPurchaseOrderLineDao.update(line);
		
		assertEquals(line.getId(), "test_id_update");
		assertEquals(line.getItem().getId(), TEST_ITEM_ID_2);
		assertEquals(line.getLineNumber(), 321);
		assertEquals(0f, line.getPrice(), 123.789f);
		assertEquals(line.getQuantity(), 321);
		assertEquals(line.getAmmount(), 321);
	}
	
	@Test
	public void testDelete() {
		
		Item item = itemDao.get(TEST_ITEM_ID);
		
		PostedPurchaseOrderLine line = new PostedPurchaseOrderLine();
		
		line.setId("test_id_delete");
		line.setItem(item);
		line.setLineNumber(123);
		line.setPrice(123.456f);
		line.setQuantity(123);
		line.setAmmount(123);
		
		postedPurchaseOrderLineDao.create(line);
		
		postedPurchaseOrderLineDao.delete(line);
		
		line = postedPurchaseOrderLineDao.get("test_id_delete");
		
		assertNull(line);
	}
	
}
