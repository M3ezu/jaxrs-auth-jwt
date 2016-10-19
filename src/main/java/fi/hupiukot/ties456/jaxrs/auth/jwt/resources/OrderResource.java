package fi.hupiukot.ties456.jaxrs.auth.jwt.resources;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import fi.hupiukot.ties456.jaxrs.auth.jwt.resources.OrderService;
import javax.ws.rs.core.UriInfo;

@Path("orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {
	
	
	OrderService orderService = new OrderService();
	
	@GET
	@Path("fill")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Order> fillExamples() {
		orderService.addOrder(new Order("Jack"));
		orderService.addOrder(new Order("Mike"));
		orderService.addOrder(new Order("Thomas"));
		
		return orderService.getOrders();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Order> getOrders() {
		return orderService.getOrders(); 
	}
	
	@GET
	@Path("/{orderId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrder(@PathParam("orderId") int id, @Context UriInfo uriInfo) {
		Order order = orderService.getOrder(id);
		
		
		URI uri = uriInfo.getAbsolutePathBuilder().build();
		
		return Response.status(Status.OK)
				.header("Location", uri)
				.entity(order)
				.build(); 
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addOrder (Order order,  @Context UriInfo uriInfo) {
		order.setId();
		Order newOrder = orderService.addOrder(order);
		String newId = String.valueOf(newOrder.getId());
		
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		
		return 	Response.status(Status.CREATED)
						.header("Location", uri)
						.entity(newOrder)
						.build(); 
	}
	
	@PUT
	@Path("/{orderId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateOrder(@PathParam("orderId") int id, Order order, @Context UriInfo uriInfo) {
		//Order o = orderService.getOrder(id);
		//o.setCustomer(order.customer); // muuttaa orderin nimen
		//return orderService.updateOrder(order);
		String newId = String.valueOf(order.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.status(Status.ACCEPTED).header("Location", uri).entity(order).build();
	}
	
	@DELETE
	@Path("/{orderId}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteOrder(@PathParam("orderId") int id) {
		String result;
		Status status;
	    try{
	        orderService.removeOrder(id);
	        status = Status.OK;
	        result = "success";
	        
	    }
	    catch (Exception e){
	        e.printStackTrace();
	        status = Status.NOT_FOUND;
	        result = "fail";
	    }
	    
	    return Response.status(status).entity(result).build();
	}
		
}
