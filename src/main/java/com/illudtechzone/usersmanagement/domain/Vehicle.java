package com.illudtechzone.usersmanagement.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Vehicle.
 */
@Entity
@Table(name = "vehicle")
@Document(indexName = "vehicle")
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "register_no")
    private String registerNo;

    @Column(name = "current_location_geopoint")
    private String currentLocationGeopoint;

    @Column(name = "occupied")
    private Boolean occupied;

    @OneToMany(mappedBy = "vehicle")
    private Set<VehicleDocument> documents = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("vehicles")
    private Driver drivers;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public Vehicle registerNo(String registerNo) {
        this.registerNo = registerNo;
        return this;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public String getCurrentLocationGeopoint() {
        return currentLocationGeopoint;
    }

    public Vehicle currentLocationGeopoint(String currentLocationGeopoint) {
        this.currentLocationGeopoint = currentLocationGeopoint;
        return this;
    }

    public void setCurrentLocationGeopoint(String currentLocationGeopoint) {
        this.currentLocationGeopoint = currentLocationGeopoint;
    }

    public Boolean isOccupied() {
        return occupied;
    }

    public Vehicle occupied(Boolean occupied) {
        this.occupied = occupied;
        return this;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

    public Set<VehicleDocument> getDocuments() {
        return documents;
    }

    public Vehicle documents(Set<VehicleDocument> vehicleDocuments) {
        this.documents = vehicleDocuments;
        return this;
    }

    public Vehicle addDocument(VehicleDocument vehicleDocument) {
        this.documents.add(vehicleDocument);
        vehicleDocument.setVehicle(this);
        return this;
    }

    public Vehicle removeDocument(VehicleDocument vehicleDocument) {
        this.documents.remove(vehicleDocument);
        vehicleDocument.setVehicle(null);
        return this;
    }

    public void setDocuments(Set<VehicleDocument> vehicleDocuments) {
        this.documents = vehicleDocuments;
    }

    public Driver getDrivers() {
        return drivers;
    }

    public Vehicle drivers(Driver driver) {
        this.drivers = driver;
        return this;
    }

    public void setDrivers(Driver driver) {
        this.drivers = driver;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vehicle vehicle = (Vehicle) o;
        if (vehicle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vehicle{" +
            "id=" + getId() +
            ", registerNo='" + getRegisterNo() + "'" +
            ", currentLocationGeopoint='" + getCurrentLocationGeopoint() + "'" +
            ", occupied='" + isOccupied() + "'" +
            "}";
    }
}
