package s3f.ka_user_store.dtos;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class CompanyLDAPDto {
    private String tenant_uuid;
    @ApiModelProperty(required = true)
    private String name1;
    @ApiModelProperty(required = true)
    private String street;
    @ApiModelProperty(required = true)
    private String postal_code;
    @ApiModelProperty(required = true)
    private String city;
    private String customerNumber;
    private String vatId; // Umsatzsteuer-Identifikationsnummer
    private String given_name;
    private String family_name;
    private String name2;
    private String name3;
    private String industry;
    private String postal_code_postbox;
    private String postbox;
    @ApiModelProperty(required = true)
    private String country_code;
    private String phone;
    private String fax;
    private String mobile_phone;
    @ApiModelProperty(required = true)
    private String email;
    @ApiModelProperty(required = true)
    private DeliveryType delivery_type;
    @ApiModelProperty(required = true)
    private CompanyType type;

    public CompanyLDAPDto() {
    }

    public CompanyLDAPDto(String tenant_uuid, String name1, String street, String postal_code, String city, String customerNumber, String vatId, String given_name, String family_name, String name2, String name3, String industry, String postal_code_postbox, String postbox, String country_code, String phone, String fax, String mobile_phone, String email, DeliveryType delivery_type, CompanyType type) {
        this.tenant_uuid = tenant_uuid;
        this.name1 = name1;
        this.street = street;
        this.postal_code = postal_code;
        this.city = city;
        this.customerNumber = customerNumber;
        this.vatId = vatId;
        this.given_name = given_name;
        this.family_name = family_name;
        this.name2 = name2;
        this.name3 = name3;
        this.industry = industry;
        this.postal_code_postbox = postal_code_postbox;
        this.postbox = postbox;
        this.country_code = country_code;
        this.phone = phone;
        this.fax = fax;
        this.mobile_phone = mobile_phone;
        this.email = email;
        this.delivery_type = delivery_type;
        this.type = type;
    }

    public String getTenant_uuid() {
        return tenant_uuid;
    }

    public void setTenant_uuid(String tenant_uuid) {
        this.tenant_uuid = tenant_uuid;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getVatId() {
        return vatId;
    }

    public void setVatId(String vatId) {
        this.vatId = vatId;
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

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getPostal_code_postbox() {
        return postal_code_postbox;
    }

    public void setPostal_code_postbox(String postal_code_postbox) {
        this.postal_code_postbox = postal_code_postbox;
    }

    public String getPostbox() {
        return postbox;
    }

    public void setPostbox(String postbox) {
        this.postbox = postbox;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DeliveryType getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(DeliveryType delivery_type) {
        this.delivery_type = delivery_type;
    }

    public CompanyType getType() {
        return type;
    }

    public void setType(CompanyType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompanyLDAPDto that = (CompanyLDAPDto) o;
        return Objects.equals(tenant_uuid, that.tenant_uuid) &&
                Objects.equals(name1, that.name1) &&
                Objects.equals(street, that.street) &&
                Objects.equals(postal_code, that.postal_code) &&
                Objects.equals(city, that.city) &&
                Objects.equals(customerNumber, that.customerNumber) &&
                Objects.equals(vatId, that.vatId) &&
                Objects.equals(given_name, that.given_name) &&
                Objects.equals(family_name, that.family_name) &&
                Objects.equals(name2, that.name2) &&
                Objects.equals(name3, that.name3) &&
                Objects.equals(industry, that.industry) &&
                Objects.equals(postal_code_postbox, that.postal_code_postbox) &&
                Objects.equals(postbox, that.postbox) &&
                Objects.equals(country_code, that.country_code) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(fax, that.fax) &&
                Objects.equals(mobile_phone, that.mobile_phone) &&
                Objects.equals(email, that.email) &&
                delivery_type == that.delivery_type &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenant_uuid, name1, street, postal_code, city, customerNumber, vatId, given_name, family_name, name2, name3, industry, postal_code_postbox, postbox, country_code, phone, fax, mobile_phone, email, delivery_type, type);
    }

    @Override
    public String toString() {
        return "CompanyLDAPDto{" +
                "tenant_uuid='" + tenant_uuid + '\'' +
                ", name1='" + name1 + '\'' +
                ", street='" + street + '\'' +
                ", postal_code='" + postal_code + '\'' +
                ", city='" + city + '\'' +
                ", customerNumber='" + customerNumber + '\'' +
                ", vatId='" + vatId + '\'' +
                ", given_name='" + given_name + '\'' +
                ", family_name='" + family_name + '\'' +
                ", name2='" + name2 + '\'' +
                ", name3='" + name3 + '\'' +
                ", industry='" + industry + '\'' +
                ", postal_code_postbox='" + postal_code_postbox + '\'' +
                ", postbox='" + postbox + '\'' +
                ", country_code='" + country_code + '\'' +
                ", phone='" + phone + '\'' +
                ", fax='" + fax + '\'' +
                ", mobile_phone='" + mobile_phone + '\'' +
                ", email='" + email + '\'' +
                ", delivery_type=" + delivery_type +
                ", type=" + type +
                '}';
    }
}
