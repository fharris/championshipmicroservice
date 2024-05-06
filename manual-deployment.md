**Manual deployment**

Get the code from GitHub If you haven’t done so, get the code from the repository with the below command:
```
git clone https://github.com/fharris/championshipmicroservice
```
***1-Create the curiosityevents namespace***
 Change to the championship microservice folder and run the following commands.
If you just want to see the app running and leave the manual steps to study later just run the following script:

```
./deploy-championship.sh
```

If all goes well, you may jump to step 5 to run the application and ignore the rest of the steps.

If you want to install things step by step, then, once everything is cleaned with the housekeeping script, try to create the namespace with the following command:

```
kubectl apply -f appconfig/consumerms-namespace.yaml
```

***2-Deploy database***
Deploy the MySQL database with the following command:

```
kubectl apply -f ./databaseconfig/.
```

You will notice that the password in the mysql-db-secret.yaml is set to the base64 of mySQLpword#2023. If you change the password, please don’t forget to take note of that.

***3-Configure the mysql database for the application***

For simplicity, we will use the curiosity user to use the championshipdb which was already also created during the curiosity microservice installation.

***4-Deploy the application***
Deploy the application with the following command:

```
kubectl apply -f ./appconfig/.
```

***5-Test the application***
If all goes well, check that you have successfully deployed the application and the database with the following command:

```
kubectl get pods -n curiosityevents
```

You should be able to see the curiosity microservice pods, the MySQL database pods, the Zookeeper and Kafka pods and finally the championship microservice pods, which are called consumers.


![image](https://github.com/fharris/championshipmicroservice/assets/17484224/35c724f8-3cb1-4b13-a464-3caac5541789)


If you open your browser at *http://localhost:3000* you should be able to use the curiosity microservice and query Wikipedia, get the stats with the queries you are doing and an up to date league table with the number of articles you have consulted in total:

![image](https://github.com/fharris/championshipmicroservice/assets/17484224/c6b20615-6f18-4998-9b59-812160b40878)







