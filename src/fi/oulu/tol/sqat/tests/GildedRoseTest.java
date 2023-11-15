package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;

public class GildedRoseTest {

	@Test
	public void testTheTruth() {
		assertTrue(true);
	}
	@Test
	public void exampleTest() {
		//create an inn, add an item, and simulate one day
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
		inn.oneDay();
		
		//access a list of items, get the quality of the one set
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		//assert quality has decreased by one
		assertEquals("Failed quality for Dexterity Vest", 19, quality);
	}
	
	/* Tests:
	 * Main method
	 */
	@SuppressWarnings("static-access")
	@Test
	public void testMain() {
		GildedRose gr = new GildedRose();
		gr.main(null);
	}
	
	/* Tests:
	 * Once the sell by date has passed, Quality degrades twice as fast
	 * At the end of each day our system lowers sellIn value
	 */
	@Test
	public void testSellinDecAndSellinNegative() {
		GildedRose gr = new GildedRose();
		Item tuote = new Item("Makkara", 2, 20);
		
		/* sets new item to GildedRose list and skips one day*/
		gr.setItem(tuote);
		gr.oneDay();
		
		/* checks sellIn value */
		assertEquals("sellIn should be 1", 1, tuote.getSellIn());
		
		/* skips 2 days */
		gr.oneDay();
		gr.oneDay();
		
		/* checks sellIn and quality values */
		assertEquals("sellIn should be -1", -1, tuote.getSellIn());
		assertEquals("quality should be 16", 16, tuote.getQuality());
	}
	
	/* Tests:
	 * The Quality of an item is never negative
	 */
	@Test
	public void testQualityNegative() {
		GildedRose gr = new GildedRose();
		Item tuote = new Item("Sukset", 1, -1);
		
		/* sets new item to GildedRose list and checks quality value*/
		gr.setItem(tuote);
		assertEquals("quality is never negative", 0, tuote.getQuality());
		
		/* tries to change quality to negative value and checks quality value*/
		tuote.setQuality(0);
		gr.oneDay();
		assertEquals("quality is never negative", 0, tuote.getQuality());
	}
	
	/* Tests:
	 * "Aged Brie" actually increases in Quality the older it gets
	 */
	@Test
	public void testAgedBrieQuality() {
		GildedRose gr = new GildedRose();
		Item tuote = new Item("Aged Brie", 2, 0);
		
		/* sets new item to GildedRose list and skips one day*/
		gr.setItem(tuote);
		gr.oneDay();
		
		/* checks sellIn and quality values */
		assertEquals("sellIn should be 1", 1, tuote.getSellIn());
		assertEquals("quality should be 1", 3, tuote.getQuality());
	}
	
	/* Tests:
	 * The Quality of an item is never more than 50
	 */
	@Test
	public void QualityNeverBiggerThan50() {
		GildedRose gr = new GildedRose();
		Item tuote = new Item("Sauvat", 2, 51);
		
		/* sets new item to GildedRose list and checks quality value*/
		gr.setItem(tuote);
		assertTrue("quality is never more than 50(after constructor)", tuote.getQuality() <= 50);

		/* changing quality and checks quality value*/
		tuote.setQuality(51);
		assertTrue("quality is never more than 50 (after setQuality)", tuote.getQuality() <= 50);
		
	}
	
	/* Tests:
	 * "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
	 * And quality can not set other than 80
	 * */
	@Test
	public void testSulfuras() {
		GildedRose gr = new GildedRose();
		Item tuote = new Item("Sulfuras, Hand of Ragnaros", 2, 80);
		
		/* sets new item to GildedRose list and checks quality value*/
		gr.setItem(tuote);
		assertEquals("legendary item quality is always 80", 80, tuote.getQuality());

		/* changing quality and checks quality value*/
		tuote.setQuality(51);
		assertEquals("legendary item quality is always 80", 80, tuote.getQuality());
		
		/* skips one day and checks values */
		gr.oneDay();
		assertEquals("legendary item sellIn never decreases", 2, tuote.getSellIn());
		assertEquals("legendary item quality is always 80", 80, tuote.getQuality());
		
		/* changing sellIn value to negative and checks values */
		tuote.setSellIn(-1);
		assertEquals("legendary item sellIn never decreases", -1, tuote.getSellIn());
		assertEquals("legendary item quality is always 80", 80, tuote.getQuality());

	}
	
	/* Tests:
	 * "Backstage passes", like aged brie, increases in Quality as it's SellIn value approaches;
	 * Quality increases by 2 when there are 10 daysor less
	 * and by 3 when there are 5 days or less
	 * but Quality drops to 0 after the concert.
	 */
	@Test
	public void testBackstageAndAgedBrie() {
		GildedRose gr = new GildedRose();
		Item tuote = new Item("Backstage passes to a TAFKAL80ETC concert", 50, 10);
		Item tuote2 = new Item("Aged Brie", 50, 10);
		
		/* sets new items to GildedRose list*/
		gr.setItem(tuote);
		gr.setItem(tuote2);
		
		/* skips one day and items should behave act the same (quality increases by one) */
		gr.oneDay();
		assertEquals("quality should increace 1", 11, tuote.getQuality());
		assertEquals("quality should increace 1", 11, tuote2.getQuality());
		
		/* sets sellIn values and skips one day and items should behave act the same (quality increases by two) */
		tuote.setSellIn(10);
		tuote2.setSellIn(10);
		gr.oneDay();
		assertEquals("quality should increace 2", 13, tuote.getQuality());
		assertEquals("quality should increace 2", 13, tuote2.getQuality());
		
		/* sets sellIn values and skips one day and items should behave act the same  (quality increases by three) */
		tuote.setSellIn(5);
		tuote2.setSellIn(5);
		gr.oneDay();
		assertEquals("quality should increace 1", 16, tuote.getQuality());
		assertEquals("quality should increace 1", 16, tuote2.getQuality());
		
		/* sets sellIn values and skips one day and items should behave act the same  (quality decreases to zero) */
		tuote.setSellIn(0);
		tuote2.setSellIn(0);
		gr.oneDay();
		assertEquals("quality should increace 1", 0, tuote.getQuality());
		assertEquals("quality should increace 1", 0, tuote2.getQuality());
		
	}
	
}