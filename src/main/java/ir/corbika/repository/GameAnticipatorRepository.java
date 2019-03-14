package ir.corbika.repository;

import ir.corbika.model.GameAnticipator;
import ir.corbika.model.GameUserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameAnticipatorRepository extends JpaRepository<GameAnticipator, GameUserId> {

}

