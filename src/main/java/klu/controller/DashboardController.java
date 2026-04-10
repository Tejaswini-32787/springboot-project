package klu.controller;

import klu.repository.JobRepository;
import klu.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
public class DashboardController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @GetMapping("/stats")
    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("jobs", jobRepository.count());
        stats.put("applications", applicationRepository.count());
        stats.put("hired", applicationRepository.countByStatus("HIRED"));
        stats.put("pending", applicationRepository.countByStatus("PENDING"));
        return stats;
    }
}
