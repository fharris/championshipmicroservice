#!/usr/bin/env groovy

pipeline {
  environment {
    dockerImageBuild = ''
    dockerImageLatest = ''
    MYSQL_CREDENTIALS = credentials('id-mysql')
    kubernetes_proxy = "${env.KUBERNETES_ENDPOINT}"
  }
  agent any
  stages{
      
      
      
    stage('Get source code') {
      steps {
        git branch: 'main', 
            url: 'http://gogs:3000/gogs-user/championshipmicroservice.git'
      }
    }
    
    
    stage('Checkout code') {
        steps {
            checkout scm
        }
    }
   
    
      stage('Listing source code') {
      steps {
        sh 'ls -ltra'
      }
    }
      
      stage('Configuring Championship in Kubernetes') {
      steps {
        withKubeConfig( credentialsId: 'jenkins-token-kubernetes', serverUrl: kubernetes_proxy ) {
	          sh "kubectl apply -f appconfig/consumerms-namespace.yaml"
            sh "kubectl apply -f ./databaseconfig/."
            sh "sleep 30"
            //sh "kubectl -n curiosityevents exec -it `kubectl -n curiosityevents get --no-headers=true pods -l app=mysql-db -o custom-columns=:metadata.name` -- mysql -h 127.0.0.1 -u root -pmySQLpword#2023 < ./databaseconfig/create-championshipdb-resources.sql"
            sh 'kubectl -n curiosityevents create secret generic consumerms-mysql-db-secret --from-literal=SPRING_DATASOURCE_PASSWORD=$MYSQL_CREDENTIALS_PSW --from-literal=SPRING_DATASOURCE_USERNAME=$MYSQL_CREDENTIALS_USR --dry-run=client -o yaml > consumerms-mysql-db-secret.yaml'
            sh "kubectl apply -f consumerms-mysql-db-secret.yaml"
            sh "kubectl apply -f ./appconfig/."
            sh "kubectl -n curiosityevents rollout restart deployment/consumerms-deployment"
        }
      }
    }

  
  }
  post {
        // Clean after build
        always {
            cleanWs(cleanWhenNotBuilt: true,
                    deleteDirs: true,
                    disableDeferredWipeout: false,
                    notFailBuild: false,
                    patterns: [[pattern: '.gitignore', type: 'INCLUDE'],
                               [pattern: '.propsfile', type: 'EXCLUDE']])
        }
    }
}
