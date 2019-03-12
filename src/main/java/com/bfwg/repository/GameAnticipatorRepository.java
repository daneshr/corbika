package com.bfwg.repository;

import com.bfwg.model.GameAnticipator;
import com.bfwg.model.GameUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameAnticipatorRepository extends JpaRepository<GameAnticipator, GameUserId> {

}

