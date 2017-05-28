package com.example.guest.myrestaurant.services;


import com.example.guest.myrestaurant.Constants;
import com.example.guest.myrestaurant.models.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class YelpService {

    public static void findRestaurants(String location, Callback callback) {

        // Set up our credentials
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(Constants.YELP_CONSUMER_KEY, Constants.YELP_CONSUMER_SECRET);
        consumer.setTokenWithSecret(Constants.YELP_TOKEN, Constants.YELP_TOKEN_SECRET);

        // Set up how were making the API call (e.g. we are adding an interceptor)
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .build();

        // Set up the url to make the API call (includes query, endpoint, etc)
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YELP_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.YELP_LOCATION_QUERY_PARAMETER, location);
        String url = urlBuilder.build().toString();

        // put the url above into a request
        Request request = new Request.Builder()
                .url(url)
                .build();

        // make the API call (client = tools required, request = url + query)
        Call call = client.newCall(request);

        // .enqueue instead of .execute so we will make the api call when our resources are freed up (execute would fire right away)
        call.enqueue(callback);
    }

    public ArrayList<Restaurant> processResults(Response response) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        try {
            // we convert the response to a string, to make sure the response is in JSON format
            String json = response.body().string();
            if(response.isSuccessful()) {
                JSONObject yelpJSON = new JSONObject(json);
                JSONArray businessesJSON = yelpJSON.getJSONArray("businesses");
                for(int i = 0; i <= businessesJSON.length(); i++) {
                    JSONObject restaurantJSON = businessesJSON.getJSONObject(i);
                    // pull out all the restaurant info
                    String name = restaurantJSON.getString("name");
                    String phone = restaurantJSON.optString("display_phone", "Phone not available");
                    String website = restaurantJSON.getString("url");
                    double rating = restaurantJSON.getDouble("rating");
                    String imageUrl = restaurantJSON.getString("image_url");
                    double latitude = restaurantJSON.getJSONObject("location")
                            .getJSONObject("coordinate").getDouble("latitude");
                    double longitude = restaurantJSON.getJSONObject("location")
                            .getJSONObject("coordinate").getDouble("longitude");
                    JSONArray addressJSON = restaurantJSON.getJSONObject("location")
                            .getJSONArray("display_address");
                    JSONArray categoriesJSON = restaurantJSON.getJSONArray("categories");

                    ArrayList<String> address = new ArrayList<>();

                    for(int k = 0; k < addressJSON.length(); k++) {
                        address.add(addressJSON.get(k).toString());
                    }

                    ArrayList<String> categories = new ArrayList<>();

                    for(int l = 0; l < categoriesJSON.length(); l++) {
                        categories.add(categoriesJSON.get(l).toString());
                    }

                    // use the restaurant model to create a restaurant object then add to our array of restaurants
                    Restaurant restaurant = new Restaurant(name, phone, website, rating, imageUrl, latitude, longitude, address, categories);
                    restaurants.add(restaurant);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return restaurants;
    }
}
