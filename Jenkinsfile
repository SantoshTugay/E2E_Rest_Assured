pipeline {
    agent any

    tools {
        jdk 'jdk11'
        maven 'maven3'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'master',
                    url: 'https://github.com/SantoshTugay/E2E_Rest_Assured/.git'
            }
        }

        stage('Build & Run Tests') {
            steps {
                sh 'mvn clean test com.api.tests.*'
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
