package com.bootcamptoprod.dto;

/**
 * Record representing the classification response from the routing analysis.
 * This captures both the reasoning behind the decision and the selected route.
 */
public record RouteClassification(
        /**
         * Detailed explanation of why this particular route was chosen
         */
        String reasoning,

        /**
         * The selected route name that will handle the customer inquiry
         */
        String selectedRoute
) {
}