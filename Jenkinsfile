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
        withSonarQubeEnv() {
          bat 'gradle sonarqube'
        }

      }
    }

  }
}
