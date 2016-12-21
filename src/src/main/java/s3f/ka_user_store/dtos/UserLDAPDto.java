package s3f.ka_user_store.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class UserLDAPDto {
    private String given_name;
    private String family_name;
    private String display_name;
    private String email;
    private Date email_verified_on = new Date();
    private List<String> linked_identities = new ArrayList<>();
    private String password;
    private String account_uuid;



    public String getAccount_uuid() {
        return account_uuid;
    }

    public void setAccount_uuid(String account_uuid) {
        this.account_uuid = account_uuid;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getEmail_verified_on() {
        return email_verified_on;
    }

    public void setEmail_verified_on(Date email_verified_on) {
        this.email_verified_on = email_verified_on;
    }

    public List<String> getLinked_identities() {
        return linked_identities;
    }

    public void setLinked_identities(List<String> linked_identities) {
        this.linked_identities = linked_identities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
