pipeline{
    agent any
    stages {

        stage("test") {
            when {
                expression {
                    env.GIT_BRANCH == 'dev'
                }
            }
            steps {
                echo "Testing the application..."
            }
        }
        stage("build") {
            when {
                expression {
                    env.GIT_BRANCH == 'jenkins-jobs'
                }
            }
            steps {
                echo "Building the application..."
            }
        }
        stage("deploy") {
            when {
                expression {
                    env.GIT_BRANCH == 'jenkins-jobs'
                }
            }
            steps {
                echo "Deploying the application..."
            }
        }
    }

}