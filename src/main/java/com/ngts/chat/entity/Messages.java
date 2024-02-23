package com.ngts.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import java.time.OffsetDateTime;


@Entity
public class Messages {

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

    @Column(nullable = false, length = 100)
    private String msgId;

    @Column(nullable = false, length = 100)
    private String channelId;

    @Column
    private String conversationType;

    @Column(nullable = false, length = 100)
    private String fromId;

    @Column(nullable = false, length = 100)
    private String toId;

    @Column(nullable = false, length = 100)
    private String fromName;

    @Column(nullable = false, length = 100)
    private String toName;

    @Column(nullable = false, length = 25)
    private String msgStatus;

    @Column
    private String messageType;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column
    private OffsetDateTime deletedAt;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(final String msgId) {
        this.msgId = msgId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(final String channelId) {
        this.channelId = channelId;
    }

    public String getConversationType() {
        return conversationType;
    }

    public void setConversationType(final String conversationType) {
        this.conversationType = conversationType;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(final String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(final String toId) {
        this.toId = toId;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(final String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(final String toName) {
        this.toName = toName;
    }

    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(final String msgStatus) {
        this.msgStatus = msgStatus;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(final String messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(final OffsetDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

}
