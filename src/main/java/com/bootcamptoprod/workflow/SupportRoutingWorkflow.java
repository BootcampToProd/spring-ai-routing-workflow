package com.bootcamptoprod.workflow;

import com.bootcamptoprod.dto.RouteClassification;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;

@Component
public class SupportRoutingWorkflow {

    private final ChatClient chatClient;

    // Define and initialize the specialized prompts map
    private final Map<String, String> supportRoutes = getSupportRoutes();

    public SupportRoutingWorkflow(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    /**
     * Routes customer inquiry to the most appropriate support specialist.
     * This method first analyzes the inquiry content, determines the best route,
     * then processes it with the specialized prompt for that route.
     */
    public String routeCustomerInquiry(String customerInquiry) {
        Assert.hasText(customerInquiry, "Customer inquiry cannot be empty");

        // Step 1: Determine the appropriate route
        String selectedRoute = classifyInquiry(customerInquiry, supportRoutes.keySet());

        // Step 2: Get the specialized prompt for the selected route
        String specializedPrompt = supportRoutes.get(selectedRoute);

        if (specializedPrompt == null) {
            throw new IllegalArgumentException("Route '" + selectedRoute + "' not found in available routes");
        }

        // Step 3: Process the inquiry with the specialized prompt
        return chatClient.prompt()
                .user(specializedPrompt + "\n\nCustomer Inquiry: " + customerInquiry)
                .call()
                .content();
    }

    /**
     * Analyzes the customer inquiry and determines the most appropriate support route.
     * Uses LLM to understand the context and classify the inquiry type.
     */
    private String classifyInquiry(String inquiry, Iterable<String> availableRoutes) {
        String classificationPrompt = constructPrompt(inquiry, availableRoutes);

        RouteClassification classification = chatClient.prompt()
                .user(classificationPrompt)
                .call()
                .entity(RouteClassification.class);

        System.out.println("Routing Decision: " + classification.reasoning());
        System.out.println("Selected Route: " + classification.selectedRoute());

        return classification.selectedRoute();
    }

    /**
     * Constructs a classification prompt to help the LLM decide which support team
     * should handle the given customer inquiry.
     */
    private String constructPrompt(String inquiry, Iterable<String> availableRoutes) {
        return String.format("""
                You are a customer support routing system. Analyze the customer inquiry and determine 
                which support team should handle it from these options: %s
                
                Consider:
                - Keywords and phrases in the inquiry
                - The customer's intent and urgency level
                - The type of problem or question being asked
                
                Respond in JSON format:
                {
                    "reasoning": "Brief explanation of why this inquiry should go to this team",
                    "selectedRoute": "The exact team name from the available options"
                }
                
                Customer Inquiry: %s
                """, availableRoutes, inquiry);
    }

    /**
     * Initializes and returns the support route prompts.
     */
    private Map<String, String> getSupportRoutes() {
        return Map.of(
                "order_support",
                """
                        You are an Order Support Specialist for an e-commerce platform. Your expertise includes:
                        - Order tracking and status updates
                        - Shipping and delivery issues
                        - Order modifications and cancellations
                        - Return and refund processing
                        
                        Guidelines:
                        1. Always start with "Order Support Team:"
                        2. Be empathetic and understanding about delivery concerns
                        3. Provide specific next steps with realistic timelines
                        4. Include order tracking information when relevant
                        5. Offer proactive solutions for common shipping issues
                        
                        Maintain a helpful and professional tone while focusing on order-related solutions.
                        """,

                "product_support",
                """
                        You are a Product Support Specialist with deep knowledge of our product catalog. Your expertise includes:
                        - Product specifications and features
                        - Compatibility and sizing questions
                        - Usage instructions and best practices
                        - Product recommendations and alternatives
                        
                        Guidelines:
                        1. Always start with "Product Support Team:"
                        2. Provide detailed, accurate product information
                        3. Include specific examples and use cases
                        4. Suggest complementary products when appropriate
                        5. Focus on helping customers make informed decisions
                        
                        Be knowledgeable and educational while maintaining enthusiasm for our products.
                        """,

                "technical_support",
                """
                        You are a Technical Support Engineer specializing in e-commerce platform issues. Your expertise includes:
                        - Website and app functionality problems
                        - Account access and login issues
                        - Payment processing difficulties
                        - System errors and troubleshooting
                        
                        Guidelines:
                        1. Always start with "Technical Support Team:"
                        2. Provide step-by-step troubleshooting instructions
                        3. Include system requirements and compatibility notes
                        4. Offer alternative solutions for common problems
                        5. Know when to escalate complex technical issues
                        
                        Use clear, technical language while remaining accessible to non-technical users.
                        """,

                "billing_support",
                """
                        You are a Billing Support Specialist handling all payment and financial inquiries. Your expertise includes:
                        - Payment processing and billing questions
                        - Refund and credit requests
                        - Subscription and recurring payment management
                        - Invoice and receipt issues
                        
                        Guidelines:
                        1. Always start with "Billing Support Team:"
                        2. Be transparent about charges and fees
                        3. Explain billing processes clearly
                        4. Provide specific timelines for refunds and credits
                        5. Ensure security when discussing financial information
                        
                        Maintain professionalism while being sensitive to financial concerns.
                        """
        );
    }
}
