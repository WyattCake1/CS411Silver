package com.rolecall;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FlaskClient{
    OkHttpClient client = new OkHttpClient();
    public FlaskClient(){

    }
    public void requestUserListings(String userId, ResponseCallback callback){
        String endpoint = FlaskEndpoints.userListings;
        Listing[] userListings = null;
        userId = userId.trim();
            makeRequest(userId, endpoint, new ResponseCallback() {
                @Override
                public void onSuccess(String response) {
                    System.out.println("Response: " + response);  // Handle successful response
                    callback.onSuccess(response);
                }

                @Override
                public void onError(IOException e) {
                    System.err.println("Error making request: " + e.getMessage());  // Handle error
                    callback.onError(e);
                }
            });
    }
    public void makeRequest(String userId, String endpoint, ResponseCallback callback) {
        String url = "http://10.0.2.2:5000" + endpoint + "/" + userId;
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Trigger the callback's onError method
                callback.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Trigger the callback's onSuccess method with the response body
                    String responseData = response.body().string();
                    callback.onSuccess(responseData);
                } else {
                    // Trigger the callback's onError method with a generic IOException
                    callback.onError(new IOException("Request failed with code: " + response.code()));
                }
            }
        });
    }

    public void saveListing(Listing listing, ResponseCallback callback) {
        String jsonListing = listing.toJson();
        RequestBody body = RequestBody.create(jsonListing, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url("http://10.0.2.2:5000" + FlaskEndpoints.userListings)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body().string());
                } else {
                    callback.onError(new IOException("Request failed with status code: " + response.code()));
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e);
            }
        });
    }

    public void getActiveUsersId(String name, ResponseCallback callback){
        String url = "http://10.0.2.2:5000/users/" + name;
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Trigger the callback's onError method
                callback.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Trigger the callback's onSuccess method with the response body
                    String responseData = response.body().string();
                    callback.onSuccess(responseData);
                } else {
                    // Trigger the callback's onError method with a generic IOException
                    callback.onError(new IOException("Request failed with code: " + response.code()));
                }
            }
        });
    }

    public void requestMatches(String listId, String userId, ResponseCallback callback) {
        String url = "http://10.0.2.2:5000/matches?listingId=" + listId + "&userId=" + userId;
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Trigger the callback's onError method
                callback.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Trigger the callback's onSuccess method with the response body
                    String responseData = response.body().string();
                    callback.onSuccess(responseData);
                } else {
                    // Trigger the callback's onError method with a generic IOException
                    callback.onError(new IOException("Request failed with code: " + response.code()));
                }
            }
        });
    }
}

