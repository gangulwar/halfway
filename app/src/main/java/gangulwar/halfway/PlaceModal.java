package gangulwar.halfway;

public class PlaceModal {
    String nameOfPlace;
    String typeOfPlace;
    String iconUrl;
    int distance;
    double lat;
    double lon;

    public PlaceModal(String nameOfPlace, String typeOfPlace, String iconUrl, int distance, double lat, double lon) {
        this.nameOfPlace = nameOfPlace;
        this.typeOfPlace = typeOfPlace;
        this.iconUrl = iconUrl;
        this.distance = distance;
        this.lat = lat;
        this.lon = lon;
    }
}
