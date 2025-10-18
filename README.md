# FitVerse Monorepo (Phase 3)

Phase 3 upgrades the FitVerse platform with cross-cutting production capabilities: JWT-secured endpoints, Kafka-based eventing, Redis-backed chat presence, MinIO media storage integration, and reusable AI adapters. Every microservice now boots with opinionated infrastructure defaults (Flyway migrations, datasource profiles, tracing-ready configuration) so later phases can focus on orchestration and frontend experiences.

## What’s Included

- **Platform shared library** – New `platform-shared` module centralises JWT token services, Spring Security defaults, Kafka producer templates, Redis + MinIO integration, and AI strategy abstractions (food inference, workout recommendations, chatbot adapters).
- **Service hardening** – Each Spring Boot service imports the shared security stack, exposes `/actuator/health` anonymously, and requires Bearer JWT for business APIs. Auth service now hashes passwords, issues signed tokens, and surfaces the authenticated profile via `GET /auth/me`.
- **Event-driven flows** – FoodVision, Fitness Recommendation, and Notification services publish domain events (`foodlog.created`, `plan.generated`, `notification.send`) through KafkaTemplate stubs, ready for downstream processing.
- **Stateful integrations** –
  - Chatbot service records presence snapshots in Redis and routes messages through pluggable AI adapters (default echo + OpenAI placeholder).
  - Media service persists metadata while streaming uploads to MinIO buckets with automatic bucket provisioning.
  - Fitness recommendation delegates plan creation to the heuristic RecommendationEngine and emits analytics-friendly JSON payloads.
- **Persistence readiness** – All services ship Flyway migrations aligned with the PRD schema, H2 defaults for local testing, and externalised datasource/Kafka/Redis/MinIO properties.
- **Expanded unit tests** – Updated suites verify JWT issuance, Kafka interactions, Redis presence writes, AI adapter orchestration, and MinIO uploads via mocks.

## Roadmap

| Phase | Focus | Status |
| --- | --- | --- |
| 1 | Repository skeleton | ✅ Completed |
| 2 | Core service foundations | ✅ Completed |
| 3 | Security, persistence integrations, AI adapters, messaging | ✅ Completed in this update |
| 4 | Docker, infrastructure, CI/CD | ⏳ Pending |
| 5 | React frontend experience | ⏳ Pending |

## Development

```
bash
# run the backend unit tests (skip frontend tooling requirements)
mvn -pl '!frontend-react' clean test
```

Module-specific execution is available via `mvn -pl <module> test`.

Next phase will tackle container orchestration (`docker-compose`), GitHub Actions CI, and a production-ready React frontend consuming the secured gateway APIs.
