package com.cyaneer.gamesdb_api.console;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsoleRepository extends JpaRepository<Console, Long> {

    Page<Console> findAll(Pageable pageable);    
}
