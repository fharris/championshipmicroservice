#!/bin/bash

echo "Starting configuration of Championship Microservice!"

echo Running in $SHELL

# 1.	Get the code from Github
# If you haven’t done yet, get the code from the repository:

## git clone https://github.com/fharris/championshipmicroservice

# 2.	Create the curiosityevents namespace

kubectl apply -f consumerms-namespace.yaml

# 3.	Deploy database

kubectl apply -f ./databaseconfig/mysql-db-secret.yaml

# You will notice that the password in the mysql-db-secret.yaml is set to the base64 of mySQLpword# 

kubectl apply -f ./databaseconfig/mysql-persistentvolume.yaml
kubectl apply -f ./databaseconfig/mysql-persistentvolumeclaim.yaml
kubectl apply -f ./databaseconfig/mysql-persistent-deploy.yaml
kubectl apply -f ./databaseconfig/mysql-db-service.yaml

kubectl -n curiosityevents \ 
create secret generic consumerms-mysql-db-secret \ 
--from-literal=SPRING_DATASOURCE_PASSWORD=$MYSQL_CREDENTIALS_PSW \
--from-literal=SPRING_DATASOURCE_USERNAME=$MYSQL_CREDENTIALS_USR

# Replace $MYSQL_CREDENTIALS_PSW with the password like Welcome#1 and $MYSQL_CREDENTIALS_USR with the user which should be “championship”.

# When the mysql pod is running configure the mysql database for the application:  

# kubectl -n curiosityevents port-forward svc/mysql-db-deployment 3306:80

# And in a different tab, go to where you’ve clone the repository, and run the following command to create the user championship and the database championshipdb:
#(You need to have a mysql client installed, change the password accordingly to what you used in step 333.,m)

#mysql -h 127.0.0.1 -u root -pmySQLpword#2023 < ./ databaseconfig/create-championshipdb-resources.sql

#If you want to validate, run :

#mysql -h 127.0.0.1 -u root -pmySQLpword#2023;
#And once logged, run:

#SHOW DATABASES;

#You should be able to see the championshipdb created. 

#4.	Deploy the application

kubectl apply -f ./appconfig/consumerms-service-clusterip.yaml
kubectl apply -f ./appconfig/consumerms-configmap.yaml
kubectl describe configmap consumerms-configmap -n curiosityevents
kubectl apply -f  ./appconfig/consumerms-deployment.yaml -n curiosityevents
#kubectl rollout restart -f consumerms-deployment.yaml
kubectl get deployments -n curiosityevents
kubectl get pods -n curiosityevents

#You should be able to see the championship microservice and respective database pods running. Lets access it with:
#kubectl -n curiosityevents port-forward svc/consumerms-service-clusterip 9000:80


