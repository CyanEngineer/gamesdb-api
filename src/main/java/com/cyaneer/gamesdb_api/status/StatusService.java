package com.cyaneer.gamesdb_api.status;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatusService {
    
    private StatusRepository repository;

    public StatusService(StatusRepository repository) {
        this.repository = repository;
    }

    public List<Status> getAllStatuses() {
        return repository.findAll();
    }

    public Status getStatus(Long id) {
        return findById(id);
    }

    @Transactional
    public Status createStatus(StatusDTO dto) {
        Status status = new Status(dto.getName());

        return repository.save(status);
    }

    @Transactional
    public Status updateStatus(Long id, StatusDTO dto) {
        Status status = findById(id);
        status.update(dto.getName());

        return repository.save(status);
    }

    @Transactional
    public void deleteStatus(Long id) {
        repository.deleteById(id);
    }

    public StatusResponse mapToResponse(Status status) {
        return new StatusResponse(
            status.getId(),
            status.getName()
        );
    }

    private Status findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new StatusNotFoundException(id));
    }
}
