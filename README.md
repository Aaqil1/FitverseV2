# FitVerse Monorepo (Phase 2)

Phase 2 of the FitVerse build introduces foundational domain, service, and API layers across every backend microservice. Each Spring Boot module now exposes validated REST endpoints backed by DTOs, JPA entities, MapStruct mappers, repositories, services, and RFC-7807 compliant exception handling. Focus remains on creating testable service logic that future phases will secure, integrate, and operationalize.

## What’s Included

- **Shared tooling** – Parent Maven POM centralizes Spring Boot 3.2.5 + MapStruct versions and compiler settings.
- **Service foundations** – Auth, Profile, FoodVision, Calorie Tracker, Fitness Recommendation, Chatbot, Notification, Analytics, and Media services now contain:
  - Domain entities aligned with the PRD data model
  - DTOs with Jakarta validation
  - MapStruct mappers using Spring components
  - Service and repository layers with transactional boundaries
  - REST controllers exposing the PRD-specified endpoints
  - Global `@ControllerAdvice` implementations returning `ProblemDetail`
- **Unit tests** – Mockito + MapStruct powered unit tests validate service behaviour in each module.

## Roadmap

| Phase | Focus | Status |
| --- | --- | --- |
| 1 | Repository skeleton | ✅ Completed |
| 2 | Core service foundations | ✅ Completed in this update |
| 3 | Security, persistence integrations, AI adapters, messaging | ⏳ Next |
| 4 | Docker, infrastructure, CI/CD | ⏳ Pending |
| 5 | React frontend experience | ⏳ Pending |

## Development

```bash
# run the full unit test suite
mvn clean test
```

Individual modules can be tested via `mvn -pl <module> test` to iterate faster.

Subsequent phases will introduce real authentication, persistence configuration (MySQL, Redis, Kafka, MinIO), containerization, frontend integration, and observability tooling.
