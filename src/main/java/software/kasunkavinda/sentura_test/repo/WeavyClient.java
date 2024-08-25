package software.kasunkavinda.sentura_test.repo;

import com.google.gson.Gson;
import com.squareup.okhttp.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.kasunkavinda.sentura_test.model.User;

import java.io.IOException;
import java.util.Arrays;


@Service
@RequiredArgsConstructor
public class WeavyClient {

    private static final String URL = "https://6788c37313c5499fa4ab110b59b4cb85.weavy.io/api/";
    private final OkHttpClient client;
    private final String apiKey;
    private final Gson gson;


    @Autowired
    public WeavyClient(@Value("${weavy.api.key}") String apiKey) {
        this.client = new OkHttpClient();
        this.apiKey = apiKey;
        this.gson = new Gson();
    }
    private Request.Builder buildRequest(String endpoint) {
        return new Request.Builder()
                .url(URL + endpoint)
                .addHeader("Authorization", "Bearer " + apiKey);
    }

    public User createUser(User user) throws Exception {

        String jsonBody = gson.toJson(user);
        Request request = buildRequest("users/")
                .post(RequestBody.create(MediaType.parse("application/json"), jsonBody))
                .build();

        Response response = null;
        try {

          response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new Exception("Failed to create user");
            }
            return gson.fromJson(response.body().string(), User.class);
        } catch (IOException e) {
            throw new Exception("Network error occurred while creating user", e);
        } finally {
            if (response != null) {
                response.body().close();
            }
        }
    }

    public User getUser(String userId) throws Exception {
        Request request = buildRequest("users/" + userId).get().build();


        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new Exception("Failed to get user details");
            }
            return gson.fromJson(response.body().string(), User.class);
        } catch (IOException e) {
            throw new Exception("Network error occurred while get a user", e);
        } finally {
            if (response != null) {
                response.body().close();
            }
        }
    }

    public User updateUser(User user) throws Exception {
        String jsonBody = gson.toJson(user);
        Request request = buildRequest("users/" + user.getUid())
                .patch(RequestBody.create(MediaType.parse("application/json"), jsonBody))
                .build();


        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new Exception("Failed to update user");
            }
            return gson.fromJson(response.body().string(), User.class);
        } catch (IOException e) {
            throw new Exception("Network error occurred while update user", e);
        } finally {
            if (response != null) {
                response.body().close();
            }
        }

    }

    public boolean deleteUser(String userId) throws Exception {
        Request request = buildRequest("users/" + userId +"/trash")
                .post(RequestBody.create(MediaType.parse("application/json"), ""))
                        .build();

        System.out.println(request);

        Response response = null;
        try {
            response = client.newCall(request).execute();
            System.out.println(response);
            return response.isSuccessful();
        } catch (IOException e) {
            throw new Exception("Network error occurred while deleting user", e);
        } finally {
            if (response != null) {
                response.body().close();
            }
        }

    }

    public String listUsers() throws Exception {
        Request request = buildRequest("users").get().build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new Exception("Failed to list users");
            }
            return response.body().string();
        } catch (IOException e) {
            throw new Exception("Network error occurred while get users", e);
        } finally {
            if (response != null) {
                response.body().close();
            }
        }

    }
}

