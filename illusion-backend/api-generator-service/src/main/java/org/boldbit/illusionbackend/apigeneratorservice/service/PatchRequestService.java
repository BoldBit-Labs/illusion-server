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

@Service
@Transactional
@RequiredArgsConstructor
public class PatchRequestService {

    private final Utils utils;
    private final BigDBRepository repository;
    private final APIDocumentsRegistryRepository registryRepository;

    public ResponseEntity<?> handlePatchRequest(String collectionId, String documentId,
                                                Map<String, Object> schema, Map<String, Object> patchRequestUpdates) {

        utils.validateSchema(schema, patchRequestUpdates);

        boolean registryExists = registryRepository.findById(collectionId)
                .map(registry -> registry.getDocumentIds().contains(documentId)).orElse(false);

        if (!registryExists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return repository.findById(documentId).map(document -> {
            mergeUpdates(document.getObject(), patchRequestUpdates);
            repository.save(document);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("id", documentId);
            response.putAll(document.getObject());
            return ResponseEntity.ok(response);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    private void mergeUpdates(Map<String, Object> originalDoc, Map<String, Object> patchUpdates) {
        patchUpdates.forEach((key, value) -> {
            if (value instanceof Map) {
                Object originalValue = originalDoc.get(key);
                if (originalValue instanceof Map) {
                    mergeUpdates((Map<String, Object>) originalValue, (Map<String, Object>) value);
                } else {
                    originalDoc.put(key, value);
                }
            } else {
                originalDoc.put(key, value);
            }
        });
    }
}
