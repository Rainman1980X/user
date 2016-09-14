package s3f.ka_user_store.dtos;

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
    public String customernumber;
    public String vatId; //Umsatzsteuer-Identifikationsnummer

    public CompanyDto(){}

    public CompanyDto(String companyId, String name, String street, String zipcode, String locality, String customernumber, String vatId) {
        this.companyId = companyId;
        this.name = name;
        this.street = street;
        this.zipcode = zipcode;
        this.locality = locality;
        this.customernumber = customernumber;
        this.vatId = vatId;
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

    public String getCustomernumber() {
        return customernumber;
    }

    public String getVatId() {
        return vatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyDto)) return false;

        CompanyDto that = (CompanyDto) o;

        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (zipcode != null ? !zipcode.equals(that.zipcode) : that.zipcode != null) return false;
        if (locality != null ? !locality.equals(that.locality) : that.locality != null) return false;
        if (customernumber != null ? !customernumber.equals(that.customernumber) : that.customernumber != null)
            return false;
        return vatId != null ? vatId.equals(that.vatId) : that.vatId == null;

    }

    @Override
    public int hashCode() {
        int result = companyId != null ? companyId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (zipcode != null ? zipcode.hashCode() : 0);
        result = 31 * result + (locality != null ? locality.hashCode() : 0);
        result = 31 * result + (customernumber != null ? customernumber.hashCode() : 0);
        result = 31 * result + (vatId != null ? vatId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CompanyDto{" +
                "companyId='" + companyId + '\'' +
                ", name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", locality='" + locality + '\'' +
                ", customernumber='" + customernumber + '\'' +
                ", vatId='" + vatId + '\'' +
                '}';
    }
}
