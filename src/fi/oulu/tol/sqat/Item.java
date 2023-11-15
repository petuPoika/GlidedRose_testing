package fi.oulu.tol.sqat;


public class Item {
    public String name;
	public int sellIn; 
    public int quality; 
    
    public Item(String name, int sellIn, int quality) {
		this.setName(name);
		this.setSellIn(sellIn);
		this.setQuality(quality);
	}
    
	/* Generated getter and setter code */
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSellIn() {
		return sellIn;
	}
	public void setSellIn(int sellIn) {
		this.sellIn = sellIn;
	}
	public int getQuality() {
		return quality;
	}
	public void setQuality(int quality) {
		// added if because "The Quality of an item is never more than 50"
		if (quality >= 0 && quality <= 50 && !"Sulfuras, Hand of Ragnaros".equals(name)) {
			this.quality = quality;
		}
		if (quality == 80 && "Sulfuras, Hand of Ragnaros".equals(name)) {
			this.quality = quality;
		}
	}
}
