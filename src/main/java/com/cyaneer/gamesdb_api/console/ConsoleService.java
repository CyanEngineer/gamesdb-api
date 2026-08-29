package com.cyaneer.gamesdb_api.console;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cyaneer.gamesdb_api.common.ResourceInUseException;
import com.cyaneer.gamesdb_api.game.GameRepository;

@Service
public class ConsoleService {
    
    private ConsoleRepository consoleRepository;
    private GameRepository gameRepository;

    public ConsoleService(ConsoleRepository consoleRepository, GameRepository gameRepository) {
        this.consoleRepository = consoleRepository;
        this.gameRepository = gameRepository;
    }

    public List<Console> getAllConsoles() {
        return consoleRepository.findAll();
    }

    public Console getConsole(Long id) {
        return findById(id);
    }

    @Transactional
    public Console createConsole(ConsoleDTO dto) {
        Console console = new Console(dto.getName());

        return consoleRepository.save(console);
    }

    @Transactional
    public Console updateConsole(Long id, ConsoleDTO dto) {
        Console console = findById(id);
        console.update(dto.getName());

        return consoleRepository.save(console);
    }

    @Transactional
    public void deleteConsole(Long id) {
        if (gameRepository.existsByConsoleId(id)) {
            throw new ResourceInUseException("Console", id);
        }
        consoleRepository.deleteById(id);
    }

    public ConsoleResponse mapToResponse(Console console) {
        return new ConsoleResponse(
            console.getId(), 
            console.getName()
        );
    }

    private Console findById(Long id) {
        return consoleRepository.findById(id)
            .orElseThrow(() -> new ConsoleNotFoundException(id));
    }
}
