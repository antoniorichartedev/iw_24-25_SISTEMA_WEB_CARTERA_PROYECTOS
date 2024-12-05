package com.example.proyecto.security.RolRestrictions;

import com.example.proyecto.spring.Rol;

public interface RoleRestrictedView {
    Rol getRequiredRole();
}
