#!/usr/bin/env groovy

library identifier: "jenkins-shared-library@master", retriever: modernSCM(
  [
    $class: 'GitSCMSource',
    $remote: 'https://github.com/luisyanez2779/jenkins-shared-library.git',
    credentialsId: 'github-credentials'
  ]
)

def gv

pipeline {
    agent any
    tools {
      maven "maven-3.9" 
    }
    stages {
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("test") {
            steps {
                script {
                    gv.testApp()
                }
            }
        }        
        stage("build jar") {
            /*
            when {
                expression {
                    BRANCH_NAME == 'master'
                }
            }
            */
            steps {
                script {
                    buildJar()
                }
            }
        }
        stage("build and push image image") {
            /*
            when {
                expression {
                    BRANCH_NAME == 'master'
                }
            }
            */
            steps {              
                script {
                    buildImage 'luisyanez27/demo-app:jma-3.0'
                    dockerLogin 'luisyanez27/demo-app:jma-3.0'
                    dockerPush 'luisyanez27/demo-app:jma-3.0'
                }
            }
        }
        stage("deploy") {
            /*
            when {
                expression {
                    BRANCH_NAME == 'master'
                }
            }
            */
            steps {
                script {

                    gv.deployApp()
                }
            }
        }
    }   
}
