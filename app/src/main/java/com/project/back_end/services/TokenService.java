package com.project.back_end.services;

import com.project.back_end.repo.AdminRepository;
import com.project.back_end.repo.DoctorRepository;
import com.project.back_end.repo.PatientRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class TokenService {

    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public TokenService(AdminRepository adminRepository, DoctorRepository doctorRepository,
            PatientRepository patientRepository) {
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    /**
     * ✅ Generate JWT token for given email.
     */
    public String generateToken(String email, String userType) {        return Jwts.builder()
                .claim("sub", email)
                .claim("role", userType)
                .claim("iat", new Date(System.currentTimeMillis()))
                .claim("exp", new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7 days
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * ✅ Extract email from JWT token.
     */
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * ✅ Extract username from JWT token.
     */
    public String extractUsername(String token) {
        return extractEmail(token);
    }

    /**
     * ✅ Validate token and check if user exists in DB.
     */
    public boolean validateToken(String token, String userType) {
        try {
            String email = extractEmail(token);            return switch (userType.toLowerCase()) {
                case "admin" -> adminRepository.findByUsername(email).isPresent();
                case "doctor" -> doctorRepository.findByEmail(email).isPresent();
                case "patient" -> patientRepository.findByEmail(email).isPresent();
                default -> false;
            };
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * ✅ Get claims from token.
     */
    private Claims getClaims(String token) {        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * ✅ Generate signing key from secret.
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
