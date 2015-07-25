package ru.sw.modules.weapon;

import org.springframework.stereotype.Repository;
import ru.sw.platform.core.repositories.AbstractRepository;

import java.util.List;

@Repository("WeaponRepository")
public class WeaponRepository extends AbstractRepository<Weapon> {
}
