package com.example.budget_management_system.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "locations")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(unique = true)
    private String code;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LocationType locationType;
    
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Location parent;
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Location> children;
    
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> users;

    public Location() {}

    public Location(String name, String code, LocationType locationType, Location parent) {
        this.name = name;
        this.code = code;
        this.locationType = locationType;
        this.parent = parent;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public LocationType getLocationType() { return locationType; }
    public void setLocationType(LocationType locationType) { this.locationType = locationType; }
    
    public Location getParent() { return parent; }
    public void setParent(Location parent) { this.parent = parent; }
    
    public List<Location> getChildren() { return children; }
    public void setChildren(List<Location> children) { this.children = children; }
    
    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }
}
