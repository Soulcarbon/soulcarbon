package ru.sw.platform.modules.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sw.platform.core.annotations.Action;
import ru.sw.platform.core.services.AbstractService;
import ru.sw.platform.core.services.BootsrapService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service(value = "RoleService")
public class RoleService extends AbstractService implements BootsrapService<Role>{

    @Autowired
    private RoleRepository roleRepository;

    @Action(name = "getRoleByName")
    public Role getRoleByName(HashMap<String,Object> data) {
        String roleName = (String) data.get("roleName");
        Role role = roleRepository.getSingleEntityByFieldAndValue(Role.class , "roleName" , roleName);
        return role;
    }

    @Override
    public void bootstrap(HashMap<String, Object> map) {
        if(roleRepository.count(Role.class) == 0) {
            Role role = new Role();
            role.setRoleName("ROLE_ADMIN");

            Role role2 = new Role();
            role2.setRoleName("ROLE_WATCHER");

            roleRepository.create(role);
            roleRepository.create(role2);
        }
    }
}
