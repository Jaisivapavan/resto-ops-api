package com.sivapavan.restoops.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.sivapavan.restoops.entity.Role;
import com.sivapavan.restoops.entity.RoleName;
import com.sivapavan.restoops.repository.RoleRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    
    // ✅ Add constructor manually
    public DataSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    
    @Override
    public void run(String... args) {
        for (RoleName roleName : RoleName.values()) {
            roleRepository.findByName(roleName).orElseGet(() -> {
                Role role = new Role();
                role.setName(roleName);
                return roleRepository.save(role);
            });
        }
    }
}