package hsf302.assignment.service;

import hsf302.assignment.pojo.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Integer id);
    User createUser(User user);
    User updateUser(Integer id, User userDetails);
    void deleteUser(Integer id);
    boolean existsByEmail(String email);
    User findByEmailAndPassword(String email, String password);


    Optional<Object> findById(Integer id);
}
