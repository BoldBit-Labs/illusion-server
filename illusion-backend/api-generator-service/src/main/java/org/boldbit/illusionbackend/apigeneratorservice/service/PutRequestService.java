package org.boldbit.illusionbackend.apigeneratorservice.service;

import lombok.RequiredArgsConstructor;
import org.boldbit.illusionbackend.apigeneratorservice.repository.APIDocumentsRegistryRepository;
import org.boldbit.illusionbackend.apigeneratorservice.repository.BigDBRepository;
import org.boldbit.illusionbackend.apigeneratorservice.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PutRequestService {

    private final Utils utils;
    private final BigDBRepository repository;
    private final APIDocumentsRegistryRepository registryRepository;

    public ResponseEntity<?> handlePutRequest(String collectionId, String documentId,
                                              Map<String, Object> schema, Map<String, Object> body) {

        utils.validateSchema(schema, body);

        Optional<Boolean> registryExists = registryRepository.findById(collectionId)
                .map(registry -> registry.getDocumentIds().contains(documentId));

        if (!registryExists.orElse(false)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return repository.findById(documentId).map(document -> {
            document.setObject(body);
            repository.save(document);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("id", documentId);
            response.putAll(body);
            return ResponseEntity.ok(response);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
