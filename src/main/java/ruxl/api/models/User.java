package ruxl.api.models;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "USERS")
public class User {
    @Id
    @SequenceGenerator(name = "user_seq",sequenceName = "user_sequence",initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "user_sec")
    @Column(nullable = false)
    private Long id;
    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Email must be correct")
    @Column(nullable = false, unique = true)
    private String email;
    @NotEmpty(message = "Password must not be empty")
    private String password;
    @NotEmpty(message = "Full name must not be empty")
    @Column(nullable = false,unique = true)
    private String full_name;

    @ColumnDefault(value = "false")
    private boolean agree_with_agreement;

    @AttributeOverrides(
            value = {
                    @AttributeOverride(name = "code", column = @Column(name = "phone_code",nullable = true)),
                    @AttributeOverride(name = "country", column = @Column(name = "phone_country",nullable = true)),
                    @AttributeOverride(name = "number", column = @Column(name = "phone_number",nullable = true)),
            }

    )
    private PhoneNumber phoneNumber;

    public User() {
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public boolean isAgree_with_agreement() {
        return agree_with_agreement;
    }

    public void setAgree_with_agreement(boolean agree_with_agreement) {
        this.agree_with_agreement = agree_with_agreement;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", full_name='" + full_name + '\'' +
                ", agree_with_agreement=" + agree_with_agreement +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
