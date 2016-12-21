package s3f.ka_user_store.dtos;

/*
 * This converter maps the original DTO to the LDAP DTO
 */
public final class MappingConverter {

    private MappingConverter() {
        // Single Helperclass
    }

    public final static CompanyLDAPDto convert(final CompanyDto companyDto) {
        return companyDto;
    }

    public final static CompanyDto convert(final CompanyLDAPDto companyLDAPDto) {
        return new CompanyDto(companyLDAPDto.getTenant_uuid(),companyLDAPDto);
    }

}
