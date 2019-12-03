package com.illudtechzone.usersmanagement.service.dto;
import java.time.Instant;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the DriverDocument entity.
 */
public class DriverDocumentDTO implements Serializable {

    private Long id;

    private String documentType;

    @Lob
    private byte[] document;

    private String documentContentType;
    private Instant uploadTime;

    private LocalDate validataionStartDate;

    private LocalDate expiryDate;

    private Boolean isExpired;


    private Long driverId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public String getDocumentContentType() {
        return documentContentType;
    }

    public void setDocumentContentType(String documentContentType) {
        this.documentContentType = documentContentType;
    }

    public Instant getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Instant uploadTime) {
        this.uploadTime = uploadTime;
    }

    public LocalDate getValidataionStartDate() {
        return validataionStartDate;
    }

    public void setValidataionStartDate(LocalDate validataionStartDate) {
        this.validataionStartDate = validataionStartDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Boolean isIsExpired() {
        return isExpired;
    }

    public void setIsExpired(Boolean isExpired) {
        this.isExpired = isExpired;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DriverDocumentDTO driverDocumentDTO = (DriverDocumentDTO) o;
        if (driverDocumentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), driverDocumentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DriverDocumentDTO{" +
            "id=" + getId() +
            ", documentType='" + getDocumentType() + "'" +
            ", document='" + getDocument() + "'" +
            ", uploadTime='" + getUploadTime() + "'" +
            ", validataionStartDate='" + getValidataionStartDate() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", isExpired='" + isIsExpired() + "'" +
            ", driver=" + getDriverId() +
            "}";
    }
}
