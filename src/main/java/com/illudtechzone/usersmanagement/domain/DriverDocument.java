package com.illudtechzone.usersmanagement.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DriverDocument.
 */
@Entity
@Table(name = "driver_document")
@Document(indexName = "driverdocument")
public class DriverDocument implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document_type")
    private String documentType;

    @Lob
    @Column(name = "document")
    private byte[] document;

    @Column(name = "document_content_type")
    private String documentContentType;

    @Column(name = "upload_time")
    private Instant uploadTime;

    @Column(name = "validataion_start_date")
    private LocalDate validataionStartDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "is_expired")
    private Boolean isExpired;

    @ManyToOne
    @JsonIgnoreProperties("documents")
    private Driver driver;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentType() {
        return documentType;
    }

    public DriverDocument documentType(String documentType) {
        this.documentType = documentType;
        return this;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public byte[] getDocument() {
        return document;
    }

    public DriverDocument document(byte[] document) {
        this.document = document;
        return this;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public String getDocumentContentType() {
        return documentContentType;
    }

    public DriverDocument documentContentType(String documentContentType) {
        this.documentContentType = documentContentType;
        return this;
    }

    public void setDocumentContentType(String documentContentType) {
        this.documentContentType = documentContentType;
    }

    public Instant getUploadTime() {
        return uploadTime;
    }

    public DriverDocument uploadTime(Instant uploadTime) {
        this.uploadTime = uploadTime;
        return this;
    }

    public void setUploadTime(Instant uploadTime) {
        this.uploadTime = uploadTime;
    }

    public LocalDate getValidataionStartDate() {
        return validataionStartDate;
    }

    public DriverDocument validataionStartDate(LocalDate validataionStartDate) {
        this.validataionStartDate = validataionStartDate;
        return this;
    }

    public void setValidataionStartDate(LocalDate validataionStartDate) {
        this.validataionStartDate = validataionStartDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public DriverDocument expiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Boolean isIsExpired() {
        return isExpired;
    }

    public DriverDocument isExpired(Boolean isExpired) {
        this.isExpired = isExpired;
        return this;
    }

    public void setIsExpired(Boolean isExpired) {
        this.isExpired = isExpired;
    }

    public Driver getDriver() {
        return driver;
    }

    public DriverDocument driver(Driver driver) {
        this.driver = driver;
        return this;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
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
        DriverDocument driverDocument = (DriverDocument) o;
        if (driverDocument.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), driverDocument.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DriverDocument{" +
            "id=" + getId() +
            ", documentType='" + getDocumentType() + "'" +
            ", document='" + getDocument() + "'" +
            ", documentContentType='" + getDocumentContentType() + "'" +
            ", uploadTime='" + getUploadTime() + "'" +
            ", validataionStartDate='" + getValidataionStartDate() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", isExpired='" + isIsExpired() + "'" +
            "}";
    }
}
