#!/usr/bin/env groovy

@Library('jenkins-shared-library')
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
        stage("build image") {
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
