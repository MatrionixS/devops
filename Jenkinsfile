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
                    gv.testApp()
                }
            }
        }
        stage("build") {
            steps {
               script {
                    gv.buildApp()
               }
            }
        }
        stage("deploy") {
            input {
                message "Select the environment to deploy to"
                ok "Done"
                parameters {
                    choice(name: 'ENV', choices: ['dev', 'stage', 'prod'], description: '')
                }
            }
            steps {
                script {
                    gv.deployApp()
                    echo "Deploying to ${ENV}"
                }
            }
        }
    }

}