package ruxl.api.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Token {
    @Id
    @SequenceGenerator(name = "token_seq",sequenceName = "token_sequence",initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "token_seq")
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String token;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    private Date createdAd;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(insertable = false, updatable = false)
    @Generated(
            org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private Date updatedAd;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Token() {
    }

    public Token(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Date getCreatedAd() {
        return createdAd;
    }

    public Date getUpdatedAd() {
        return updatedAd;
    }

    public User getUser() {
        return user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
