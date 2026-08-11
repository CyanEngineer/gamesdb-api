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
        when(statusRepository.findById(1L)).thenReturn(Optional.of(new Status("1")));
        when(consoleRepository.findById(2L)).thenReturn(Optional.of(new Console("2")));
        when(gameRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        GameDTO dto = new GameDTO("0", 1L, 2L, 3.0, "4");
        Game savedGame = service.createGame(dto);
        assert(savedGame != null);
        assert("0".equals(savedGame.getTitle()));
        assert("1".equals(savedGame.getStatus().getName()));
        assert("2".equals(savedGame.getConsole().getName()));
        assert(3.0 == savedGame.getScore());
        assert("4".equals(savedGame.getSortingName()));
    }

    @Test
    public void testCreateGameRoundsScoreToOneDecimal() {
        when(statusRepository.findById(1L)).thenReturn(Optional.of(new Status("1")));
        when(consoleRepository.findById(1L)).thenReturn(Optional.of(new Console("1")));
        when(gameRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        GameDTO dto = new GameDTO("0", 1L, 1L, 5.01, "0");
        Game savedGame = service.createGame(dto);
        assert(savedGame.getScore().equals(5.0));

        dto = new GameDTO("0", 1L, 1L, 5.05, "0");
        savedGame = service.createGame(dto);
        assert(savedGame.getScore().equals(5.1));
    }

    @Test
    public void testCreateGameSortingnameOmitsLeadingArticles() {
        when(statusRepository.findById(1L)).thenReturn(Optional.of(new Status("1")));
        when(consoleRepository.findById(1L)).thenReturn(Optional.of(new Console("1")));
        when(gameRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        GameDTO aPlatformer = new GameDTO("A Platformer", 1L, 1L, null, null);
        Game savedAPlatformer = service.createGame(aPlatformer);
        assert(savedAPlatformer.getSortingName().equals("platformer"));

        GameDTO anRPG = new GameDTO("An RPG", 1L, 1L, null, null);
        Game savedAnRPG = service.createGame(anRPG);
        assert(savedAnRPG.getSortingName().equals("rpg"));

        GameDTO theMetroidvania = new GameDTO("The Metroidvania", 1L, 1L, null, null);
        Game savedTheMetroidvania = service.createGame(theMetroidvania);
        assert(savedTheMetroidvania.getSortingName().equals("metroidvania"));
    }

    @Test
    public void testCreateGameSortingnameDoesntOmitNonleadingArticles() {
        when(statusRepository.findById(1L)).thenReturn(Optional.of(new Status("1")));
        when(consoleRepository.findById(1L)).thenReturn(Optional.of(new Console("1")));
        when(gameRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        GameDTO itsAPlatformer = new GameDTO("It's a Platformer", 1L, 1L, null, null);
        Game savedAPlatformer = service.createGame(itsAPlatformer);
        assert(savedAPlatformer.getSortingName().equals("it's a platformer"));

        GameDTO itsAnRPG = new GameDTO("It's an RPG", 1L, 1L, null, null);
        Game savedAnRPG = service.createGame(itsAnRPG);
        assert(savedAnRPG.getSortingName().equals("it's an rpg"));

        GameDTO itsTheMetroidvania = new GameDTO("It's the Metroidvania", 1L, 1L, null, null);
        Game savedTheMetroidvania = service.createGame(itsTheMetroidvania);
        assert(savedTheMetroidvania.getSortingName().equals("it's the metroidvania"));
    }

    @Test
    public void testCreateGameSortingnameDoesntOmitLeadingNonarticles() {
        when(statusRepository.findById(1L)).thenReturn(Optional.of(new Status("1")));
        when(consoleRepository.findById(1L)).thenReturn(Optional.of(new Console("1")));
        when(gameRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        GameDTO anotherFifa = new GameDTO("Another FIFA", 1L, 1L, null, null);
        Game savedAnotherFifa = service.createGame(anotherFifa);
        assert(savedAnotherFifa.getSortingName().equals("another fifa"));

        GameDTO theseCODs = new GameDTO("These CoDs", 1L, 1L, null, null);
        Game savedTheseCODs = service.createGame(theseCODs);
        assert(savedTheseCODs.getSortingName().equals("these cods"));
    }

    @Test
    public void testUpdateGameUpdatesGame() {
        Game game = new Game("0", new Status("0"), new Console("0"), 0.0, "0");

        when(statusRepository.findById(2L)).thenReturn(Optional.of(new Status("2")));
        when(consoleRepository.findById(3L)).thenReturn(Optional.of(new Console("3")));
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));
        when(gameRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        GameDTO dto = new GameDTO("1", 2L, 3L, 4.0, "5");
        Game savedGame = service.updateGame(1L, dto);
        assert(savedGame != null);
        assert("1".equals(savedGame.getTitle()));
        assert("2".equals(savedGame.getStatus().getName()));
        assert("3".equals(savedGame.getConsole().getName()));
        assert(4.0 == savedGame.getScore());
        assert("5".equals(savedGame.getSortingName()));
    }

    @Test
    public void testNotFoundIdThrowsError() {
        when(gameRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () -> service.getGame(1L));
    }
}
