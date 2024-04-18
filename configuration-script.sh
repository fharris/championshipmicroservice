#!/bin/bash

echo "Starting configuration of Championship Microservice!"

echo Running in $SHELL

# 1.	Get the code from Github
# If you haven’t done yet, get the code from the repository:

## git clone https://github.com/fharris/curiositymonolith

# 2.	Create the curiositymonolith namespace

kubectl apply -f championshipms-namespace.yaml

# 3.	Deploy database

kubectl apply -f ./databaseconfig/mysql-db-secret.yaml

# You will notice that the password in the mysql-db-secret.yaml is set to the base64 of mySQLpword# 

kubectl apply -f ./databaseconfig/mysql-persistentvolume.yaml
kubectl apply -f ./databaseconfig/mysql-persistentvolumeclaim.yaml
kubectl apply -f ./databaseconfig/mysql-persistent-deploy.yaml
kubectl apply -f ./databaseconfig/mysql-db-service.yaml

kubectl -n curiosityevents \ 
create secret generic championshipms-mysql-db-secret \ 
--from-literal=SPRING_DATASOURCE_PASSWORD=$MYSQL_CREDENTIALS_PSW \
--from-literal=SPRING_DATASOURCE_USERNAME=$MYSQL_CREDENTIALS_USR

# Replace $MYSQL_CREDENTIALS_PSW with the password like Welcome#1 and $MYSQL_CREDENTIALS_USR with the user which should be “curiosity”.

# When the mysql pod is running configure the mysql database for the application:  

# kubectl -n curiositymonolith port-forward svc/mysql-db-deployment 3306:80

# And in a different tab, go to where you’ve clone the repository, and run the following command to create the user curiosity and the database curiositydb:
#(You need to have a mysql client installed, change the password accordingly to what you used in step 333.,m)

#mysql -h 127.0.0.1 -u root -pmySQLpword#2023 < ./ databaseconfig/create-curiositydb-resources.sql

#If you want to validate, run :

#mysql -h 127.0.0.1 -u root -pmySQLpword#2023;
#And once logged, run:

#SHOW DATABASES;

#You should be able to see the curiositydb created. 

#4.	Deploy the application

kubectl apply -f ./appconfig/championshipms-service-clusterip.yaml
kubectl apply -f ./appconfig/championshipms-configmap.yaml
kubectl describe configmap championshipms-configmap -n curiosityevents
kubectl apply -f  ./appconfig/championshipms-deployment.yaml -n curiosityevents
#kubectl rollout restart -f curiositymonolith-deployment.yaml
kubectl get deployments -n curiosityevents
kubectl get pods -n curiosityevents

#You should be able to see the curiositymonolith and respective database pods running. Lets access it with:
#kubectl -n curiositymonolith port-forward svc/curiositymonolith-service-lb 9000:80


