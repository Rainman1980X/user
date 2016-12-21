package s3f.ka_user_store.dtos;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by MSBurger on 09.09.2016.
 */
@Document
public class UserDto {
    @Id
    private String userId;
    private String degree;
    private String salutation;
    private String givenname;
    private String surename;
    private String email; // login
    private String telefon;
    private Date startAccess;
    private List<UserRoleDto> roles;
    private boolean active;

    public UserDto(){

    }

    public UserDto(String userId,
                   String degree,
                   String salutation,
                   String givenname,
                   String surename,
                   String password,
                   String email,
                   String telefon,
                   Date startAccess,
            List<UserRoleDto> roles,
                   boolean active) {
        this.userId = userId;
        this.degree = degree;
        this.salutation = salutation;
        this.givenname = givenname;
        this.surename = surename;
        this.email = email;
        this.telefon = telefon;
        this.startAccess = startAccess;
        this.roles = roles;
        this.active = active;
    }

    public String getUserId() {
        return userId;
    }

    public String getDegree() {
        return degree;
    }

    public String getSalutation() {
        return salutation;
    }

    public String getGivenname() {
        return givenname;
    }

    public String getSurename() {
        return surename;
    }


    public String getEmail() {
        return email;
    }

    public String getTelefon() {
        return telefon;
    }

    public Date getStartAccess() {
        return startAccess;
    }

    public List<UserRoleDto> getRoles() {
        return roles;
    }

    public boolean isActive() {
        return active;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public void setGivenname(String givenname) {
        this.givenname = givenname;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setStartAccess(Date startAccess) {
        this.startAccess = startAccess;
    }

    public void setRoles(List<UserRoleDto> roles) {
        this.roles = roles;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
