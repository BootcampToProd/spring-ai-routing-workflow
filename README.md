# Spring AI - Routing Workflow Pattern

This repository demonstrates the **Spring AI Routing Workflow Pattern**, an intelligent request routing system that uses LLM-powered classification to automatically direct customer inquiries to the most appropriate specialized support handlers.

📖 **Dive Deeper**: For a complete walkthrough, detailed explanations of the Routing Workflow pattern, and step-by-step instructions for the example application, read our blog post.<br>
👉 [Spring AI Routing Workflow: Intelligent Request Routing with LLM-Powered Classification](https://bootcamptoprod.com/spring-ai-routing-workflow-guide/)

🎥 **Visual Learning**: Prefer video tutorials? Watch our step-by-step implementation guide on YouTube.<br>
👉 [Spring AI Routing Workflow: Intelligent Request Routing with LLM-Powered Classification](https://youtu.be/_HKoUl78GXk) 🤖
---

## 📦 Environment Variables

Make sure to provide these Java environment variables when running the application:

- `GEMINI_API_KEY`: Your Google Gemini API key.

---

## About This Project

This project implements an **E-commerce Customer Support System** as a real-world example of the Spring AI Routing Workflow. It showcases how to:

*   Set up a Spring Boot application with Spring AI.
*   Configure Spring AI to use Google Gemini (or other LLMs).
*   Implement the `SupportRoutingWorkflow` to intelligently classify and route customer inquiries.
*   Use LLM-powered analysis to determine the most appropriate support specialist.
*   Process inquiries with specialized prompts tailored for different support teams.
*   Handle diverse customer inquiries including order support, product questions, technical issues, and billing concerns.
*   Implement structured output parsing to extract routing decisions from LLM responses.

---

**Learn More**: This is part of our Spring AI Agentic Workflow series. Check out our other workflow patterns:
- [Chain Workflow Pattern](https://bootcamptoprod.com/spring-ai-chain-workflow-guide/)
- [Parallelization Workflow Pattern](https://bootcamptoprod.com/spring-ai-parallelization-workflow-guide/)