package ru.sw.modules.game;


import org.springframework.stereotype.Repository;
import ru.sw.platform.core.repositories.AbstractRepository;

@Repository("GameRepository")
public class GameRepository extends AbstractRepository<Game> {
}
