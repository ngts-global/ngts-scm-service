package com.ngts.chat.entity.enum1;
import jakarta.persistence.*;


@Entity
@Table(name = "chat_user_online_status")
public class OnlineStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EOnlineStatus eOnlineStatus;

    public OnlineStatus() {
    }

    public OnlineStatus(Integer id, EOnlineStatus onlineStatus) {
        this.id = id;
        this.eOnlineStatus = onlineStatus;
    }

    public OnlineStatus(EOnlineStatus name) {
        this.eOnlineStatus = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EOnlineStatus getName() {
        return eOnlineStatus;
    }

    public void setName(EOnlineStatus name) {
        this.eOnlineStatus = name;
    }
}
