# FitVerse Monorepo (Phase 1)

This repository begins the phased build-out of **FitVerse – AI-Driven Personal Health Companion**. Phase 1 establishes the IntelliJ-ready Maven multi-module skeleton that future phases will flesh out with full domain logic, integrations, and infrastructure.

## Current Structure

- Aggregator parent `pom.xml` with Spring Boot 3.2.5 BOM management
- Backend service modules:
  - api-gateway
  - auth-service
  - user-profile-service
  - foodvision-service
  - calorie-tracker-service
  - fitness-recommendation-service
  - chatbot-service
  - notification-service
  - analytics-service
  - media-service (optional placeholder)
- Frontend module `frontend-react` configured with `frontend-maven-plugin`
- Each backend module contains a minimal Spring Boot application entrypoint and `application.yml` naming configuration.

## Next Steps

Future phases will iteratively add:

1. Domain models, DTOs, controllers, services, repositories, and tests per microservice
2. Security, messaging, persistence, and AI adapters
3. Docker, infrastructure automation, CI/CD pipelines, and comprehensive documentation
4. React frontend implementation with Redux Toolkit Query, Vite, and supporting tooling

## Building

At this stage, each module is a compile-only placeholder. To validate the Maven structure:

```bash
mvn -pl api-gateway clean package
```

Full multi-module build will be enabled once module dependencies and code are implemented in later phases.
