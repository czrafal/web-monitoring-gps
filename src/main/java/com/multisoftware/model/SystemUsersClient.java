package com.multisoftware.model;
import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


@Entity
@Table(name="systemUsersclient", schema = "public")
@NamedQuery(name="SystemUsersClient.findAll", query="SELECT s FROM SystemUsersClient s")
public class SystemUsersClient implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="IDSystemClient")
    private Long IDSystemClient;

    @Column(name="Active")
    private String active;

    @Column(name="Email")
    private String email;

    @Column(name="IDSystem")
    private Long IDSystem;

    @Column(name="LastLogin")
    private Timestamp lastLogin;

    @Column(name="Login")
    private String login;

    @Column(name="Password")
    private String password;

    @Temporal(TemporalType.DATE)
    @Column(name="RegisterDate")
    private Date registerDate;

    @Column(name="Role")
    private String role;

    //bi-directional many-to-one association to SystemUser
    @ManyToOne
    @JoinColumn(name="IDSystem", referencedColumnName="IDSystem", insertable=false, updatable=false)
    private SystemUser systemUser;

    public SystemUsersClient() {
    }

    public Long getIDSystemClient() {
        return this.IDSystemClient;
    }

    public void setIDSystemClient(Long IDSystemClient) {
        this.IDSystemClient = IDSystemClient;
    }

    public String getActive() {
        return this.active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getIDSystem() {
        return this.IDSystem;
    }

    public void setIDSystem(Long IDSystem) {
        this.IDSystem = IDSystem;
    }

    public Timestamp getLastLogin() {
        return this.lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegisterDate() {
        return this.registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public SystemUser getSystemUser() {
        return this.systemUser;
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }

}