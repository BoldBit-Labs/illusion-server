package org.boldbit.illusionbackend.apigeneratorservice.service;

import lombok.RequiredArgsConstructor;
import org.boldbit.illusionbackend.apigeneratorservice.model.APIDocumentsRegistry;
import org.boldbit.illusionbackend.apigeneratorservice.model.BigDB;
import org.boldbit.illusionbackend.apigeneratorservice.repository.APIDocumentsRegistryRepository;
import org.boldbit.illusionbackend.apigeneratorservice.repository.BigDBRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class GetRequestService {

    private final BigDBRepository bigDBRepository;
    private final APIDocumentsRegistryRepository registryRepository;

    public ResponseEntity<?> handleGetRequest(String collectionId, String documentId) {
        if (documentId == null) {
            return getAll(collectionId);
        } else {
            return getById(collectionId, documentId);
        }
    }

    private ResponseEntity<?> getAll(String collectionId) {
        List<String> documentIds = registryRepository.findById(collectionId)
                .map(APIDocumentsRegistry::getDocumentIds)
                .orElseThrow(() -> new RuntimeException("Invalid registry or documents not found!")); // Fixme: remove string msg

        List<BigDB> documents = bigDBRepository.findAllById(documentIds);
        List<Map<String, Object>> response = new ArrayList<>();
        documents.forEach(document -> {
            Map<String, Object> responseMap = new LinkedHashMap<>();
            responseMap.put("id", document.getId());
            responseMap.putAll(document.getObject());
            response.add(responseMap);
        });

        return ResponseEntity.ok(response);
    }

    private ResponseEntity<?> getById(String collectionId, String documentId) {
        BigDB obj = registryRepository.findById(collectionId).flatMap(registry -> {
            if (registry.getDocumentIds().contains(documentId)) {
                return bigDBRepository.findById(documentId);
            } else {
                throw new RuntimeException("Document ID not found in registry!"); // fixme:
            }
        }).orElseThrow(() -> new RuntimeException("Document not found in database!")); // fixme

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("id", obj.getId());
        response.putAll(obj.getObject());

        return ResponseEntity.ok(response);
    }
}
