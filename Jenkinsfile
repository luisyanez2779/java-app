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
      IMAGE_NAME = 'luisyanez27/java-maven-app:jma-2.0'
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
        }
        stage("build and push image") {
            steps {
                script {
                    buildImage(env.IMAGE_NAME)
                    dockerLogin(env.IMAGE_NAME)
                    dockerPush(env.IMAGE_NAME)
                }
            }
        }
        stage("deploy") {
          steps {
              script {
                echo "deploying docker image with docker compose to EC2 for image ${env.IMAGE_NAME}"
                def shellCmd = "bash ./server-cmds.sh ${IMAGE_NAME}"
                def ec2Instance = "ec2-user@18.217.58.32"
                sshagent(['ec2-server-key']) {
                    sh "scp server-cmds.sh ${ec2Instance}:/home/ec2-user"
                    sh "scp docker-compose.yaml ${ec2Instance}:/home/ec2-user"
                    sh "ssh -o StrictHostKeyChecking=no ${ec2Instance} ${shellCmd}"
                }
             }
          }
        }
    }   
}
