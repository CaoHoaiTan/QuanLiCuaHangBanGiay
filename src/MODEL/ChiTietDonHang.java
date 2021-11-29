package MODEL;

public class ChiTietDonHang {
	private int id;
	private int idOrder;
	private int idProduct;
	private int quantity;
	private float cost;
	
	public ChiTietDonHang() {}
	public ChiTietDonHang( int idProduct, int quantity, float cost) {
		this.idProduct = idProduct;
		this.quantity = quantity;
		this.cost = cost;
	}
	public ChiTietDonHang(int id, int idOrder, int idProduct, int quantity, float cost) {
		this.id = id;
		this.idOrder = idOrder;
		this.idProduct = idProduct;
		this.quantity = quantity;
		this.cost = cost;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdOrder() {
		return idOrder;
	}
	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}
	public int getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	
	
}
