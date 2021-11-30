package MODEL;

public class ThuongHieu {
	private int id;
	private String name;
	private String email;
	private String Logo;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLogo() {
		return Logo;
	}
	public void setLogo(String logo) {
		Logo = logo;
	}
	public ThuongHieu() {		
	}
	public ThuongHieu(int id, String name, String email, String logo) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.Logo = logo;
	}
	@Override
	public String toString() {
		return name;
	}
	
	
}
