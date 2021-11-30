package MODEL;

import java.sql.Date;

public class SanPham {
	private int id, idBrand, quantity;
	private String name, image, describe;
	private double cost;
	private Date saleDate;

	public SanPham(int id, int idBrand, String name, String image, String describe, int quantity, double cost,
			Date saleDate) {
		this.id = id;
		this.idBrand = idBrand;
		this.quantity = quantity;
		this.name = name;
		this.image = image;
		this.describe = describe;
		this.cost = cost;
		this.saleDate = saleDate;
	}
	
	public SanPham() {}
	
	public SanPham(int idBrand, int quantity, String name, String image, String describe, Float cost,
			Date saleDate) {
		this.idBrand = idBrand;
		this.quantity = quantity;
		this.name = name;
		this.image = image;
		this.describe = describe;
		this.cost = cost;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getIdBrand() {
		return idBrand;
	}


	public void setIdBrand(int idBrand) {
		this.idBrand = idBrand;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String getDescribe() {
		return describe;
	}


	public void setDescribe(String describe) {
		this.describe = describe;
	}



	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public Date getSaleDate() {
		return saleDate;
	}


	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
	
	
}
