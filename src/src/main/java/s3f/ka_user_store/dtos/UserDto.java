package s3f.ka_user_store.dtos;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * Created by MSBurger on 09.09.2016.
 */
public class UserDto {
    @Id
    public final String userId;
    public final String degree;
    public final String salutation;
    public final String surename;
    public final String lastname;
    public final String password; //it is a hashvalue!!
    public final String email; // login
    public final String telefon;
    public final Date startAccess;
    public final List<String> roles;
    public final boolean active;

    public UserDto(String userId,
                   String degree,
                   String salutation,
                   String surename,
                   String lastname,
                   String password,
                   String email,
                   String telefon,
                   Date startAccess,
                   List<String> roles,
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

    public List<String> getRoles() {
        return roles;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;

        UserDto userDto = (UserDto) o;

        if (active != userDto.active) return false;
        if (userId != null ? !userId.equals(userDto.userId) : userDto.userId != null) return false;
        if (!degree.equals(userDto.degree)) return false;
        if (salutation != null ? !salutation.equals(userDto.salutation) : userDto.salutation != null) return false;
        if (surename != null ? !surename.equals(userDto.surename) : userDto.surename != null) return false;
        if (lastname != null ? !lastname.equals(userDto.lastname) : userDto.lastname != null) return false;
        if (password != null ? !password.equals(userDto.password) : userDto.password != null) return false;
        if (email != null ? !email.equals(userDto.email) : userDto.email != null) return false;
        if (telefon != null ? !telefon.equals(userDto.telefon) : userDto.telefon != null) return false;
        if (startAccess != null ? !startAccess.equals(userDto.startAccess) : userDto.startAccess != null) return false;
        return roles != null ? roles.equals(userDto.roles) : userDto.roles == null;

    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + degree.hashCode();
        result = 31 * result + (salutation != null ? salutation.hashCode() : 0);
        result = 31 * result + (surename != null ? surename.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (telefon != null ? telefon.hashCode() : 0);
        result = 31 * result + (startAccess != null ? startAccess.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userId='" + userId + '\'' +
                ", degree='" + degree + '\'' +
                ", salutation='" + salutation + '\'' +
                ", surename='" + surename + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", telefon='" + telefon + '\'' +
                ", startAccess=" + startAccess +
                ", roles=" + roles +
                ", active=" + active +
                '}';
    }
}
