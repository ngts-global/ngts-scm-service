package com.ngts.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import java.time.OffsetDateTime;


@Entity
public class Channels {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer id;

    @Column(nullable = false, length = 60)
    private String channelName;

    @Column(nullable = false, length = 100)
    private String creatorId;

    @Column(nullable = false, length = 100)
    private String channelId;

    @Column(nullable = false, length = 50)
    private String channelStatus;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false, length = 100)
    private String createdName;

    @Column(nullable = false, length = 100)
    private String createdEmail;

    @Column
    private OffsetDateTime deletedAt;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(final String channelName) {
        this.channelName = channelName;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(final String creatorId) {
        this.creatorId = creatorId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(final String channelId) {
        this.channelId = channelId;
    }

    public String getChannelStatus() {
        return channelStatus;
    }

    public void setChannelStatus(final String channelStatus) {
        this.channelStatus = channelStatus;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedName() {
        return createdName;
    }

    public void setCreatedName(final String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedEmail() {
        return createdEmail;
    }

    public void setCreatedEmail(final String createdEmail) {
        this.createdEmail = createdEmail;
    }

    public OffsetDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(final OffsetDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

}
