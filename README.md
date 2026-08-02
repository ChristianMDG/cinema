# 🎬 Cinema Reservation API

Application backend de gestion de réservations de cinéma développée avec **Spring Boot**, déployée sur **AWS Lambda** via la plateforme **POJA**.

Projet réalisé dans le cadre du cursus Full Stack à la **Haute École d'Informatique (HEI) d'Ivandry**, Antananarivo, Madagascar.

---

## 📋 Table des matières

- [Description](#description)
- [Stack technique](#stack-technique)
- [Architecture](#architecture)
- [Modèle de données](#modèle-de-données)
- [Règles d'accès par rôle](#règles-daccès-par-rôle)
- [Installation](#installation)
- [Configuration](#configuration)
- [Lancement](#lancement)
- [Tests](#tests)
- [Déploiement](#déploiement)
- [Convention de commits](#convention-de-commits)
- [Équipe](#équipe)

---

## Description

API REST permettant à un cinéma de gérer :
- son catalogue de films et de salles
- ses séances (projections)
- les réservations de sièges par les clients

Trois niveaux d'utilisateurs cohabitent dans le système : **CLIENT**, **EMPLOYEE**, **MANAGER**, chacun avec des permissions différentes sur les endpoints.

---

## Stack technique

| Composant | Technologie |
|---|---|
| Langage | Java 21 |
| Framework | Spring Boot 3 |
| Build tool | Gradle |
| Base de données | PostgreSQL (hébergée sur [Neon](https://neon.tech)) |
| ORM | Spring Data JPA / Hibernate |
| Migrations DB | Flyway |
| Sécurité | Spring Security + JWT |
| Validation | Bean Validation (Jakarta) |
| Tests | JUnit 5, Mockito, AssertJ, Spring Boot Test |
| Couverture de code | Jacoco (≥ 80%) |
| Déploiement | AWS Lambda (SnapStart) via POJA / AWS SAM |
| CI/CD | GitHub Actions (généré par POJA) |

---

## Architecture

Structure des packages suivant la convention POJA :
com.hei.cinema
├── endpoint
│ └── rest
│ ├── controller # Contrôleurs REST
│ └── dto # DTOs de requête/réponse
├── repository
│ ├── entity # Entités JPA
│ └── *Repository.java # Interfaces Spring Data JPA
├── security # JWT, filtres, config Spring Security
└── service # Logique métier
**Principe** : les entités JPA (mutables, avec relations) ne sortent jamais directement de l'API — les DTOs (`record`, immuables) servent d'interface avec l'extérieur pour les endpoints sensibles comme `/auth`.

---

## Modèle de données
Room (1) ──contains── () Seat
Movie (1) ──show── () Projection ──takes place──> Room
Projection (1) ──concern── () Reservation
User (1) ──make── () Reservation
Reservation () ──books── () Seat
### Entités principales

| Entité | Champs clés |
|---|---|
| `Room` | number, capacity |
| `Seat` | number, room |
| `Movie` | title, genre[], description, duration |
| `Projection` | datetime, seatPrice, movie, room |
| `User` | firstName, lastName, email, password (BCrypt), role |
| `Reservation` | firstName, lastName, email, seats[], projection, user |

### Enums

- **Genre** : THRILLER, ROMANCE, COMEDY, DRAMA, ACTION, SCI_FI, FANTASY, ANIMATION
- **UserRole** : CLIENT, EMPLOYEE, MANAGER

---

## Règles d'accès par rôle

| Endpoint | CLIENT | EMPLOYEE | MANAGER |
|---|---|---|---|
| `GET /movies` | 200 | 200 | 200 |
| `PUT /movies/:id` | 403 | 403 | 200 |
| `GET /reservations` | 403 | 200 | 200 |
| `GET /reservations/:id` | 200 si propriétaire, sinon 403 | 200 | 200 |
| `PUT /reservations/:id` | 403 | 200 | 200 |
| `PUT /projections/:id` | 403 | 403 | 200 |
| `GET /projections` | 200 | 200 | 200 |
| `POST /auth/register` | public | public | public |
| `POST /auth/login` | public | public | public |

---

## Installation

### Prérequis

- Java 21 (JDK)
- AWS SAM CLI (déploiement)
- Un compte [Neon](https://neon.tech) (PostgreSQL)
- Docker (optionnel, pour les tests d'intégration)

### Cloner le projet

```bash
git clone https://github.com/<votre-org>/cinema.git
cd cinema
```

---

## Configuration

Le projet utilise des variables d'environnement — **aucun secret n'est commité**.

Copie le fichier d'exemple et renseigne tes propres valeurs :

```bash
cp .env.example .env
```

**Variables requises (`.env`)** :
SPRING_DATASOURCE_USERNAME=<votre-utilisateur>
SPRING_DATASOURCE_PASSWORD=<votre-mot-de-passe>
SPRING_DATASOURCE_URL=<votre-url-postgres>
JWT_SECRET=<clé-secrète-longue-et-aléatoire>
⚠️ Ne jamais commit `.env`. Vérifié via `.gitignore`. En cas de doute :
```bash
git ls-files | grep .env
```

---

## Lancement

### En local

```bash
./gradlew bootRun
```

L'API démarre sur `http://localhost:8080`.

### Migrations de base de données

Les migrations Flyway s'exécutent automatiquement au démarrage (`src/main/resources/db/migration/`).

### Vérifier que l'API répond

```bash
curl http://localhost:8080/ping
```

---

## Tests

### Lancer tous les tests

```bash
./gradlew test
```

### Générer le rapport de couverture Jacoco

```bash
./gradlew jacocoTestReport
```

Le rapport HTML est disponible dans `build/reports/jacoco/test/html/index.html`.

**Objectif du sujet : couverture ≥ 80%.**

### Collection Postman

Une collection Postman est disponible dans `/postman/` pour tester manuellement les règles d'accès par rôle (voir table ci-dessus).

---

## Déploiement

Le déploiement se fait automatiquement via GitHub Actions + AWS SAM lors d'un push sur la branche principale, grâce à la configuration générée par [POJA](https://poja.io).

### Build manuel (debug)

```bash
sam build
sam deploy
```

### Runtime

- Java 21, architecture ARM64
- AWS Lambda avec SnapStart (démarrage à froid réduit)

---

## Convention de commits

Ce projet suit [Conventional Commits](https://www.conventionalcommits.org/) :
<type>(<scope>): <description>
**Types utilisés** : `feat`, `fix`, `chore`, `docs`, `test`, `refactor`

**Exemples** :
feat(entity): add User and Reservation entities
feat(security): implement JwtAuthFilter for request authentication
test(controller): add integration tests for reservation access rules by role
fix(controller): correct 403/200 access rules for reservationById endpoint

**Historique propre** : commits squashés/renommés avant push si nécessaire (`git rebase -i`), jamais après un push partagé.

---

## Contraintes du projet

- ✅ Projet POJA (déploiement AWS Lambda)
- ✅ Couverture de tests ≥ 80% (Jacoco)
- ✅ Historique de commits propre (Conventional Commits, rebase/squash)

---

## Équipe

Répartition du travail par domaine fonctionnel :

| Membre | Domaine |
|---|---|
| Nomena | Catalogue — Room, Seat, Movie, Projection |
| Christian | Utilisateurs & Réservations — User, Reservation, Sécurité (JWT, Auth) |

---

## Licence

Projet académique — HEI Ivandry, Antananarivo.
