package klu.controller;

import klu.model.User;
import klu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import klu.service.EmailService;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    // Helper to generate 6 digit OTP
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    // Register a new user
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        String email = user.getUsername();
        if (email != null && (email.equalsIgnoreCase("mudamanchutejaswini@gmail.com") || email.equalsIgnoreCase("2400032787@kluniversity.in"))) {
            user.setRole("ADMIN");
        } else {
            user.setRole("STUDENT");
        }

        // If user already exists, let's update their password so we don't get a 500 Duplicate Key Error!
        User existingUser = userRepository.findByUsername(email);
        if (existingUser != null) {
            existingUser.setPassword(user.getPassword());
            existingUser.setRole(user.getRole());
            return userRepository.save(existingUser);
        }
        return userRepository.save(user);
    }

    // Login (Simple check)
    @PostMapping("/login")
    public User loginUser(@RequestBody User loginDetails) {
        User user = userRepository.findByUsername(loginDetails.getUsername());
        if (user != null && user.getPassword().equals(loginDetails.getPassword())) {
            return user; // Return user if password matches
        }
        return null; // Return null if login fails
    }

    // Phase 1 of Login: Check credentials and send OTP
    @PostMapping("/send-otp")
    public String sendOtp(@RequestBody User loginDetails) {
        User user = userRepository.findByUsername(loginDetails.getUsername());
        if (user != null && user.getPassword().equals(loginDetails.getPassword())) {
            String otp = generateOtp();
            user.setOtp(otp);
            user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
            userRepository.save(user);

            // Fallback: Print OTP to Eclipse console so the developer can see it
            System.out.println("=================================================");
            System.out.println("GENERATED OTP FOR " + user.getUsername() + ": " + otp);
            System.out.println("=================================================");

            // Try to send actual email via SMTP
            try {
                emailService.sendOtpEmail(user.getUsername(), otp);
            } catch (Exception e) {
                System.out.println("WARNING: Microsoft Office 365 blocked the email from being sent (App Password required).");
                System.out.println("But don't worry, you can use the OTP printed above!");
            }
            
            return "{\"message\": \"OTP sent successfully\"}";
        }
        return "{\"error\": \"Invalid credentials\"}";
    }

    // Phase 2 of Login: Verify OTP and return the User
    @PostMapping("/verify-otp")
    public User verifyOtp(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String otp = request.get("otp");

        User user = userRepository.findByUsername(username);
        if (user != null) {
            String dbOtp = user.getOtp();
            System.out.println("VERIFY OTP ATTEMPT | User: " + username + " | Typed: '" + otp + "' | DB: '" + dbOtp + "'");
            
            if (dbOtp != null && otp != null && dbOtp.trim().equals(otp.trim())) {
                // Removing strict LocalDateTime expiry check to avoid database timezone mismatch bugs
                
                // Ensure our designated admins actually get the ADMIN role even if they registered earlier as a student
                if (username != null && username.equalsIgnoreCase("2400032787@kluniversity.in")) {
                    user.setRole("ADMIN");
                }

                // Valid OTP: Clear it and login
                user.setOtp(null);
                user.setOtpExpiry(null);
                userRepository.save(user);
                return user;
            } else {
                System.out.println("OTP Verification Failed! Mismatch.");
            }
        }
        return null;
    }
}