package s3f.ka_user_store.dtos;

import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * Created by MSBurger on 09.09.2016.
 */
public class CompanyDto {
    @Id
    public String companyId;
    public String name;
    public String street;
    public String zipcode;
    public String locality;
    public String customerNumber;
    public String vatId; // Umsatzsteuer-Identifikationsnummer
    public List<UserRoleDto> assignedUserWithRole;

    public CompanyDto() {
    }

    public CompanyDto(String companyId, String name, String street, String zipcode, String locality,
            String customerNumber, String vatId, List<UserRoleDto> assignedUserWithRole) {
        this.companyId = companyId;
        this.name = name;
        this.street = street;
        this.zipcode = zipcode;
        this.locality = locality;
        this.customerNumber = customerNumber;
        this.vatId = vatId;
        this.assignedUserWithRole = assignedUserWithRole; // create a local copy
    }

    public String getCompanyId() {
        return companyId;
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

    public List<UserRoleDto> getAssignedUserWithRole() {
        return assignedUserWithRole; // While returning create a new List !!
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public void setAssignedUserWithRole(List<UserRoleDto> assignedUserWithRole) {
        this.assignedUserWithRole = assignedUserWithRole;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CompanyDto other = (CompanyDto) obj;
        if (assignedUserWithRole == null) {
            if (other.assignedUserWithRole != null)
                return false;
        } else if (!assignedUserWithRole.equals(other.assignedUserWithRole))
            return false;
        if (companyId == null) {
            if (other.companyId != null)
                return false;
        } else if (!companyId.equals(other.companyId))
            return false;
        if (customerNumber == null) {
            if (other.customerNumber != null)
                return false;
        } else if (!customerNumber.equals(other.customerNumber))
            return false;
        if (locality == null) {
            if (other.locality != null)
                return false;
        } else if (!locality.equals(other.locality))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (street == null) {
            if (other.street != null)
                return false;
        } else if (!street.equals(other.street))
            return false;
        if (vatId == null) {
            if (other.vatId != null)
                return false;
        } else if (!vatId.equals(other.vatId))
            return false;
        if (zipcode == null) {
            if (other.zipcode != null)
                return false;
        } else if (!zipcode.equals(other.zipcode))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((assignedUserWithRole == null) ? 0 : assignedUserWithRole.hashCode());
        result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
        result = prime * result + ((customerNumber == null) ? 0 : customerNumber.hashCode());
        result = prime * result + ((locality == null) ? 0 : locality.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        result = prime * result + ((vatId == null) ? 0 : vatId.hashCode());
        result = prime * result + ((zipcode == null) ? 0 : zipcode.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "CompanyDto [companyId=" + companyId + ", name=" + name + ", street=" + street + ", zipcode=" + zipcode
                + ", locality=" + locality + ", customerNumber=" + customerNumber + ", vatId=" + vatId
                + ", assignedUserWithRole=" + assignedUserWithRole.toString() + "]";
    }
}
