package fi.hupiukot.ties456.jaxrs.auth.jwt.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order {
	public int id;
	private static int nextID = 0;
	public String customer;
	//private List<Author> coAuthors = new ArrayList<>();
	//private List<Link> links = new ArrayList<>();
	
	public Order() {
		this.id = nextID++;
	}
	
	public Order(String customer) {
		this.customer = customer;
		this.id = setId();
	}
	
	public int setId() {
		this.id = nextID++;
		return this.id;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public int getId() {
		return this.id;
	}
	
	public String getCustomer(){
		return this.customer;
	}
	
	
	/*
	public class Link {
		private String link;
		private String rel;
		// below generate getters and setters for all the variable of the class…
		public void setLink(String url) {
			this.link = url;
		}
		public void setRel(String rel2) {
			this.rel = rel2;
		}
	}
	
	public void addLink(String url, String rel){
	Link link = new Link();
	link.setLink(url);
	link.setRel(rel);
	links.add(link);
	}
	
	@GET
	@Path("/get")
	@Produces("application/json")
    public Order getPublJSON() {
		Product tuote = new Product("kissa", 22);
		Tilausrivi tilriv = new Tilausrivi(tuote, 11);
    	Order obj = new Order(tilriv, "customer");
    	return obj;
    }
	*/
}
