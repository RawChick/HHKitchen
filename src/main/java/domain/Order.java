package domain;

import java.util.*;

public class Order {
	private int tableNr;
	private int orderNr;
	private int status;
	private Date placed;
	
	public Order(int tableNr, int orderNr, int status) {
		this.tableNr = tableNr;
		this.orderNr = orderNr;
		this.status = status;
	}
	
	public int getTableNr() {
		return tableNr;
	}
	
	public int getOrderNr() {
		return orderNr;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
}
