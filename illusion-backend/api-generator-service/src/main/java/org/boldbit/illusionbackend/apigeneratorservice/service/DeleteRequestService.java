package org.boldbit.illusionbackend.apigeneratorservice.service;

import lombok.RequiredArgsConstructor;
import org.boldbit.illusionbackend.apigeneratorservice.repository.DataModelsRegistryRepository;
import org.boldbit.illusionbackend.apigeneratorservice.repository.DataModelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteRequestService {

    private final DataModelRepository repository;
    private final DataModelsRegistryRepository registryRepository;

    public ResponseEntity<?> handleDeleteRequest(String collectionId, String pathVariable) {
        if (!repository.existsById(pathVariable)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        repository.deleteById(pathVariable);

        registryRepository.findById(collectionId).ifPresentOrElse(registry -> {
            registry.getDataModelIds().remove(pathVariable);
            registryRepository.save(registry);
        }, () -> {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        });

        return ResponseEntity.ok().build();
    }
}
