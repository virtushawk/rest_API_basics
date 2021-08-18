pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        bat 'gradle build'
      }
    }

    stage('SonarQube') {
      steps {
        withSonarQubeEnv(credentialsId: '44e0cc92-2f16-4ddd-a502-ca5dffaf118c', installationName: '9.0.1')
        bat 'gradle sonarqube'
      }
    }

  }
}