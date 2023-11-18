package gangulwar.halfway;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APIRequest {

    public static ArrayList<PlaceModal> APIRequest(String lat, String lon, String radius, String category) throws InterruptedException {
        System.out.println("NEW REQUEST...\nLAT,LAN=" + lat + "," + lon + "\nRADIUS=" + radius + "\nCATEGORY=" + category);
        //Thread.sleep(50000);
        OkHttpClient client = new OkHttpClient();
        String API_BASE_URL = "https://api.foursquare.com/v3/places/search?";
        String API_KEY = "API_KEY";
        String endpoint = API_BASE_URL + "ll=" + lat + "," + lon + "&radius=" + radius + "&categories=" + category;

        Request request = new Request.Builder()
                .url(endpoint)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", API_KEY)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            System.out.println("RESPONSE BODY:" + responseBody);
            return getObjects(responseBody);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            ArrayList<PlaceModal> arrayList = new ArrayList<>();
            arrayList.add(new PlaceModal("Error", "Please Try Again", "https://th.bing.com/th/id/OIP.0WZKH9lCcFtu_J8U9UuDEAHaHa?pid=ImgDet&rs=1", 0, 0, 0,""));
            return arrayList;
        }
    }

    private static ArrayList<PlaceModal> getObjects(String responseBody) throws JSONException {

        ArrayList<PlaceModal> arrayList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray results = jsonObject.getJSONArray("results");
        if (results.length() == 0) {
            PlaceModal placeModal = new PlaceModal("No Places Found", "As Per Your Requirements", "Error", 0, 0, 0,"");
            ArrayList<PlaceModal> modals = new ArrayList<>();
            modals.add(placeModal);
            return modals;
        }
        for (int i = 0; i < results.length(); i++) {

            JSONObject places = results.getJSONObject(i);

            String nameOfPlace = places.getString("name");

            JSONArray categories = places.getJSONArray("categories");
            JSONObject typeJson = categories.getJSONObject(0);
            String type = typeJson.getString("name");

            JSONObject icon = typeJson.getJSONObject("icon");
            String prefix = icon.getString("prefix");
            String suffix = icon.getString("suffix");
            String iconUrl = prefix + "120" + suffix;

            int distance = places.getInt("distance");

            JSONObject geocodes = places.getJSONObject("geocodes");
            JSONObject main = geocodes.getJSONObject("main");
            double lat = main.getDouble("latitude");
            double lon = main.getDouble("longitude");

            JSONObject location=places.getJSONObject("location");
            String address=location.getString("formatted_address");

            arrayList.add(new PlaceModal(nameOfPlace, type, iconUrl, distance, lat, lon,address));
        }
        return arrayList;
    }

    public static ArrayList<PlaceModal> getDemoArrayList() {
        ArrayList<PlaceModal> placesList = new ArrayList<>();
        placesList.add(new PlaceModal("Error", "Please Try Again", "https://mir-s3-cdn-cf.behance.net/project_modules/hd/ab0c1e57515093.59d8c6eb16d19.gif", 0, 0, 0,""));
        placesList.add(new PlaceModal("Checkers", "Fast Food Restaurant", "https://ss3.4sqi.net/img/categories_v2/food/fastfood_120.png", 2639, 21.158116, 79.076738,"Nagpur 941735, Mahārāshtra"));
        placesList.add(new PlaceModal("Haldiram's Hot Spot", "Restaurant", "https://ss3.4sqi.net/img/categories_v2/food/default_120.png", 1942, 21.16358, 79.080093,"Anjuman Complex, Haldiram, Nagpur 440001, Maharashtra"));
        placesList.add(new PlaceModal("Shruti Veg Restro", "Indian Restaurant", "https://ss3.4sqi.net/img/categories_v2/food/indian_120.png", 7003, 21.118747, 79.064889,"Opposite Jerryl Lawns (8 rasta sq.), Nagpur 941735, Mahārāshtra"));
        placesList.add(new PlaceModal("Starbucks", "Coffee Shop", "https://ss3.4sqi.net/img/categories_v2/food/coffeeshop_120.png", 5568, 21.138021, 79.05835,"Nagpur 138021, Mahārāshtra"));
        placesList.add(new PlaceModal("Cafe Coffee Day", "Café", "https://ss3.4sqi.net/img/categories_v2/food/cafe_120.png", 5265, 21.150441, 79.136893,"Wardhaman, Nagpur 941735, Maharashtra"));
        placesList.add(new PlaceModal("SSD Fast Food", "Restaurant", "https://ss3.4sqi.net/img/categories_v2/food/cafe_120.png", 512, 21.17868, 79.090347,"Shop No. 19, 20 .And 21, Shendre Complex, Central Avenue Road, Chapru Square, Lakadganj (C. A. Road), Nagpur 440008, Mahārāshtra"));
        placesList.add(new PlaceModal("Domino's Pizza", "Pizzeria", "https://ss3.4sqi.net/img/categories_v2/food/pizza_120.png", 4336, 21.14871, 79.124381,"Sitabuldi, 941735"));
        placesList.add(new PlaceModal("Hitchki", "Lounge", "https://ss3.4sqi.net/img/categories_v2/nightlife/default_120.png", 1467, 21.166878, 79.08328,"Sitabuldi, 941735"));
        placesList.add(new PlaceModal("Shere Punjab", "Indian Restaurant", "https://ss3.4sqi.net/img/categories_v2/food/indian_120.png", 1631, 21.168426, 79.107614,"Pratap, Nagpur 440012, Maharashtra"));
        placesList.add(new PlaceModal("Nile Tea and Nashta Shop", "Restaurant", "https://ss3.4sqi.net/img/categories_v2/food/default_120.png", 2122, 21.192387, 79.10362,"Nagpur 440018, Maharashtra"));

        return placesList;
    }
}