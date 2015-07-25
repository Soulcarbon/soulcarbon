package ru.sw.modules.userInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sw.platform.core.annotations.Action;
import ru.sw.platform.core.exceptions.PlatofrmExecption;
import ru.sw.platform.core.services.AbstractService;
import ru.sw.platform.core.services.BootsrapService;
import ru.sw.platform.modules.user.User;
import ru.sw.platform.modules.user.UserRepository;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service("UserInfoService")
public class UserInfoService extends AbstractService implements BootsrapService<UserInfo> {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Action(name = "getAuthorizedUser")
    public UserInfo getRoleByName(HashMap<String,Object> data) {

        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        if(principal == null) {
            return null;
        }
        return  userInfoRepository.getUserInfoByLogin(principal.getName());
    }


    @Override
    public List<UserInfo> bootstrap(HashMap<String, Object> map) {
        if(userInfoRepository.count(UserInfo.class) == 0){

            List<User> list = userRepository.list(User.class);
            if(list.isEmpty()) {
                throw new PlatofrmExecption("users bootstrap not completed" , PlatofrmExecption.Type.ListError);
            }

            User admin = list.get(0);

            UserInfo userInfo = new UserInfo();
            userInfo.setUser(admin);
            userInfo.setInfo("main admin");
            userInfoRepository.create(userInfo);
            return Arrays.asList(userInfo);
        }
        return userInfoRepository.list(UserInfo.class);
    }
}
