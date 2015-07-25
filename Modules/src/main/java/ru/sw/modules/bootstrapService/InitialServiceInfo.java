package ru.sw.modules.bootstrapService;

import ru.sw.platform.core.annotations.ModuleInfo;
import ru.sw.platform.core.entity.AbstractEntity;
import ru.sw.platform.core.entity.UserList;

import javax.persistence.*;

@Entity
@ModuleInfo(repositoryName = "InitialServiceInfoRepository" , serviceName = "InitialServiceInfoService")
@Table(name = "bootstrap_service")
public class InitialServiceInfo extends AbstractEntity{

    public enum BootstrapState {
        Complete,
        NotComplete,
        Failed
    }

    public InitialServiceInfo(String serviceName) {
        this.serviceName = serviceName;
    }

    public InitialServiceInfo() {

    }

    private String serviceName;

    private String errorMessage;

    @Enumerated(EnumType.STRING)
    private BootstrapState state = BootstrapState.NotComplete;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public BootstrapState getState() {
        return state;
    }

    public void setState(BootstrapState state) {
        this.state = state;
    }

    @Override
    public UserList getOwners() {
        return null;
    }
}
