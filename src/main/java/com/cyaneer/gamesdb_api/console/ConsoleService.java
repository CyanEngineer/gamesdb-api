package com.cyaneer.gamesdb_api.console;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsoleService {
    
    private ConsoleRepository repository;

    public ConsoleService(ConsoleRepository repository) {
        this.repository = repository;
    }

    public List<Console> getAllConsoles() {
        return repository.findAll();
    }

    public Console getConsole(Long id) {
        return findById(id);
    }

    @Transactional
    public Console createConsole(ConsoleDTO dto) {
        Console console = new Console(dto.getName());

        return repository.save(console);
    }

    @Transactional
    public Console updateConsole(Long id, ConsoleDTO dto) {
        Console console = findById(id);
        console.update(dto.getName());

        return repository.save(console);
    }

    @Transactional
    public void deleteConsole(Long id) {
        repository.deleteById(id);
    }

    public ConsoleResponse mapToResponse(Console console) {
        return new ConsoleResponse(
            console.getId(), 
            console.getName()
        );
    }

    private Console findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ConsoleNotFoundException(id));
    }
}
