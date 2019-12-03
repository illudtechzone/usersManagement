package com.illudtechzone.usersmanagement.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Driver entity.
 */
public class DriverDTO implements Serializable {

    private Long id;

    private String driverIdpCode;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String location;

    @Lob
    private byte[] image;

    private String imageContentType;
    private Double totalRating;

    private Boolean status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDriverIdpCode() {
        return driverIdpCode;
    }

    public void setDriverIdpCode(String driverIdpCode) {
        this.driverIdpCode = driverIdpCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Double getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Double totalRating) {
        this.totalRating = totalRating;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DriverDTO driverDTO = (DriverDTO) o;
        if (driverDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), driverDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DriverDTO{" +
            "id=" + getId() +
            ", driverIdpCode='" + getDriverIdpCode() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", location='" + getLocation() + "'" +
            ", image='" + getImage() + "'" +
            ", totalRating=" + getTotalRating() +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
