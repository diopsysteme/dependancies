# 🔧 Base Service Dependency

Une dépendance modulaire prête à l'emploi qui fournit une implémentation standardisée des opérations CRUD, une configuration Swagger, Firebase, Keycloak, JWT et un service d'envoi d'emails. Il vous suffit d'étendre quelques classes abstraites pour bénéficier d'une architecture robuste et intégrée.

---

## 🚀 Fonctionnalités Principales

- 🔄 **CRUD Automatisé** : Implémentez simplement `AbstractEntity`, `AbstractRepository`, `AbstractService` et bénéficiez des opérations de base (Create, Read, Update, Delete) sans effort.
- 📚 **Swagger** : Documentation Swagger auto-configurée pour toutes vos API.
- 🔐 **JWT** :
  - Filtre d'authentification JWT (`JwtFilter`)
  - Configuration et gestion automatique des tokens
- 👑 **Keycloak** :
  - Intégration complète avec Keycloak
  - Services d’administration Keycloak prêts à l'emploi (`KeycloakAdminService`)
- 🔥 **Firebase** :
  - Configuration rapide via une URL
  - Services Firebase préconfigurés pour l’interaction (ex: envoi de notifications, stockage, etc.)
- 📧 **MailService** :
  - Service d’envoi d’e-mails simple et personnalisable

---
