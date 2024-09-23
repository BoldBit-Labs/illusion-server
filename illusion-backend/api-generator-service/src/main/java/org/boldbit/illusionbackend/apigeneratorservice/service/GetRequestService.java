package org.boldbit.illusionbackend.apigeneratorservice.service;

import lombok.RequiredArgsConstructor;
import org.boldbit.illusionbackend.apigeneratorservice.model.DataModelsRegistry;
import org.boldbit.illusionbackend.apigeneratorservice.model.DataModel;
import org.boldbit.illusionbackend.apigeneratorservice.repository.DataModelsRegistryRepository;
import org.boldbit.illusionbackend.apigeneratorservice.repository.DataModelRepository;
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

    private final DataModelRepository bigDBRepository;
    private final DataModelsRegistryRepository registryRepository;

    public ResponseEntity<?> handleGetRequest(String collectionId, String documentId) {
        if (documentId == null) {
            return getAll(collectionId);
        } else {
            return getById(collectionId, documentId);
        }
    }

    private ResponseEntity<?> getAll(String collectionId) {
        List<String> documentIds = registryRepository.findById(collectionId)
                .map(DataModelsRegistry::getDataModelIds)
                .orElseThrow(() -> new RuntimeException("Invalid registry or documents not found!")); // Fixme: remove string msg

        List<DataModel> documents = bigDBRepository.findAllById(documentIds);
        List<Map<String, Object>> response = new ArrayList<>();
        documents.forEach(document -> {
            Map<String, Object> responseMap = new LinkedHashMap<>();
            responseMap.put("id", document.getId());
            responseMap.putAll(document.getJsonObject());
            response.add(responseMap);
        });

        return ResponseEntity.ok(response);
    }

    private ResponseEntity<?> getById(String collectionId, String documentId) {
        DataModel obj = registryRepository.findById(collectionId).flatMap(registry -> {
            if (registry.getDataModelIds().contains(documentId)) {
                return bigDBRepository.findById(documentId);
            } else {
                throw new RuntimeException("Document ID not found in registry!"); // fixme:
            }
        }).orElseThrow(() -> new RuntimeException("Document not found in database!")); // fixme

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("id", obj.getId());
        response.putAll(obj.getJsonObject());

        return ResponseEntity.ok(response);
    }
}
