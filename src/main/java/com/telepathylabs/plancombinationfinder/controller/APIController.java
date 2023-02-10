package com.telepathylabs.plancombinationfinder.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.telepathylabs.plancombinationfinder.controller.Plan.getCheapestComboOfPlans;

@RestController
public class APIController {

    @Operation(summary = "Takes an arbitrary text file that contains the available plans and a comma-delimited string containing the features needed, and returns the string containing the cheapest price and combination of plans that contains the features needed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Returned the cheapest combination of plans",
                    content = {@Content(mediaType = "text/plain; charset=utf-8")}),
            @ApiResponse(responseCode = "400",
                    description = "Error parsing arguments",
                    content = {@Content(mediaType = "text/plain; charset=utf-8")})
    })
    @PostMapping(value = "/plan", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> handleFileUpload(@Parameter(description = "The features needed by the user. This will be parsed as a comma-delimited string, e.g. admin,email,voice") @RequestParam("features_needed") String featuresNeeded,
                                                   @Parameter(description = "The file containing the available plans.") @RequestParam("file") MultipartFile file) {
        try {
            String fileContents = new String(file.getBytes());
            Plan cheapestCombo = getCheapestComboOfPlans(fileContents, featuresNeeded);

            return ResponseEntity.ok().body(cheapestCombo == null ? "0" : String.format("%d%s", cheapestCombo.Price, cheapestCombo.PlanName));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error processing file");
        }
    }
}
