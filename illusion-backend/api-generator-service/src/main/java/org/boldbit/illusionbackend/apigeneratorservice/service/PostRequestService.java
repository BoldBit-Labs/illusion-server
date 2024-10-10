package org.boldbit.illusionbackend.apigeneratorservice.service;

import lombok.RequiredArgsConstructor;
import org.boldbit.illusionbackend.apigeneratorservice.controller.APIGeneratorController;
import org.boldbit.illusionbackend.apigeneratorservice.model.DataModel;
import org.boldbit.illusionbackend.apigeneratorservice.repository.DataModelsRegistryRepository;
import org.boldbit.illusionbackend.apigeneratorservice.repository.DataModelRepository;
import org.boldbit.illusionbackend.apigeneratorservice.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final DataModelRepository bigDBRepository;
    private final DataModelsRegistryRepository registryRepository;
    private static final Logger logger = LoggerFactory.getLogger(PostRequestService.class);

    protected ResponseEntity<?> handlePostRequest(String collectionId, Map<String, Object> schema, Map<String, Object> body) {
        logger.info("handlePostRequest called");
        utils.validateSchema(schema, body);
        DataModel bigDB = new DataModel();
        bigDB.setJsonObject(body);
        DataModel savedObj = bigDBRepository.save(bigDB);

        registryRepository.findById(collectionId).ifPresentOrElse(registry -> {
            registry.getDataModelIds().add(savedObj.getId());
            registryRepository.save(registry);
        }, () -> {
            ResponseEntity.status(HttpStatus.NOT_FOUND);
        });

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("id", savedObj.getId());
        response.putAll(savedObj.getJsonObject());

        return ResponseEntity.ok(response);
    }
}
