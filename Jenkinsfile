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
      IMAGE_NAME = 'luisyanez27/java-maven-app:jma-1.0'
    }
    stages {
        stage('increment version') {
            steps {
                script {
                    echo 'incrementing app version...'
                    sh 'mvn build-helper:parse-version versions:set \
                        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                        versions:commit'
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                    def version = matcher[0][1]
                    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
                    echo "version number is ${env.IMAGE_NAME}"
                }
            }
        }
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
        stage('commit version update'){
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'github-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]){
                        sh 'git remote set-url origin https://$USER:$PASS@github.com/luisyanez2779/java-maven-app.git'
                        sh 'git add .'
                        sh 'git commit -m "ci: version bump"'
                        sh 'git push origin HEAD:payment'
                    }
                }
            }
        }
    }   
}
