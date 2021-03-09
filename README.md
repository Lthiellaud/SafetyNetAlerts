## SafetyNet Alerts
### Description

Partie back-end de l’application SafetyNet Alerts qui permettra d’envoyer aux services d’urgence des informations sur les populations concernées par un évènement nécessitant leur intervention. 


### Solutions technique
-   `Langage JAVA` (version 1.8.0_151)
-   `Framework Spring Boot` (version 2.4.2) – Starters utilisés  :
    *   Spring Web
    *   Spring Boot Actuator
    *   Spring Data JPA
    *   MySQL Driver
    *   Lombok
-   `Base de données MySQL` (version 8.0.22)

### Input
Fichier contenant les données relatives aux populations concernées :
-   `data.json` dans le répertoire \src\main\resources\json