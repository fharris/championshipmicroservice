#!/usr/bin/env groovy

pipeline {
  environment {
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
      stage('Checking source code') {
      steps {
        sh 'ls -ltra'
      }
    }
   
      stage('Deployment  to Kubernetes') {
      steps {
        withKubeConfig( credentialsId: 'jenkins-token-kubernetes', serverUrl: kubernetes_proxy ) {
            sh "echo 'KUBERNETES ENDPOINT=$KUBERNETES_ENDPOINT'"
            sh "kubectl apply -f appconfig/championshipms-configmap.yaml"
            sh "kubectl describe configmap championshipms-configmap -n curiosityevents"
            sh "kubectl rollout restart -f appconfig/championshipms-deployment.yaml"
            sh "kubectl get deployments -n curiosityevents"
            sh "kubectl get pods -n curiosityevents"
        }
      }
    }

  
  }
  post {
        // Clean after build
        always {
            cleanWs(cleanWhenNotBuilt: false,
                    deleteDirs: true,
                    disableDeferredWipeout: true,
                    notFailBuild: true,
                    patterns: [[pattern: '.gitignore', type: 'INCLUDE'],
                               [pattern: '.propsfile', type: 'EXCLUDE']])
        }
    }
}
