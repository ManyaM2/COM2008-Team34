public class Address {

    private String houseNumber;
    private String roadName;
    private String cityName;
    private String postcode;

    public String getHouseNumber() {
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

    public String toString(){
        return " " + houseNumber + " " + roadName + "\n " + cityName + "\n " + postcode;
    }

    public Address(String house, String road, String city, String post) {
        this.houseNumber = house;
        this.roadName = road;
        this.cityName = city;
        this.postcode = post;
    }
}
