package com.redhat.demos.controllers;

import com.redhat.demos.model.DecisionRequest;
import com.redhat.demos.model.DecisionResponse;
import com.redhat.demos.service.DecisionService;
import com.redhat.demos.service.RulesService;

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
@RequestMapping( { "/api/v1" } )
public class DecisionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DecisionController.class);
    
    @Autowired
    DecisionService ds;
    @Autowired
    RulesService rs;

    @GetMapping( "/assets/scan" )
	public ResponseEntity<String> scan() {
        LOGGER.info("Scan kjar...");
		rs.scanLatestKieBase();
        return ResponseEntity.ok().body("Scan initiated!");
	}

    @PostMapping( "/decisions/adult" )
	public DecisionResponse decisionPost(@RequestBody DecisionRequest request) {
        LOGGER.info("Call decision model...");
        LOGGER.debug("DecisionRequest: " + request);
		return ds.callDecision(request);
	}

    @PostMapping( "/rules/adult" )
	public DecisionResponse rulesPost(@RequestBody DecisionRequest request) {
        LOGGER.info("Fire rules...");
        LOGGER.debug("DecisionRequest: " + request);
		return rs.fireRulesOnStatefulSession(request);
	}

}