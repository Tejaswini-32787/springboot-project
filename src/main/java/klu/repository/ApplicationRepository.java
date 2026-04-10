package klu.repository;

import klu.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // 🔹 Find all applications for a specific job (Admin use)
    List<Application> findByJobId(Long jobId);

    // 🔹 Find all applications by a specific student (Student profile)
    List<Application> findByStudentName(String studentName);

    // 🔹 Count applications by status (Dashboard analytics)
    long countByStatus(String status);

    // 🔹 Optional: Get applications by status (Admin filters)
    List<Application> findByStatus(String status);
}
