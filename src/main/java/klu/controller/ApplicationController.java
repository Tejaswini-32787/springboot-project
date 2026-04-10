package klu.controller;

import klu.model.Application;
import klu.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "http://localhost:5173")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    // 1️⃣ Student Applies for a Job
    @PostMapping
    public Application applyForJob(@RequestBody Application app) {
        app.setStatus("PENDING");
        app.setHoursWorked(0);
        return applicationRepository.save(app);
    }

    // 2️⃣ Get All Applications (Admin)
    @GetMapping
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    // 3️⃣ Get Applications by Student (Profile Page)
    @GetMapping("/student/{name}")
    public List<Application> getByStudent(@PathVariable String name) {
        return applicationRepository.findByStudentName(name);
    }

    // 4️⃣ Get Applications by Status (Admin Filter)
    @GetMapping("/status/{status}")
    public List<Application> getByStatus(@PathVariable String status) {
        return applicationRepository.findByStatus(status);
    }

    // 5️⃣ Admin Updates Status (Hire/Reject)
    @PutMapping("/{id}/status")
    public Application updateStatus(@PathVariable Long id, @RequestBody String status) {
        Optional<Application> optionalApp = applicationRepository.findById(id);
        if (optionalApp.isPresent()) {
            Application app = optionalApp.get();
            app.setStatus(status.replace("\"", "")); // remove quotes if sent as JSON string
            return applicationRepository.save(app);
        }
        return null;
    }

    // 6️⃣ Student Logs Hours
    @PutMapping("/{id}/hours")
    public Application logHours(@PathVariable Long id, @RequestBody int hours) {
        Optional<Application> optionalApp = applicationRepository.findById(id);
        if (optionalApp.isPresent()) {
            Application app = optionalApp.get();
            app.setHoursWorked(app.getHoursWorked() + hours);
            return applicationRepository.save(app);
        }
        return null;
    }

    // 7️⃣ Admin Adds Performance Review
    @PutMapping("/{id}/review")
    public Application addReview(@PathVariable Long id, @RequestBody String review) {
        Optional<Application> optionalApp = applicationRepository.findById(id);
        if (optionalApp.isPresent()) {
            Application app = optionalApp.get();
            app.setPerformanceReview(review.replace("\"", ""));
            return applicationRepository.save(app);
        }
        return null;
    }
}
