package com.bootcamptoprod.controller;

import com.bootcamptoprod.dto.InquiryRequest;
import com.bootcamptoprod.dto.SupportResponse;
import com.bootcamptoprod.service.CustomerSupportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/support")
public class CustomerSupportController {

    private final CustomerSupportService supportService;

    public CustomerSupportController(CustomerSupportService supportService) {
        this.supportService = supportService;
    }

    /**
     * Endpoint to handle customer support inquiries.
     * The system will automatically route the inquiry to the appropriate support team.
     */
    @PostMapping("/inquiry")
    public ResponseEntity<SupportResponse> handleInquiry(@RequestBody InquiryRequest request) {
        String response = supportService.handleCustomerInquiry(request.inquiry());
        return ResponseEntity.ok(new SupportResponse(response));

    }
}

