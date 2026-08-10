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

import com.cyaneer.gamesdb_api.console.Console;
import com.cyaneer.gamesdb_api.console.ConsoleRepository;
import com.cyaneer.gamesdb_api.game.Game;
import com.cyaneer.gamesdb_api.game.GameDTO;
import com.cyaneer.gamesdb_api.game.GameNotFoundException;
import com.cyaneer.gamesdb_api.game.GameRepository;
import com.cyaneer.gamesdb_api.game.GameService;
import com.cyaneer.gamesdb_api.status.Status;
import com.cyaneer.gamesdb_api.status.StatusRepository;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    GameRepository gameRepository;

    @Mock
    StatusRepository statusRepository;

    @Mock
    ConsoleRepository consoleRepository;

    GameService service;

    @BeforeEach
    void setUp() {
        service = new GameService(gameRepository, statusRepository, consoleRepository);
    }
    
    @Test
    public void testCreateGameCreatesGame() {
        when(statusRepository.findById(1L)).thenReturn(Optional.of(new Status("2")));
        when(consoleRepository.findById(3L)).thenReturn(Optional.of(new Console("4")));
        when(gameRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        GameDTO dto = new GameDTO("0", 1L, 3L, 5.0);
        Game savedGame = service.createGame(dto);
        assert(savedGame != null);
        assert("0".equals(savedGame.getTitle()));
        assert("2".equals(savedGame.getStatus().getName()));
        assert("4".equals(savedGame.getConsole().getName()));
        assert(savedGame.getScore().equals(5.0));
    }

    @Test
    public void testCreateGameRoundsScoreToOneDecimal() {
        when(statusRepository.findById(1L)).thenReturn(Optional.of(new Status("1")));
        when(consoleRepository.findById(1L)).thenReturn(Optional.of(new Console("1")));
        when(gameRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        GameDTO dto = new GameDTO("0", 1L, 1L, 5.01);
        Game savedGame = service.createGame(dto);
        assert(savedGame.getScore().equals(5.0));

        dto = new GameDTO("0", 1L, 1L, 5.05);
        savedGame = service.createGame(dto);
        assert(savedGame.getScore().equals(5.1));
    }

    @Test
    public void testUpdateGameUpdatesGame() {
        Game game = new Game("0", new Status("0"), new Console("0"), 0.0);

        when(statusRepository.findById(2L)).thenReturn(Optional.of(new Status("3")));
        when(consoleRepository.findById(4L)).thenReturn(Optional.of(new Console("5")));
        when(gameRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));

        GameDTO dto = new GameDTO("1", 2L, 4L, 6.0);
        Game savedGame = service.updateGame(1L, dto);
        assert(savedGame != null);
        assert("1".equals(savedGame.getTitle()));
        assert("3".equals(savedGame.getStatus().getName()));
        assert("5".equals(savedGame.getConsole().getName()));
        assert(6.0 == savedGame.getScore());
    }

    @Test
    public void testNotFoundIdThrowsError() {
        when(gameRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () -> service.getGame(1L));
    }
}
