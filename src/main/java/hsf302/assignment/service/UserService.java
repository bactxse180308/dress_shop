package hsf302.assignment.service;

import hsf302.assignment.pojo.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();

    Optional<User> findByEmail(String email);

    User getUserById(Integer id);

    User createUser(User user);

    User updateUser(Integer id, User userDetails); // dùng cho user tự cập nhật

    User updateUserByAdmin(Integer id, User userDetails); // admin update user

    void deleteUser(Integer id); // không còn dùng ở controller

    void deleteUserByAdmin(Integer targetUserId, Integer currentUserId); // admin xóa user

    boolean existsByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    User updateUserProfile(User user); // người dùng tự cập nhật

    Optional<Object> findById(Integer id);
}
