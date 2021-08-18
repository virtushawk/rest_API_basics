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
        withSonarQubeEnv(credentialsId: 'Secret text', installationName: '9.0.1')
        bat 'gradle sonarqube'
      }
    }

  }
}