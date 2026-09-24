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

        stage("Commit version") {
            steps {
                script {
                    sh 'git config --global user.email "jenkins@example.com"'
                    sh 'git config --global user.name "jenkins"'

                    sh 'git status'
                    sh 'git branch'
                    sh 'git config --list'

                    sh 'git remote set-url origin git@github.com:MatrionixS/devops.git'
                    sh 'git add .'
                    sh 'git commit -m "ci: version bump"'
                    sshagent(['github-ssh']) {
                        sh 'git push origin HEAD:jenkins-jobs'
                    }
                }
            }
        }

//         stage("Deploy image") {
//             steps {
//                 script {
//                     echo 'Deploying docker image to EC2...'
//                     def dockerCmd = "docker run -p 8080:8080 -d baribars/demo-app:${IMAGE_NAME}"
//                     sshagent(['droplet-ssh']) {
//                         sh "ssh -o StrictHostKeyChecking=no root@157.230.102.162 ${dockerCmd}"
//                     }
//                 }
//             }
//         }
        stage("Deploy image") {
            steps {
                script {
                    echo 'Deploying docker image to EC2...'
                    def dockerComposeCommand = "docker-compose -f docker-compose.yaml up -d"
                    sshagent(['droplet-ssh']) {
                        sh "scp docker-compose.yaml root@157.230.102.162:/root"
                        sh "ssh -o StrictHostKeyChecking=no root@157.230.102.162 ${dockerComposeCommand}"
                    }
                }
            }
        }

    }

}