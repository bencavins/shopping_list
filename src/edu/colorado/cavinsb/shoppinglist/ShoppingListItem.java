package edu.colorado.cavinsb.shoppinglist;

public class ShoppingListItem {
	
	private String name = "";
	private Integer quantity = 0;
	private Double price = 0.0;
	private Boolean crossed = false;
	
	/**
	 * Basic constructor for a shopping list item. Quantity and price are initialized to zero.
	 * 
	 * @param name The name of the item.
	 */
	public ShoppingListItem(String name) {
		this(name, 0, 0.0);
	}
	
	/**
	 * Constructor for a shopping list item where quantity and price are set.
	 * 
	 * @param name The name of the item.
	 * @param quantity The quantity of the item.
	 * @param price The price of the item.
	 */
	public ShoppingListItem(String name, Integer quantity, Double price) {
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public boolean isCrossed() {
		return this.crossed;
	}
	
	public void toggleCrossed() {
		this.crossed = !this.crossed;
	}

	@Override
	public String toString() {
		return "ShoppingListItem [name=" + name + ", quantity=" + quantity
				+ ", price=" + price + "]";
	}
	
}
