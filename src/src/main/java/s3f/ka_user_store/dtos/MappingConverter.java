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

    public final static UserLDAPDto convert(final UserDto userDto) {
        UserLDAPDto userLDAPDto = new UserLDAPDto();
        userLDAPDto.setEmail(userDto.getEmail());
        userLDAPDto.setGiven_name(userDto.getGivenName());
        userLDAPDto.setFamily_name(userDto.getLastname());
        userLDAPDto.setPassword(userDto.getPassword());
        userLDAPDto.setDisplay_name(userLDAPDto.getFamily_name()+", "+userLDAPDto.getGiven_name());
        return userLDAPDto;
    }
}
