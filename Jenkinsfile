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
      withSonarQubeEnv('9.0.1') {
                bat 'gradle sonarqube'
              }
      }
    }

  }
}
