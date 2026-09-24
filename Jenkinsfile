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
                     -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.incrementalVersion} \
                    versions:commit'
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                    def version = matcher[1][1]
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
        stage("Version prepare") {
            steps {
                script {
                    echo 'Preparing app version test...'
                    sh 'mvn build-helper:parse-version versions:set \
                    -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion}-SNAPSHOT \
                    versions:commit'
                }
            }
        }

        stage("Commit version update") {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'github-credentials', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
                        sh 'git config --global user.email "jenkins@example.com"'
                        sh 'git config --global user.name "jenkins"'

                        sh 'git status'
                        sh 'git branch'
                        sh 'git config --list'

                        sh 'git remote set-url origin https://${USERNAME}:${PASSWORD}github.com/MatrionixS/devops.git'
                        sh 'git add .'
                        sh 'git commit -m "ci: version bump"'
                        sh 'git push origin HEAD:jenkinkis-jobs'
                    }
                }
            }
        }
    }

}