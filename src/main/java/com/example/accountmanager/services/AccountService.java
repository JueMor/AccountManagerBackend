package com.example.accountmanager.services;

import com.example.accountmanager.datatypes.Name;
import com.example.accountmanager.model.account.User;
import com.example.accountmanager.model.account.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final UserRepository userRepository;

    @Autowired
    public AccountService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(User user){
        Optional<User> accountExists = userRepository.findByEmail(user.getEmail());
        if (accountExists.isPresent()){
            String password = accountExists.get().getPassword();
            if (user.getPassword().equals(password)){
                return true;
            }
        }
        throw new IllegalStateException("incorrect username or password");
    }

    public List<User> getAccounts() {
        return userRepository.findAll();
    }

    public User getAccountById(long id) {
        Optional<User> account = userRepository.findById(id);
        return account.orElse(null);
    }

    public User addNewAccount(User user) {
        Optional<User> accountByEmail = userRepository.findByEmail(user.getEmail());
        if (accountByEmail.isPresent()) {
            throw new IllegalStateException("email taken");
        }

        userRepository.save(user);

        return user;
    }

    public void deleteAccount(String email) {
        Optional<User> deleteAccount = userRepository.findByEmail(email);
        if (deleteAccount.isEmpty()) {
            throw new IllegalStateException(
                    "student with id " + email + " does not exist"
            );
        }
        userRepository.deleteById(deleteAccount.get().getId());
    }

    @Transactional
    public User updateAccount(User user) {
        User updateUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalStateException(
                        "student with email " + user.getEmail() + " does not exist"
                ));

        setRole(user, updateUser);
        setName(user, updateUser);
        setEmail(user, updateUser);
        setAddress(user, updateUser);
        setDateOfBirth(user, updateUser);
        setPhoneNumber(user, updateUser);
        setPassword(user, updateUser);

        userRepository.save(updateUser);
        return updateUser;
    }

    private void setRole(User user, User updateUser) {
        /*if (account.getRole().equals("")) {
            return;
        }

        if (!account.getRole().matches("User|Admin")) {
            throw new IllegalStateException("role must be set to: User, Admin");
        }

        updateAccount.setRole(account.getRole());*/
    }

    private void setName(User user, User updateUser) {
        if (user.getName().equals(updateUser.getName())) {
            return;
        }

        if (user.getName().getFirstName() != null &&
                user.getName().getLastName() != null &&
                user.getName().getFirstName().length() > 0 &&
                user.getName().getLastName().length() > 0) {
            Name newName = new Name(user.getName().getFirstName(), user.getName().getLastName());

            updateUser.setName(newName);
        } else {
            throw new IllegalStateException("name incorrect");
        }
    }

    private void setAddress(User user, User updateUser) {
        if (user.getAddress().equals(updateUser.getAddress())){
            return;
        }



        updateUser.setAddress(user.getAddress());
    }

    private void setEmail(User user, User updateUser) {
        if (user.getEmail().equals("") || user.getEmail().equals(updateUser.getEmail())) {
            return;
        }

        if (user.getEmail() != null &&
                user.getEmail().length() > 0) {
            Optional<User> studentByMail = userRepository.findByEmail(user.getEmail());
            if (studentByMail.isPresent()) {
                if (studentByMail.get().getId() != updateUser.getId()){
                    throw new IllegalStateException("email taken");
                }
            }
            updateUser.setEmail(user.getEmail());
        }
    }

    private void setDateOfBirth(User user, User updateUser) {
        updateUser.setDob(user.getDob());
    }

    private void setPhoneNumber(User user, User updateUser) {
        updateUser.setPhoneNumber(user.getPhoneNumber());
    }

    private void setPassword(User user, User updateUser) {
        updateUser.setPassword(user.getPassword());
    }
}
