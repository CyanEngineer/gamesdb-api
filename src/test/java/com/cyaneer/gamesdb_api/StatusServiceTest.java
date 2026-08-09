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

import com.cyaneer.gamesdb_api.status.Status;
import com.cyaneer.gamesdb_api.status.StatusDTO;
import com.cyaneer.gamesdb_api.status.StatusNotFoundException;
import com.cyaneer.gamesdb_api.status.StatusRepository;
import com.cyaneer.gamesdb_api.status.StatusService;

@ExtendWith(MockitoExtension.class)
public class StatusServiceTest {

    @Mock
    StatusRepository statusRepository;

    StatusService service;

    @BeforeEach
    void setUp() {
        service = new StatusService(statusRepository);
    }
    
    @Test
    public void testCreateStatusCreatesStatus() {
        
        when(statusRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        
        StatusDTO dto = new StatusDTO("PC");
        Status savedStatus = service.createStatus(dto);
        assert(savedStatus != null);
        assert(dto.getName().equals(savedStatus.getName()));
    }

    @Test
    public void testUpdateStatusUpdatesStatus() {
        Status status = new Status("PlayStation 5");

        when(statusRepository.findById(anyLong())).thenReturn(Optional.of(status));
        when(statusRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        StatusDTO dto = new StatusDTO("PS5");
        Status updatedStatus = service.updateStatus(1L, dto);
        assert(updatedStatus != null);
        assert(dto.getName().equals(updatedStatus.getName()));
    }

    @Test
    public void testNotFoundIdThrowsException() {
        when(statusRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(StatusNotFoundException.class, () -> service.getStatus(1L));
    }
}
