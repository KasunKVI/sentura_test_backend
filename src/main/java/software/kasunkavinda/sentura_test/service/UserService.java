package software.kasunkavinda.sentura_test.service;

import software.kasunkavinda.sentura_test.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user) throws Exception;
    User getUser(String id) throws Exception;
    User updateUser( User user) throws Exception;
    boolean deleteUser(String id) throws Exception;
    List<User> getAllUsers() throws Exception;
}
