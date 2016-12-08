package s3f.ka_user_store.dtos;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;

/**
 * Created by MSBurger on 09.09.2016.
 */
public class CompanyDto {
    @Id
    private String companyId;
    private String tenantId;
    private String name;
    private String street;
    private String zipcode;
    private String locality;
    private String customerNumber;
    private String vatId; // Umsatzsteuer-Identifikationsnummer

    public CompanyDto() {
    }

    public CompanyDto(String companyId, String name, String street, String zipcode, String locality,
            String customerNumber, String vatId) {
        this.companyId = companyId;
        this.name = name;
        this.street = street;
        this.zipcode = zipcode;
        this.locality = locality;
        this.customerNumber = customerNumber;
        this.vatId = vatId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getLocality() {
        return locality;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public String getVatId() {
        return vatId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public void setVatId(String vatId) {
        this.vatId = vatId;
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
