def gv

pipeline{
    agent any
    stages {
        stage("init") {
            steps {
                script {
                    gv = load "jenkins.groovy"
                }
            }
        }
        stage("test") {
            steps {
                script {
                    gv.testApp
                }
            }
        }
        stage("build") {
            steps {
               script {
                gv.buildApp
               }
            }
        }
        stage("deploy") {
            steps {
                script {
                gv.deployApp
                }
            }
        }
    }

}