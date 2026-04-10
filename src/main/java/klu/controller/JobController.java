package klu.controller;

import klu.model.Job;
import klu.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "http://localhost:5173")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // 1️⃣ Get all jobs (Student view)
    @GetMapping
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // 2️⃣ Create job (Admin) — 🔥 REAL-TIME BROADCAST
    @PostMapping
    public Job createJob(@RequestBody Job job) {
        Job savedJob = jobRepository.save(job);

        // send new job to all connected students
        messagingTemplate.convertAndSend("/topic/jobs", savedJob);

        return savedJob;
    }

    // 3️⃣ Update job (Admin edit)
    @PutMapping("/{id}")
    public Job updateJob(@PathVariable Long id, @RequestBody Job job) {
        Job existing = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        existing.setTitle(job.getTitle());
        existing.setDescription(job.getDescription());
        existing.setWage(job.getWage());

        return jobRepository.save(existing);
    }

    // 4️⃣ Delete job (Admin delete)
    @DeleteMapping("/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobRepository.deleteById(id);
        return "Job deleted successfully";
    }
}
