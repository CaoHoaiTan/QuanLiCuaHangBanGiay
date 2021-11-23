package MODEL;

public class KhachHang {
    // Guess: (phoneNumber, fullname, sex, address, email,totalCost, discount)
    private String phoneNumber, fullname, sex, address, email;
    private double totalCost;
    private int discount;

    public KhachHang() {
    }

    public KhachHang(String phoneNumber, String fullname, String sex, String address, String email, double totalCost, int discount) {
        this.phoneNumber = phoneNumber;
        this.fullname = fullname;
        this.sex = sex;
        this.address = address;
        this.email = email;
        this.totalCost = totalCost;
        this.discount = discount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
