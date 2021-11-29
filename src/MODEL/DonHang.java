package MODEL;

import java.sql.Date;

public class DonHang {
	private int id;
	private String staffName;
	private String phoneNumGuess;
	private Float cost;
	private Date purchaseDate;
	//Thêm để xử lí lấy doanh thu theo tháng
	private int purchaseMonth;
	
	
	public DonHang(float cost, int purchaseMonth) {
		this.cost = cost;
		this.purchaseMonth = purchaseMonth;
	}
	public DonHang(float cost, Date purchaseDate) {
		this.cost = cost;
		this.purchaseDate = purchaseDate;
	}
	public DonHang() {}
	public DonHang( String guessName, String phoneNumGuess, Float cost){
		this.staffName = guessName;
		this.phoneNumGuess = phoneNumGuess;
		this.cost = cost;
	}
	public DonHang(int id, String staffName, String phoneNumGuess, Float cost, Date purchaseDate) {
		this.id = id;
		this.staffName = staffName;
		this.phoneNumGuess = phoneNumGuess;
		this.cost = cost;
		this.purchaseDate = purchaseDate;
	}
	
	
	public int getPurchaseMonth() {
		return purchaseMonth;
	}
	public void setPurchaseMonth(int purchaseMonth) {
		this.purchaseMonth = purchaseMonth;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getPhoneNumGuess() {
		return phoneNumGuess;
	}
	public void setPhoneNumGuess(String phoneNumGuess) {
		this.phoneNumGuess = phoneNumGuess;
	}
	public Float getCost() {
		return cost;
	}
	public void setCost(Float cost) {
		this.cost = cost;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	
}
