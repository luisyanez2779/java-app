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
                    echo "Testing application..."
                }
            }
            /*
            steps {
                script {
                    gv.testApp()
                }
            }
            */
        }        
        stage("build jar") {
            steps {
                script {
                    echo "Building jar..."
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
                    buildJar()
                }
            }*/
        }
        stage("build and push image image") {
            steps {
                script {
                    echo "Building application..."
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
                    buildImage 'luisyanez27/demo-app:jma-3.0'
                    dockerLogin 'luisyanez27/demo-app:jma-3.0'
                    dockerPush 'luisyanez27/demo-app:jma-3.0'
                }
            }
            */
        }
        stage("deploy") {
          steps {
              script {
                def dockerCmd = 'docker run -d -p 3080:3080 luisyanez27/react-nodejs-app:rn-1.0'
                sshagent(['ec2-server-key']) {
                  sh "ssh -o StrictHostKeyChecking=no ec2-user@18.217.58.32 ${dockerCmd}"
                }
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
