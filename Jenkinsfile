pipeline{
    agent any
    tools {
        maven 'maven-3.9.16'
        jdk 'java-25'
    }
    stages {
       stage("init") {
            steps {
                script {
                echo "Executing pipeline for branch name $GIT_BRANCH"
                }
            }
        }
        stage("test") {
            steps {
                script {
                sh 'mvn clean test'
                }
            }
        }
        stage("release") {
            steps {
                script {
                    echo 'Incrementing app version...'
                    sh 'mvn build-helper:parse-version versions:set \
                     -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                    versions:commit'
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                    def version = matcher[0][1]
                    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
                }
            }
        }
        stage("build") {
            steps {
               script {
               echo 'Building docker image'
               sh "docker build -t  baribars/demo-app:$IMAGE_NAME ."
               withCredentials([usernamePassword(credentialsId: 'docker-hub', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
                    sh 'echo "$PASSWORD" | docker login -u "$USERNAME" --password-stdin'
               }
               echo 'Pushing docker image'
               sh "docker push baribars/demo-app:$IMAGE_NAME"
               }
            }
        }
    }

}