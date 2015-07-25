package ru.sw.platform.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ru.sw.platform.core.annotations.ModuleInfo;
import ru.sw.platform.core.exceptions.PlatofrmExecption;
import ru.sw.platform.core.repositories.AbstractRepository;
import ru.sw.platform.core.services.AbstractService;


public class ModuleLocator {

    @Autowired
    private ApplicationContext ac;

    public AbstractRepository findRepository(Class classEntity) throws PlatofrmExecption {
        ModuleInfo moduleInfo = (ModuleInfo) classEntity.getAnnotation(ModuleInfo.class);
        if(moduleInfo == null) {
            throw new PlatofrmExecption("entity has not repository" , PlatofrmExecption.Type.ReadError);
        }
        AbstractRepository abstractRepository = (AbstractRepository) ac.getBean(moduleInfo.repositoryName());
        return abstractRepository;
    }

    public AbstractService findService(Class classEntity) throws PlatofrmExecption {
        ModuleInfo moduleInfo = (ModuleInfo) classEntity.getAnnotation(ModuleInfo.class);
        if(moduleInfo == null) {
            throw new PlatofrmExecption("entity has not repository" , PlatofrmExecption.Type.ReadError);
        }
        AbstractService abstractRepository = (AbstractService) ac.getBean(moduleInfo.serviceName());
        return abstractRepository;
    }
}
