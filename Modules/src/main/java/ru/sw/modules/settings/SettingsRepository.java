package ru.sw.modules.settings;

import org.springframework.stereotype.Repository;
import ru.sw.platform.core.repositories.AbstractRepository;

@Repository("SettingsRepository")
public class SettingsRepository extends AbstractRepository<Settings> {
}
