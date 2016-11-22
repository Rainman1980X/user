package s3f.ka_user_store.enumns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by MSBurger on 19.09.2016.
 */
public enum UserRoles {

    AUDITOR("Prüfer"), CONTRACTOR("Unternehmer"), EMPLOYEE("Angestellte(r) des Unternehmers"), SUPPORTER(
            "DATEV Support"), ADMIN("Administrator"), DEVICE("Kasse(n-System)"), SYSADMIN("System Administrator");

    private static final Map<UserRoles, String> userRoleList = new HashMap<>();
    private static final List<EntryDefiniton> userRoleEntryList = new ArrayList<>();

    static {
        for (UserRoles userRoles : values()) {
            userRoleList.put(userRoles, userRoles.getUserRole());
        }
        
        for (UserRoles userRoles : values()) {
            userRoleEntryList.add(new EntryDefiniton(userRoles.toString(), userRoles.getUserRole()));
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
        Optional<UserRoles> firstKey = userRoleList.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getValue(), userRole)).map(Map.Entry::getKey).findFirst();
        return firstKey.get();
    }

    public static List<String> getUserRoleList() {
        return new ArrayList<String>(userRoleList.values());
    }

    public static Map<UserRoles, String> getUserRoleListMap() {
        return userRoleList;
    }

    public static List<EntryDefiniton> getUserRoleEntryList() {
        return userRoleEntryList;
    }
}
