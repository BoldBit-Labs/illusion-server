package org.boldbit.illusionbackend.apigeneratorservice.service;

import lombok.RequiredArgsConstructor;
import org.boldbit.illusionbackend.apigeneratorservice.model.BigDB;
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
public class PostRequestService {

    private final Utils utils;
    private final BigDBRepository bigDBRepository;
    private final APIDocumentsRegistryRepository apidocumentsRegistryRepository;

    protected ResponseEntity<?> handlePostRequest(String collectionId, Map<String, Object> schema, Map<String, Object> body) {
        utils.validateSchema(schema, body);
        BigDB bigDB = new BigDB();
        bigDB.setObject(body);
        BigDB savedObj = bigDBRepository.save(bigDB);

        apidocumentsRegistryRepository.findById(collectionId).ifPresentOrElse(registry -> {
            registry.getDocumentIds().add(savedObj.getId());
            apidocumentsRegistryRepository.save(registry);
        }, () -> {
            ResponseEntity.status(HttpStatus.NOT_FOUND);
        });

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("id", savedObj.getId());
        response.putAll(savedObj.getObject());

        return ResponseEntity.ok(response);
    }
}
