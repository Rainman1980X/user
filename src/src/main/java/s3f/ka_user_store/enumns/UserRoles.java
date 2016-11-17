package s3f.ka_user_store.enumns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MSBurger on 19.09.2016.
 */
public enum UserRoles {

    AUDITOR("Prüfer"), CONTRACTOR("Unternehmer"), EMPLOYEE("Angestellte(r) des Unternehmers"), SUPPORTER(
            "DATEV Support"), ADMIN("SINTEC Administrator"), DEVICE("Kasse(n-System)");

    private static final Map<String, UserRoles> userRoleList = new HashMap<>();

    static {
        for (UserRoles userRoles : values()) {
            userRoleList.put(userRoles.getUserRole(), userRoles);
        }
    }

    private final String userRole;

    private UserRoles(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return this.userRole;
    }

    public UserRoles getUserRoleByName(String userRole) {
        return userRoleList.get(userRole);
    }

    public static List<String> getUserRoleList() {
        return new ArrayList<String>(userRoleList.keySet());
    }

    public static Map<String, UserRoles> getUserRoleListMap() {
        return userRoleList;
    }
}
