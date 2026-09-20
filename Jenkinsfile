pipeline{
    agent any
    stages {
        stage("test") {
            when {
                expression {
                    env.BRANCH_NAME == 'dev'
                }
            }
            steps {
                echo "Testing the application..."
            }
        }
        stage("build") {
            when {
                expression {
                    env.BRANCH_NAME == 'jenkins-jobs'
                }
            }
            steps {
                echo "Building the application..."
            }
        }
        stage("deploy") {
            when {
                expression {
                    env.BRANCH_NAME == 'jenkins-jobs'
                }
            }
            steps {
                echo "Deploying the application..."
            }
        }
    }

}