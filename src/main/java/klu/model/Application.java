package klu.model;

import jakarta.persistence.*;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jobId;          // Links to the Job ID
    private String studentName;  // Name of the student applying
    private String status;       // "PENDING", "HIRED", "REJECTED"
    private int hoursWorked;     // To track work hours
    private String performanceReview; // To track performance

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getHoursWorked() { return hoursWorked; }
    public void setHoursWorked(int hoursWorked) { this.hoursWorked = hoursWorked; }

    public String getPerformanceReview() { return performanceReview; }
    public void setPerformanceReview(String performanceReview) { this.performanceReview = performanceReview; }
}