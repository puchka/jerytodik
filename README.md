![JeryTodik](https://github.com/Andriantomanga/jerytodik/blob/master/jerytodik-logo.png)

Un projet multi-module d'archivage de ressources web et d'exploitation de celles-ci pour des fins d'analyse et plus encore

## Build status

[![Build Status](https://travis-ci.org/Andriantomanga/jerytodik.svg?branch=master)](https://travis-ci.org/Andriantomanga/jerytodik)

## Exploitation

Le projet JeryTodik est en cours de développement, pour faire vos tests. Vous devez bien paramétrer les propriétés dans le fichier ```database.properties``` pour la connexion à la base de données (si vous voulez vous connecter à une base autre de PostgreSQL, ajouter le driver correspondant, item pour Hibernate), le fichier ```hibernate.properties``` et le fichier ```jerytodik.properties```. Il est normalement possible de mettre ces fichiers à n'importe quel endroit à partir du moment où la variable d'environnement ```jerytodik_home``` est spécifié et qu'ils soient dans un sous-répertoire ```config```. Ensuite, définir l'intervalle d'exécution dans le fichier ```jerytodik-scheduler.properties```. Pour créer le modèle physique de données, exécuter sur votre client Postgres le script ```jerytodik-root/jerytodik/sql/postgres_reset_db.sql```. Insérer les infos sur les ressources à archiver dans la table ```jt_source```. Une fois l'étape de paramétrage terminée, vous pouvez lancer le module ```jerytodik-scheduler```. Après chaque exécution vous pouvez consulter le résultat dans la table ```jt_history```.

## Technologies utilisées

Les principaux technologies utilisées dans ce projet :
 
 - Java 8
 - Spring 4
 - Enterprise Quartz Scheduler 2
 - OkHttp 3.6.0
 - Slf4J 1.7
 - JUnit 1.0.0-M3 (de org.junit.platform)

## Contribution

Vous pouvez me contacter au : contact at andriantomanga.com



