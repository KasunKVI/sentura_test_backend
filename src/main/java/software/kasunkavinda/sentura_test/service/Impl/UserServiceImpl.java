package software.kasunkavinda.sentura_test.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.kasunkavinda.sentura_test.model.User;
import software.kasunkavinda.sentura_test.repo.WeavyClient;
import software.kasunkavinda.sentura_test.service.UserService;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final WeavyClient waevyRepo;

    @Autowired
    public UserServiceImpl(@Value("${weavy.api.key}")String apiKey) {
        this.waevyRepo = new WeavyClient(apiKey);

    }

    @Override
    public User saveUser(User user) throws Exception {
        return waevyRepo.createUser(user);
    }

    @Override
    public User getUser(String id) throws Exception {
        return waevyRepo.getUser(id);
    }

    @Override
    public User updateUser(User user) throws Exception {
        return waevyRepo.updateUser(user);
    }

    @Override
    public boolean deleteUser(String id) throws Exception {
        return waevyRepo.deleteUser(id);
    }

    @Override
    public String getAllUsers() throws Exception {
        return waevyRepo.listUsers();
    }
}
