package com.example.accountmanager.model.account;

import com.example.accountmanager.datatypes.Address;
import com.example.accountmanager.datatypes.HideId;
import com.example.accountmanager.datatypes.Name;
import com.example.accountmanager.role.Role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class User implements Serializable, HideId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "USER_NAME", unique = true, nullable = false)
    @Size(max = 20)
    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "NAME_ID")
    private Name name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Address address;

    @NotBlank
    @Size(max = 50)
    private String email;

    private LocalDate dob;
    private String phoneNumber;

    @NotBlank
    @Column(length = 1000)
    private String password;

    public User() {
    }

    public User(String username, Name name, Address address, String email, LocalDate dob, String phoneNumber, String password) {
        this.username = username;
        this.name = name;
        this.address = address;
        this.email = email;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public long getId() {
        return accountId;
    }

    public void setId(long accountId) {
        this.accountId = accountId;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public int getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", title='" + getRoles().toString() + '\'' +
                ", name=" + name +
                ", address=" + address +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
