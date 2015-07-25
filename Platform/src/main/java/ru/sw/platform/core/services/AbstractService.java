package ru.sw.platform.core.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractService {
    @PersistenceContext
    public EntityManager entityManager;
}
