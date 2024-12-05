package proyectum.security.RolRestrictions;

import proyectum.data.Rol;

public interface RoleRestrictedView {
    Rol getRequiredRole();
}
