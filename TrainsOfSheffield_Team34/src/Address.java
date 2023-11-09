public class Address {

    private Integer houseNumber;
    private String roadName;
    private String cityName;
    private String postcode;

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public String getRoadName() {
        return roadName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getPostCode() {
        return postcode;
    }

    public Address(Integer house, String road, String city, String post) {
        this.houseNumber = house;
        this.roadName = road;
        this.cityName = city;
        this.postcode = post;
    }
}
