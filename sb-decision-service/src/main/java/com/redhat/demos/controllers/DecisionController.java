package com.redhat.demos.controllers;

import com.redhat.demos.model.DecisionRequest;
import com.redhat.demos.model.DecisionResponse;
import com.redhat.demos.service.DecisionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( { "/api/v1/decisions" } )
public class DecisionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DecisionController.class);
    
    @Autowired
    DecisionService rs;

    @GetMapping( "/scan" )
	public ResponseEntity<String> scan() {
        LOGGER.info("Scan kjar...");
		rs.scanLatestKieBase();
        return ResponseEntity.ok().body("Scan initiated!");
	}

    @PostMapping( "/post" )
	public DecisionResponse post(@RequestBody DecisionRequest request) {
        LOGGER.info("Call decision model...");
        LOGGER.debug("DecisionRequest: " + request);
		return rs.callDecision(request);
	}
}