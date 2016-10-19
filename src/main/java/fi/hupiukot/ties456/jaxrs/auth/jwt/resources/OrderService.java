package fi.hupiukot.ties456.jaxrs.auth.jwt.resources;

import java.util.Collection;
import java.util.HashMap;

import fi.hupiukot.ties456.jaxrs.auth.jwt.resources.Order;

public class OrderService {
	
	static HashMap<Integer,Order> orders = new HashMap<Integer, Order>();
	

	public OrderService() {
		// Constructor
	}

	public Collection<Order> getOrders() {
		return this.orders.values();
	}

	public Order getOrder(Integer id) {
		return orders.get(id);
	}

	public Order addOrder(Order order) {
		orders.put(order.id, order);
		return order;
	}

	public Order updateOrder(Order order) {
		orders.put(order.id, order);
		return order;
	}

	public void removeOrder(int id){
		orders.remove(id);
	}
}
