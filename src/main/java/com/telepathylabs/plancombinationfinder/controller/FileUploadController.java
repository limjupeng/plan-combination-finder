package com.telepathylabs.plancombinationfinder.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.telepathylabs.plancombinationfinder.controller.Plan.getCheapestComboOfPlans;

@Controller
public class FileUploadController {

    @GetMapping("/")
    public String listUploadedFiles() {
        return "uploadForm";
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("features_needed") String featuresNeeded, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {

        String fileContents = new String(file.getBytes());
        Plan cheapestCombo = getCheapestComboOfPlans(fileContents, featuresNeeded);

        redirectAttributes.addFlashAttribute("message", cheapestCombo == null ? "0" : String.format("%d%s", cheapestCombo.Price, cheapestCombo.PlanName));

        return "redirect:/";
    }
}
