package software.kasunkavinda.sentura_test.repo;

import com.google.gson.Gson;
import com.squareup.okhttp.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import software.kasunkavinda.sentura_test.model.User;

import java.io.IOException;


@Configuration
@RequiredArgsConstructor
public class WeavyRepo {

    private static final String URL = "https://6788c37313c5499fa4ab110b59b4cb85.weavy.io/";
    private final OkHttpClient client;
    private final String apiKey;
    private final Gson gson;


    @Autowired
    public WeavyRepo(@Value("${weavy.api.key}") String apiKey) {
        this.client = new OkHttpClient();
        this.apiKey = apiKey;
        this.gson = new Gson();
    }
    private Request.Builder buildRequest(String endpoint) {
        return new Request.Builder()
                .url(URL + endpoint)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json");
    }

    public User createUser(String name, String email) throws Exception {
        String jsonBody = gson.toJson( new User(name, email));
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
        Request request = buildRequest("users/" + user.getId())
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
        Request request = buildRequest("users/" + userId).delete().build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (IOException e) {
            throw new Exception("Network error occurred while deleting user", e);
        } finally {
            if (response != null) {
                response.body().close();
            }
        }

    }

    public User[] listUsers() throws Exception {
        Request request = buildRequest("users").get().build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new Exception("Failed to list users");
            }
            return gson.fromJson(response.body().string(), User[].class);
        } catch (IOException e) {
            throw new Exception("Network error occurred while get users", e);
        } finally {
            if (response != null) {
                response.body().close();
            }
        }

    }
}

