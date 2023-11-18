package gangulwar.halfway;

public class PlaceModal {
    String nameOfPlace;
    String typeOfPlace;
    String iconUrl;
    int distance;
    double lat;
    double lon;
    String address;

    public PlaceModal(String nameOfPlace, String typeOfPlace, String iconUrl, int distance, double lat, double lon, String address) {
        this.nameOfPlace = nameOfPlace;
        this.typeOfPlace = typeOfPlace;
        this.iconUrl = iconUrl;
        this.distance = distance;
        this.lat = lat;
        this.lon = lon;
        this.address = address;
    }
}
