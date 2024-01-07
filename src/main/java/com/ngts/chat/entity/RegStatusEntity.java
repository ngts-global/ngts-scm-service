package com.ngts.chat.entity;
import jakarta.persistence.*;


@Entity
@Table(name = "chat_reg_status")
public class RegStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EChatRegistrationStatus registrationStatus;

    public RegStatusEntity() {
    }

    public RegStatusEntity(Integer id, EChatRegistrationStatus registrationStatus) {
        this.id = id;
        this.registrationStatus = registrationStatus;
    }

    public RegStatusEntity(EChatRegistrationStatus name) {
        this.registrationStatus = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EChatRegistrationStatus getName() {
        return registrationStatus;
    }

    public void setName(EChatRegistrationStatus name) {
        this.registrationStatus = name;
    }

}