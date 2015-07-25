package ru.sw.modules.userInfo;

import org.springframework.stereotype.Repository;
import ru.sw.platform.core.repositories.AbstractRepository;

import java.util.List;

@Repository("UserInfoRepository")
public class UserInfoRepository extends AbstractRepository<UserInfo> {

    public UserInfo getUserInfoByLogin(String login) {
        List<UserInfo> list = entityManager.createQuery("select ui from UserInfo ui where ui.user.login = :login")
                .setParameter("login", login)
                .getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
}
