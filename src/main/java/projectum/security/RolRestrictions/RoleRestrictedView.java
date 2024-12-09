package projectum.security.RolRestrictions;

import projectum.data.Rol;

public interface RoleRestrictedView {
    Rol getRequiredRole();
}
