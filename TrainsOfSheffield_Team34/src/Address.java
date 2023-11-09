public class Address {

    private Integer houseNumber;
    private String roadName;
    private String cityName;
    private String postcode;

    public Address(Integer house, String road, String city, String post) {
        this.houseNumber = house;
        this.roadName = road;
        this.cityName = city;
        this.postcode = post;
    }
}
