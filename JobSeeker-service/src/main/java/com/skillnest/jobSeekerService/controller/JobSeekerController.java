package com.skillnest.jobSeekerService.controller;

import com.skillnest.jobSeekerService.dtos.request.*;
import com.skillnest.jobSeekerService.dtos.response.*;
import com.skillnest.jobSeekerService.service.JobSeekerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("job-seeker")
@RequiredArgsConstructor
public class JobSeekerController {

    private final JobSeekerService jobSeekerService;
    @PostMapping("complete-profile")
    public ResponseEntity<RegisterJobSeekerResponse> completeProfile(@RequestBody RegisterJobSeekerRequest request){
        return ResponseEntity.ok(jobSeekerService.completeProfile(request));
    }
    @PostMapping("update-profile")
    public ResponseEntity<UpdateJobSeekerProfileResponse> updateProfile(@RequestBody UpdateJobSeekerProfileRequest request){
        return ResponseEntity.ok(jobSeekerService.updateProfile(request));
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UpdateJobSeekerProfileResponse> getProfile(@PathVariable String userId){
        return ResponseEntity.ok(jobSeekerService.getProfile(userId));
    }
    @PostMapping("set-availability")
    public ResponseEntity<AvailabilitySlotResponse> setAvailability(@RequestBody List<AvailabilitySlotRequest> request){
        return ResponseEntity.ok(jobSeekerService.setAvailability(request));
    }
    @PostMapping("set-bank-account")
    public ResponseEntity<BankAccountResponse> setBankAccount(@RequestBody BankAccountRequest request){
        return ResponseEntity.ok(jobSeekerService.setBankAccount(request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<BankAccountResponse> getBankAccount(@PathVariable String id){
        return ResponseEntity.ok(jobSeekerService.getBankAccount(id));
    }
    @PostMapping("upload-documents")
    public ResponseEntity<UploadDocumentsResponse> uploadDocuments(@RequestBody VerificationDocumentRequest request){
        return ResponseEntity.ok(jobSeekerService.uploadDocuments(request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<VerificationDocumentResponse> getDocuments(@PathVariable String id){
        return ResponseEntity.ok(jobSeekerService.getDocuments(id));
    }
    @PostMapping("upload-work-images")
    public ResponseEntity<WorkImageResponse> uploadWorkImages(@RequestBody List<WorkImageRequest> requests){
        return ResponseEntity.ok(jobSeekerService.uploadWorkImages(requests));
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<WorkImageResponse>> getWorkImages(@PathVariable String id){
        return ResponseEntity.ok(jobSeekerService.getWorkImages(id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<AvailabilitySlotResponse>> getAvailability(@PathVariable String id){
        return ResponseEntity.ok(jobSeekerService.getAvailability(id));
    }

}
