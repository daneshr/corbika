package ir.corbika.repository;

import ir.corbika.model.GameDefinition;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GameDefinitionRepository extends JpaRepository<GameDefinition, Long> {
}

