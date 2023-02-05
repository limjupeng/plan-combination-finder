package com.telepathylabs.plancombinationfinder.controller;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.telepathylabs.plancombinationfinder.controller.Plan.getCheapestComboOfPlans;

@RestController
public class APIController {

    @PostMapping(value = "/plan", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> handleFileUpload(@RequestParam("features_needed") String featuresNeeded, @RequestParam("file") MultipartFile file) {
        try {

            String fileContents = new String(file.getBytes());
            Plan cheapestCombo = getCheapestComboOfPlans(fileContents, featuresNeeded);

            return ResponseEntity.ok().body(cheapestCombo == null ? "0" : String.format("%d%s", cheapestCombo.Price, cheapestCombo.PlanName));
        }
        catch (IOException e){
            return ResponseEntity.badRequest().body("Error processing file");
        }
    }
}
