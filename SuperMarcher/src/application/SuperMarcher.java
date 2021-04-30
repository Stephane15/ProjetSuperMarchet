package application;

public class SuperMarcher {
	
	private String article;
	private Double quantity;
	private Double prix;
	private String location;
	
	
	public SuperMarcher()
	{
		this(null,null);
	}
	
	public SuperMarcher(String article, String location)
	{
		this.article=article;
		this.quantity=0.0;
		this.prix=0.0;
		this.location="";
				
	}
	
	
	//getters and setters
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Double getPrix() {
		return prix;
	}
	public void setPrix(Double prix) {
		this.prix = prix;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
	

}
