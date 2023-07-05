package com.example.accountmanager.role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @JsonValue
    private ERole name;

    public Role(){

    }

    public Role(ERole name){
        this.name = name;
    }

    public Role(String name){
        this.name = ERole.valueOf(name);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setName(ERole name) {
        this.name = name;
    }

    public ERole getName() {
        return name;
    }
}
