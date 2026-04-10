package klu.model;

import jakarta.persistence.*; // This MUST be jakarta.persistence, not javax

@Entity // <--- This is what was likely missing or broken
@Table(name = "jobs")
public class Job {

    @Id // <--- This marks the Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Double wage;
    private String status;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getWage() { return wage; }
    public void setWage(Double wage) { this.wage = wage; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}