package ru.sw.modules.userInfo;

import org.springframework.stereotype.Repository;
import ru.sw.platform.core.repositories.AbstractRepository;

import java.util.List;

@Repository("UserInfoRepository")
public class UserInfoRepository extends AbstractRepository<UserInfo> {
}
