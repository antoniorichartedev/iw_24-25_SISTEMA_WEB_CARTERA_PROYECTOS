package projectum.security.RolRestrictions;

import projectum.data.Rol;

public interface RoleRestrictedView {

    Rol getRequiredRole();

    default boolean hasAccess(Rol currentRole) {
        return currentRole != null && currentRole.equals(getRequiredRole());
    }
}

