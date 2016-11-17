package s3f.ka_user_store.dtos;

import org.springframework.data.mongodb.core.mapping.Document;

import s3f.ka_user_store.enumns.UserRoles;

@Document
public class UserRoleDto {

    private String companyId;
    private UserRoles role;

    public UserRoleDto(String companyId, UserRoles role) {
        this.companyId = companyId;
        this.role = role;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserRoleDto other = (UserRoleDto) obj;
        if (companyId == null) {
            if (other.companyId != null)
                return false;
        } else if (!companyId.equals(other.companyId))
            return false;
        if (role != other.role)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserRoleDto [companyId=" + companyId + ", roles=" + String.join(",", role.getUserRoleList()) + "]";
    }

}
