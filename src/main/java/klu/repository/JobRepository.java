package klu.repository;

import klu.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    // This empty interface gives you save(), findAll(), findById(), delete() automatically!
}