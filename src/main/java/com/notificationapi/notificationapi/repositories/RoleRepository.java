package com.notificationapi.notificationapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.notificationapi.notificationapi.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    // Aqui você pode adicionar métodos personalizados depois, se quiser.
}
