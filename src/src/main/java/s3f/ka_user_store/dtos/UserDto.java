package s3f.ka_user_store.dtos;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by MSBurger on 09.09.2016.
 */
@Document
public class UserDto {
    @Id
    public String userId;
    public String degree;
    public String salutation;
    public String surename;
    public String lastname;
    public String password; //it is a hashvalue!!
    public String email; // login
    public String telefon;
    public Date startAccess;
    public List<UserRoleDto> roles;
    public boolean active;

    public UserDto(){

    }

    public UserDto(String userId,
                   String degree,
                   String salutation,
                   String surename,
                   String lastname,
                   String password,
                   String email,
                   String telefon,
                   Date startAccess,
            List<UserRoleDto> roles,
                   boolean active) {
        this.userId = userId;
        this.degree = degree;
        this.salutation = salutation;
        this.surename = surename;
        this.lastname = lastname;
        this.password = password;
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

    public String getSurename() {
        return surename;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
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

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPassword(String password) {
        this.password = password;
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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserDto other = (UserDto) obj;
        if (active != other.active)
            return false;
        if (degree == null) {
            if (other.degree != null)
                return false;
        } else if (!degree.equals(other.degree))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (lastname == null) {
            if (other.lastname != null)
                return false;
        } else if (!lastname.equals(other.lastname))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (roles == null) {
            if (other.roles != null)
                return false;
        } else if (!roles.equals(other.roles))
            return false;
        if (salutation == null) {
            if (other.salutation != null)
                return false;
        } else if (!salutation.equals(other.salutation))
            return false;
        if (startAccess == null) {
            if (other.startAccess != null)
                return false;
        } else if (!startAccess.equals(other.startAccess))
            return false;
        if (surename == null) {
            if (other.surename != null)
                return false;
        } else if (!surename.equals(other.surename))
            return false;
        if (telefon == null) {
            if (other.telefon != null)
                return false;
        } else if (!telefon.equals(other.telefon))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (active ? 1231 : 1237);
        result = prime * result + ((degree == null) ? 0 : degree.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((roles == null) ? 0 : roles.hashCode());
        result = prime * result + ((salutation == null) ? 0 : salutation.hashCode());
        result = prime * result + ((startAccess == null) ? 0 : startAccess.hashCode());
        result = prime * result + ((surename == null) ? 0 : surename.hashCode());
        result = prime * result + ((telefon == null) ? 0 : telefon.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "UserDto [userId=" + userId + ", degree=" + degree + ", salutation=" + salutation + ", surename="
                + surename + ", lastname=" + lastname + ", password=" + password + ", email=" + email + ", telefon="
                + telefon + ", startAccess=" + startAccess + ", active=" + active + "]";
    }
}
