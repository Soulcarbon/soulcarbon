package ru.sw.platform.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.sw.platform.modules.user.User;

import javax.persistence.*;
import java.util.List;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_name")
    private String className;

    public Long getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @JsonIgnore
    public abstract UserList getOwners();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity)) return false;

        AbstractEntity that = (AbstractEntity) o;

        if (!className.equals(that.className)) return false;
        if (!id.equals(that.id)) return false;

        return true;
    }
}
