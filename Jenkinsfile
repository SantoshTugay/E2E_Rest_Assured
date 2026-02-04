pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven3'
    }

    stages {

        stage('Build & Run Selected Tests') {
            steps {
                // Windows Jenkins → use bat, NOT sh
                bat 'mvn clean test -Dtest=SerializationDeserializationE2ETest'
            }
        }
    }

    post {
        always {
            echo 'Publishing Test Reports'
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
        }

        success {
            echo '✅ Build & Tests Successful'
        }

        failure {
            echo '❌ Tests Failed – Check Logs'
        }
    }
}
