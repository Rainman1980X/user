package s3f.ka_user_store.dtos;

public final class MappingConverter {

    private MappingConverter() {
        // Single Helperclass
    }

    public final static CompanyLDAPDto converter(final CompanyDto companyDto) {
        CompanyLDAPDto companyLDAPDto = new CompanyLDAPDto();
        companyLDAPDto.setCity(companyDto.getLocality());
        companyLDAPDto.setName1(companyDto.getName());
        companyLDAPDto.setPostal_code(companyDto.getZipcode());
        companyLDAPDto.setStreet(companyDto.getStreet());
        companyLDAPDto.setVat_id(companyDto.getVatId());
        return companyLDAPDto;
    }

    public final static CompanyDto converter(final CompanyLDAPDto companyLDAPDto) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setLocality(companyLDAPDto.getCity());
        companyDto.setName(companyLDAPDto.getName1());
        companyDto.setZipcode(companyLDAPDto.getPostal_code());
        companyDto.setStreet(companyLDAPDto.getStreet());
        companyDto.setVatId(companyLDAPDto.getVat_id());
        companyDto.setTenantId(companyLDAPDto.getTenant_id());
        return companyDto;
    }
}
