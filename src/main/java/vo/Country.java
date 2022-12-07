package vo;

public class Country {
	//privat정보은닉
	private int countryId;
	private String country;
	private String lastUpdate;
	public Country() //만들어도 되고 안만들어도 되는데 굳이 안만들어도 된다
	{
		super();//부모 생성자를 호출
		this.countryId = 0;
		this.country = null;
		this.lastUpdate = null;
	}
	public int getCountryId() //캡슐화  클래스를 만드는건 추상화  재활용하는건 상속  다형성
	{
		return this.countryId;
	}
	public void setCountryId(int countryId)
	{
		this.countryId = countryId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
