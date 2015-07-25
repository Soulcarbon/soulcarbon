package ru.sw.platform.core.services;

import java.util.HashMap;
import java.util.List;

public interface BootsrapService<T> {

    public void bootstrap(HashMap<String,Object> map);
}
