package com.cinema.dao;

import com.cinema.model.Role;

public interface RoleDao {
    Role add(Role role);

    Role getRoleByName(String roleName);
}
