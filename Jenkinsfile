 def tomcatWeb = 'C:\\apache-tomcat-9.0.38\\webapps'
 def tomcatBin = 'C:\\apache-tomcat-9.0.38\\bin'
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
        withSonarQubeEnv('9.0.2') {
          bat 'gradle sonarqube'
        }

      }
    }

   stage('Deploy to Tomcat'){
    steps {
     bat " copy controller\\build\\libs\\controller.war \"${tomcatWeb}\\controller.war\""
    }
   }

  }
}
