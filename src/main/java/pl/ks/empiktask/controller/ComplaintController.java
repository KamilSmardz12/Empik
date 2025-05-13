package pl.ks.empiktask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ks.empiktask.dto.ComplaintRequest;
import pl.ks.empiktask.dto.ComplaintResponse;
import pl.ks.empiktask.service.interfaces.ComplaintService;
import pl.ks.empiktask.util.HttpRequestUtil;

import java.util.List;

@RestController
@RequestMapping("/api/v1/complaints")
@RequiredArgsConstructor
@Tag(name = "Complaint API", description = "Operacje na reklamacjach")
public class ComplaintController {

    private final ComplaintService service;

    @PostMapping
    @Operation(summary = "Dodaj reklamację")
    public ResponseEntity<ComplaintResponse> create(@Valid @RequestBody ComplaintRequest request, HttpServletRequest httpReq) {
        var ip = HttpRequestUtil.getClientIp(httpReq)
                .orElseThrow(() -> new IllegalStateException("No IP found"));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createComplaint(request, ip));
    }

    @GetMapping
    @Operation(summary = "Pobierz wszystkie reklamacje")
    public ResponseEntity<List<ComplaintResponse>> getAll() {
        return ResponseEntity.ok(service.getAllComplaints());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Zaktualizuj treść reklamacji")
    public ResponseEntity<ComplaintResponse> update(@PathVariable Long id, @RequestBody String content) {
        return ResponseEntity.ok(service.updateComplaint(id, content));
    }

}
