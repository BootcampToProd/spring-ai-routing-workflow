package com.bootcamptoprod.service;

import com.bootcamptoprod.workflow.SupportRoutingWorkflow;
import org.springframework.stereotype.Service;

@Service
public class CustomerSupportService {

    private final SupportRoutingWorkflow routingWorkflow;

    public CustomerSupportService(SupportRoutingWorkflow routingWorkflow) {
        this.routingWorkflow = routingWorkflow;
    }

    /**
     * Processes a customer support inquiry by routing it to the appropriate specialist.
     * Returns a specialized response based on the inquiry type.
     */
    public String handleCustomerInquiry(String inquiry) {
        return routingWorkflow.routeCustomerInquiry(inquiry);
    }
}