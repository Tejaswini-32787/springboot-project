package klu.controller;

import klu.model.Application;
import klu.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:5173")
public class ReportController {

    @Autowired
    private ApplicationRepository repo;

    @GetMapping("/applications/export")
    public void exportCSV(HttpServletResponse response) throws Exception {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=applications.csv");

        List<Application> list = repo.findAll();
        PrintWriter writer = response.getWriter();
        writer.println("ID,Student,JobID,Status,Hours");

        for (Application a : list) {
            writer.println(a.getId() + "," + a.getStudentName() + "," +
                    a.getJobId() + "," + a.getStatus() + "," + a.getHoursWorked());
        }
        writer.flush();
    }
}
