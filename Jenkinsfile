pipeline {
    agent any

    tools {
        jdk 'jdk11'
        maven 'maven3'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/your-org/api-jackson-tests.git'
            }
        }

        stage('Build & Run Tests') {
            steps {
                sh 'mvn clean test'
            }
        }
    }

    post {

        always {
            echo 'Publishing Test Reports'
            junit 'target/surefire-reports/*.xml'
        }

        success {
            echo '✅ Build & Tests Successful'
        }

        failure {
            echo '❌ Tests Failed – Check Logs'
        }
    }
}
