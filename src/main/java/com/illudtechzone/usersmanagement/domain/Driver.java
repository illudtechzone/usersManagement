package com.illudtechzone.usersmanagement.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Driver.
 */
@Entity
@Table(name = "driver")
@Document(indexName = "driver")
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "driver_idp_code")
    private String driverIdpCode;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;
    @GeoPointField
    @Column(name = "location")
    private String location;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "total_rating")
    private Double totalRating;

    @Column(name = "status")
    private Boolean status;

    @OneToMany(mappedBy = "driver")
    private Set<DriverDocument> documents = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDriverIdpCode() {
        return driverIdpCode;
    }

    public Driver driverIdpCode(String driverIdpCode) {
        this.driverIdpCode = driverIdpCode;
        return this;
    }

    public void setDriverIdpCode(String driverIdpCode) {
        this.driverIdpCode = driverIdpCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public Driver firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Driver lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Driver email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Driver phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public Driver location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte[] getImage() {
        return image;
    }

    public Driver image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Driver imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Double getTotalRating() {
        return totalRating;
    }

    public Driver totalRating(Double totalRating) {
        this.totalRating = totalRating;
        return this;
    }

    public void setTotalRating(Double totalRating) {
        this.totalRating = totalRating;
    }

    public Boolean isStatus() {
        return status;
    }

    public Driver status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<DriverDocument> getDocuments() {
        return documents;
    }

    public Driver documents(Set<DriverDocument> driverDocuments) {
        this.documents = driverDocuments;
        return this;
    }

    public Driver addDocument(DriverDocument driverDocument) {
        this.documents.add(driverDocument);
        driverDocument.setDriver(this);
        return this;
    }

    public Driver removeDocument(DriverDocument driverDocument) {
        this.documents.remove(driverDocument);
        driverDocument.setDriver(null);
        return this;
    }

    public void setDocuments(Set<DriverDocument> driverDocuments) {
        this.documents = driverDocuments;
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
        Driver driver = (Driver) o;
        if (driver.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), driver.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Driver{" +
            "id=" + getId() +
            ", driverIdpCode='" + getDriverIdpCode() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", location='" + getLocation() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", totalRating=" + getTotalRating() +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
