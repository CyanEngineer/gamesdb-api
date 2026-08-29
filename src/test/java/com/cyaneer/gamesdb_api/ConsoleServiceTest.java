package com.cyaneer.gamesdb_api;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cyaneer.gamesdb_api.common.ResourceInUseException;
import com.cyaneer.gamesdb_api.console.Console;
import com.cyaneer.gamesdb_api.console.ConsoleDTO;
import com.cyaneer.gamesdb_api.console.ConsoleNotFoundException;
import com.cyaneer.gamesdb_api.console.ConsoleRepository;
import com.cyaneer.gamesdb_api.console.ConsoleService;
import com.cyaneer.gamesdb_api.game.GameRepository;

@ExtendWith(MockitoExtension.class)
public class ConsoleServiceTest {

    @Mock
    ConsoleRepository consoleRepository;
    @Mock
    GameRepository gameRepository;

    ConsoleService service;

    @BeforeEach
    void setUp() {
        service = new ConsoleService(consoleRepository, gameRepository);
    }
    
    @Test
    public void testCreateConsoleCreatesConsole() {
        
        when(consoleRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        
        ConsoleDTO dto = new ConsoleDTO("PC");
        Console savedConsole = service.createConsole(dto);
        assert(savedConsole != null);
        assert(dto.getName().equals(savedConsole.getName()));
    }

    @Test
    public void testUpdateConsoleUpdatesConsole() {
        Console console = new Console("PlayStation 5");

        when(consoleRepository.findById(anyLong())).thenReturn(Optional.of(console));
        when(consoleRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        ConsoleDTO dto = new ConsoleDTO("PS5");
        Console updatedConsole = service.updateConsole(1L, dto);
        assert(updatedConsole != null);
        assert(dto.getName().equals(updatedConsole.getName()));
    }

    @Test
    public void testNotFoundIdThrowsException() {
        when(consoleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ConsoleNotFoundException.class, () -> service.getConsole(1L));
    }

    @Test
    public void testDeleteConsoleWontDeleteInUseConsole() {
        when(gameRepository.existsByConsoleId(anyLong())).thenReturn(true);

        assertThrows(ResourceInUseException.class, () -> service.deleteConsole(1L));
    }
}
