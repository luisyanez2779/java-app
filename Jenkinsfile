#!/usr/bin/env groovy

library identifier: "jenkins-shared-library@jenkins-shared-lib", retriever: modernSCM(
  [
    $class: 'GitSCMSource',
    remote: 'https://github.com/luisyanez2779/jenkins-shared-library.git',
    credentialsId: 'github-credentials'
  ]
)

def gv

pipeline {
    agent any
    tools {
      maven "maven-3.9" 
    }
    environment {
      IMAGE_NAME = 'luisyanez27/java-maven-app:1.1.0'
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
            steps {
                script {
                    echo "Building jar..."
                    buildJar()
                }
            }
            /*
            when {
                expression {
                    BRANCH_NAME == 'master'
                }
            }
            */
        }
        stage("build and push image") {
            steps {
                script {
                    buildImage(env.IMAGE_NAME)
                    dockerLogin(env.IMAGE_NAME)
                    dockerPush(env.IMAGE_NAME)
                }
            }
            /*
            when {
                expression {
                    BRANCH_NAME == 'master'
                }
            }

            */
        }
        stage("deploy") {
          steps {
              script {
                deployToEC2(env.IMAGE_NAME)
              }
          }
            /*
            when {
                expression {
                    BRANCH_NAME == 'master'
                }
            }

            steps {
                script {

                    gv.deployApp()
                }
            }*/
        }
    }   
}
