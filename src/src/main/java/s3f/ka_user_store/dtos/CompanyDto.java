package s3f.ka_user_store.dtos;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;

import java.util.Objects;

/**
 * Created by MSBurger on 09.09.2016.
 */
@ApiModel
public class CompanyDto extends CompanyLDAPDto {
    @Id
    @ApiModelProperty(required = false)
    private String companyId;

    public CompanyDto(String companyId, CompanyLDAPDto ldapDto) {
        this.setCompanyId(companyId);
        this.setVatId(ldapDto.getVatId());
        this.setCity(ldapDto.getCity());
        this.setCountry_code(ldapDto.getCountry_code());
        this.setDelivery_type(ldapDto.getDelivery_type());
        this.setCustomerNumber(ldapDto.getCustomerNumber());
        this.setEmail(ldapDto.getEmail());
        this.setFamily_name(ldapDto.getFamily_name());
        this.setFax(ldapDto.getFax());
        this.setIndustry(ldapDto.getIndustry());
        this.setGiven_name(ldapDto.getGiven_name());
        this.setName1(ldapDto.getName1());
        this.setName2(ldapDto.getName2());
        this.setName3(ldapDto.getName3());
        this.setTenant_uuid(ldapDto.getTenant_uuid());
        this.setMobile_phone(ldapDto.getMobile_phone());
        this.setPostal_code_postbox(ldapDto.getPostal_code_postbox());
        this.setPhone(ldapDto.getPhone());
        this.setType(ldapDto.getType());
    }

    public CompanyDto(String tenant_uuid, String name1, String street, String postal_code, String city, String customerNumber, String vatId, String given_name, String family_name, String name2, String name3, String industry, String postal_code_postbox, String postbox, String country_code, String phone, String fax, String mobile_phone, String email, DeliveryType delivery_type, CompanyType type, String companyId) {
        super(tenant_uuid, name1, street, postal_code, city, customerNumber, vatId, given_name, family_name, name2, name3, industry, postal_code_postbox, postbox, country_code, phone, fax, mobile_phone, email, delivery_type, type);
        this.companyId = companyId;
    }

    public CompanyDto() {
    }

    public CompanyDto(String tenant_uuid, String name1, String street, String postal_code, String city, String customerNumber, String vatId, String given_name, String family_name, String name2, String name3, String industry, String postal_code_postbox, String postbox, String country_code, String phone, String fax, String mobile_phone, String email, DeliveryType delivery_type, CompanyType type) {
        super(tenant_uuid, name1, street, postal_code, city, customerNumber, vatId, given_name, family_name, name2, name3, industry, postal_code_postbox, postbox, country_code, phone, fax, mobile_phone, email, delivery_type, type);
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        CompanyDto that = (CompanyDto) o;
        return Objects.equals(companyId, that.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companyId);
    }

    @Override
    public String toString() {
        return "CompanyDto{" +
                "companyId='" + companyId + '\'' +
                '}';
    }
}
