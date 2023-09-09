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

    public static ArrayList<PlaceModal> APIRequest(String lat, String lon, String radius, int category) throws InterruptedException {
        //System.out.println("NEW REQUEST...\nLAT,LAN=" + lat + "," + lon + "\nRADIUS" + radius + "\nCATEGORY=" + category);
        //Thread.sleep(50000);
        OkHttpClient client = new OkHttpClient();
        String API_BASE_URL = "https://api.foursquare.com/v3/places/search?";
        String API_KEY = "API_KEY";
        String endpoint = API_BASE_URL + "ll=" + lat + "," + lon + "&radius=" + radius + "&categories=" + (category == 0 ? "" : category);

        Request request = new Request.Builder()
                .url(endpoint)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", API_KEY)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            return getObjects(responseBody);
        } catch (IOException | JSONException e) {
            ArrayList<PlaceModal> arrayList = new ArrayList<>();
            arrayList.add(new PlaceModal("Error", "Error", "https://th.bing.com/th/id/OIP.0WZKH9lCcFtu_J8U9UuDEAHaHa?pid=ImgDet&rs=1", 2, 1, 1));
            return arrayList;
        }
    }

    private static ArrayList<PlaceModal> getObjects(String responseBody) throws JSONException {

        ArrayList<PlaceModal> arrayList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray results = jsonObject.getJSONArray("results");
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

            arrayList.add(new PlaceModal(nameOfPlace, type, iconUrl, distance, lat, lon));
        }
        return arrayList;
    }
}
