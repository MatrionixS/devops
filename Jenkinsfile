pipeline{
    agent any
    tools {
        maven 'maven-3.9.16'
        jdk 'java-25'
    }
    stages {
        stage("test") {
            steps {
                script {
                sh 'mvn clean test'
                }
            }
        }
        stage("build") {
            steps {
               script {
               echo 'Building docker image'
               sh 'docker build -t  baribars/demo-app:scr-1.0 .'
               withCredentials([usernamePassword(credentialsId: 'docker-hub', passwordVariable: PASSWORD, usernameVariable: USERNAME)]) {
                    sh 'echo "$PASSWORD" | docker login -u "$USERNAME" --password-stdin'
               }
               echo 'Pushing docker image'
               sh 'docker push baribars/demo-app:jma-1.1'
               }
            }
        }
    }

}