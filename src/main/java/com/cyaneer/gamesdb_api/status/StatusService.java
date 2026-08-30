package com.cyaneer.gamesdb_api.status;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cyaneer.gamesdb_api.common.ResourceInUseException;
import com.cyaneer.gamesdb_api.game.GameRepository;

@Service
public class StatusService {
    
    private StatusRepository statusRepository;
    private GameRepository gameRepository;

    public StatusService(StatusRepository statusRepository, GameRepository gameRepository) {
        this.statusRepository = statusRepository;
        this.gameRepository = gameRepository;
    }

    public Page<Status> getAllStatuses(Pageable pageable) {
        return statusRepository.findAll(pageable);
    }

    public Status getStatus(Long id) {
        return findById(id);
    }

    @Transactional
    public Status createStatus(StatusDTO dto) {
        Status status = new Status(dto.getName());

        return statusRepository.save(status);
    }

    @Transactional
    public Status updateStatus(Long id, StatusDTO dto) {
        Status status = findById(id);
        status.update(dto.getName());

        return statusRepository.save(status);
    }

    @Transactional
    public void deleteStatus(Long id) {
        if (gameRepository.existsByStatusId(id)) {
            throw new ResourceInUseException("Status", id);
        }
        statusRepository.deleteById(id);
    }

    public StatusResponse mapToResponse(Status status) {
        return new StatusResponse(
            status.getId(),
            status.getName()
        );
    }

    private Status findById(Long id) {
        return statusRepository.findById(id)
            .orElseThrow(() -> new StatusNotFoundException(id));
    }
}
