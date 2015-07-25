package ru.sw.platform.modules.role;

import org.springframework.stereotype.Repository;
import ru.sw.platform.core.repositories.AbstractRepository;

import java.util.List;

@Repository(value = "RoleRepository")
public class RoleRepository extends AbstractRepository<Role> {

    public Role getRoleByName(String roleName) {
        List<Role> roleList = entityManager.createQuery("select r from Role r where r.roleName = :roleName").setParameter("roleName" , roleName).getResultList();
        if (roleList.isEmpty()) {
            return null;
        } else {
            return roleList.get(0);
        }
    }
}
