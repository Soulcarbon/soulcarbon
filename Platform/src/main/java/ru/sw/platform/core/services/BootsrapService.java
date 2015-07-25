package ru.sw.platform.core.services;

import java.util.HashMap;
import java.util.List;

public interface BootsrapService<T> {

    public List<T> bootstrap(HashMap<String,Object> map);
}
