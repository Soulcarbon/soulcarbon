package ru.sw.modules.bootstrapService;

import org.springframework.stereotype.Repository;
import ru.sw.platform.core.repositories.AbstractRepository;

import java.util.List;

@Repository("InitialServiceInfoRepository")
public class InitialServiceInfoRepository extends AbstractRepository<InitialServiceInfo> {

    public InitialServiceInfo getInitialServiceByName(String name) {
        List<InitialServiceInfo> list = entityManager.createQuery("select init from InitialServiceInfo init where init.serviceName = :name")
                .setParameter("name", name).getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
}

