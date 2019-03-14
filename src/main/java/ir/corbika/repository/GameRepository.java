package ir.corbika.repository;

import ir.corbika.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List findAllByRunAndFinished(boolean run,boolean finished);
}

