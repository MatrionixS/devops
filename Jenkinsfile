pipeline{
    agent any
    stages {
        stage("test") {
            when {
                expression {
                    BRANCH_NAME == 'dev'
                }
            }
            steps {
                echo "Testing the application..."
            }
        }
        stage("build") {
            when {
                expression {
                    BRANCH_NAME == 'jenkins-jobs'
                }
            }
            steps {
                echo "Building the application..."
            }
        }
        stage("deploy") {
            when {
                expression {
                    BRANCH_NAME == 'jenkins-jobs'
                }
            }
            steps {
                echo "Deploying the application..."
            }
        }
    }

}