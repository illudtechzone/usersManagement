package com.illudtechzone.usersmanagement.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Vehicle entity.
 */
public class VehicleDTO implements Serializable {

    private Long id;

    private String registerNo;

    private String currentLocationGeopoint;

    private Boolean occupied;


    private Long driversId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public String getCurrentLocationGeopoint() {
        return currentLocationGeopoint;
    }

    public void setCurrentLocationGeopoint(String currentLocationGeopoint) {
        this.currentLocationGeopoint = currentLocationGeopoint;
    }

    public Boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

    public Long getDriversId() {
        return driversId;
    }

    public void setDriversId(Long driverId) {
        this.driversId = driverId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VehicleDTO vehicleDTO = (VehicleDTO) o;
        if (vehicleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehicleDTO{" +
            "id=" + getId() +
            ", registerNo='" + getRegisterNo() + "'" +
            ", currentLocationGeopoint='" + getCurrentLocationGeopoint() + "'" +
            ", occupied='" + isOccupied() + "'" +
            ", drivers=" + getDriversId() +
            "}";
    }
}
