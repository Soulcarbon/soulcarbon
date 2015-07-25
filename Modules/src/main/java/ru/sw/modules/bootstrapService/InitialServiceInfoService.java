package ru.sw.modules.bootstrapService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sw.modules.userInfo.UserInfoService;
import ru.sw.platform.core.annotations.Action;
import ru.sw.platform.core.services.AbstractService;
import ru.sw.platform.core.services.BootsrapService;
import ru.sw.platform.modules.role.RoleService;
import ru.sw.platform.modules.user.UserService;

import java.util.*;

@Service("InitialServiceInfoService")
public class InitialServiceInfoService extends AbstractService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private InitialServiceInfoRepository initialServiceInfoRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    Logger logger = LoggerFactory.getLogger(InitialServiceInfoService.class);

    private Map<BootsrapService<?> , InitialServiceInfo> services = new LinkedHashMap<>();


    private void initServices(){
        services.put(roleService , new InitialServiceInfo("roleService"));
        services.put(userService , new InitialServiceInfo("userService"));
        services.put(userInfoService , new InitialServiceInfo("userInfoService"));

        for(Map.Entry<BootsrapService<?> , InitialServiceInfo> entry : services.entrySet()) {
            List<InitialServiceInfo> info = initialServiceInfoRepository.
                    getEntitiesByFieldAndValue(InitialServiceInfo.class, "serviceName", entry.getValue().getServiceName());
            if( info.isEmpty()) {
                initialServiceInfoRepository.create(entry.getValue());
            } else {
                entry.setValue(info.get(0));
            }
        }
    }

    @Action(name = "bootstrap")
    public List<InitialServiceInfo> doBootstrap(HashMap<String, Object> map) {
        initServices();
        List<InitialServiceInfo> list = new ArrayList<>();
        for(Map.Entry<BootsrapService<?> , InitialServiceInfo> entry : services.entrySet()) {
            InitialServiceInfo info = entry.getValue();
            list.add(info);
            try {
                entry.getKey().bootstrap(map);
            } catch(Exception e) {
                logger.warn(e.getMessage() , e);
                info.setState(InitialServiceInfo.BootstrapState.Failed);
                info.setErrorMessage(e.getMessage());
                initialServiceInfoRepository.update(info);
                continue;
            }
            info.setState(InitialServiceInfo.BootstrapState.Complete);
            initialServiceInfoRepository.update(info);
        }
        return list;
    }
}
