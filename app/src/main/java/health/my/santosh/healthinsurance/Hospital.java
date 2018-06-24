package health.my.santosh.healthinsurance;

public class Hospital {
    String Name;
    String Address;
    String Area;
    String City;

 public Hospital()
{}

    public Hospital(String name, String address, String area, String city) {
        this.Name = name;
        this.Address = address;
        this.Area = area;
        this.City = city;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
       this. Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
       this. Address = address;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
       this. Area = area;
    }

    public String getCity()
    {

     return City;
    }

    public void setCity(String city) {
       this. City = city;
    }


}
