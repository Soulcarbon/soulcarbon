package ru.sw.platform.modules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.sw.platform.core.repositories.AbstractRepository;
import org.springframework.stereotype.Repository;
import ru.sw.platform.modules.role.Role;
import ru.sw.platform.modules.role.RoleRepository;


import java.util.Calendar;
import java.util.List;

@Repository("UserRepository")
public class UserRepository extends AbstractRepository<User> {

    @Autowired
    RoleRepository roleRepository;

    public User getUserByLogin(String login) {
        List<User> userList = entityManager.createQuery("select u from User u where u.login = :login").setParameter("login" , login).getResultList();
        if (userList.isEmpty()) {
            return null;
        } else {
            return userList.get(0);
        }
    }

    @Override
    @Transactional
    public void create(User object) {
        object.setEnabled(true);
        object.setHashPwd(object.getPwdTransient());
        super.create(object);
    }
}
