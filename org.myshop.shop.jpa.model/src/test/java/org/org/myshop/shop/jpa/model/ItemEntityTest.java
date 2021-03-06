package org.org.myshop.shop.jpa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;

import org.junit.Test;

import org.myshop.shop.model.Item;
import org.myshop.shop.model.ItemCategory;
import org.myshop.shop.model.ProductGroup;

public class ItemEntityTest {

	@Test
	public void testCreateItemEntity() {
		
		ItemCategory category = new ItemCategory();
		category.setId("test_item_category_id");
		
		ProductGroup group = new ProductGroup();
		group.setId("test_product_group_id");
		group.setItemCategory(category);
		
		Item item = new Item();
		
		item.setId("test_item_id");
		item.setDescription("test_item_description");
		item.setItemCategory(category);
		item.setProductGroup(group);
		item.setPurchasePrice(123.456f);
		item.setSalesPrice(123.789f);
		
		ItemEntity entity = new ItemEntity(item);
		
		assertNotNull(entity);
		
		assertEquals(entity.getId(), "test_item_id");
		assertEquals(entity.getDescription(), "test_item_description");
		assertEquals(entity.getItemCategory().getId(), "test_item_category_id");
		assertEquals(entity.getProductGroup().getId(), "test_product_group_id");
		assertEquals(0.0f, entity.getPurchasePrice(), 123.456f);
		assertEquals(0.0f, entity.getSalesPrice(), 123.789f);
		
	}
	
	@Test
	public void testEntityFieldsSameAsModelFields() throws SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		Field [] modelFields = Item.class.getFields();
		Field [] jpaEntityFields = ItemEntity.class.getFields();
		
		for(int i = 0; i < modelFields.length; i++) {
			
			modelFields[i].setAccessible(true);
			jpaEntityFields[i].setAccessible(true);
			
			try {		
				assertEquals(modelFields[i], jpaEntityFields[i]);
			
			}catch(AssertionError e) {
				System.out.print("There are unreflected fields: "+ modelFields[i].getName() + ", " + jpaEntityFields[i].getName());
			}
		}
	}
}
