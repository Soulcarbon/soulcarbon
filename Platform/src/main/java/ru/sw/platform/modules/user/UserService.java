package ru.sw.platform.modules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sw.platform.core.annotations.Action;
import ru.sw.platform.core.services.AbstractService;
import ru.sw.platform.core.services.BootsrapService;
import ru.sw.platform.modules.role.Role;
import ru.sw.platform.modules.role.RoleRepository;

import javax.persistence.PersistenceContext;
import java.util.*;

@Service("UserService")
public class UserService extends AbstractService implements BootsrapService<User>{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<User> bootstrap(HashMap<String, Object> map) {
        if(userRepository.count(User.class) == 0) {

            String login = "admin";
            String password = "admin";
            String fullName = "administrator";

            if (map.containsKey("login")) {
                login = (String) map.get("login");
            }

            if (map.containsKey("password")) {
               password = (String) map.get("password");
            }
            if(map.containsKey("fullName")) {
                fullName = (String) map.get("fullName");
            }

            Role role = roleRepository.getRoleByName("ROLE_ADMIN");
            if (role == null) {
                return null;
            }

            User user = new User();
            user.setLogin(login);
            user.setFullName(fullName);
            user.setHashPwd(password);
            user.setRole(role);
            user.setEnabled(true);
            user.setPwdTransient(password);
            userRepository.create(user);
            return Arrays.asList(user);

        } else {
            return userRepository.list(User.class);
        }
    }
}
