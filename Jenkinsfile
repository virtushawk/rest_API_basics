 def tomcatWeb = 'C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps'
 def tomcatBin = 'C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\bin'
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
     bat "controller\\build\\libs\\controller.war \"${tomcatWeb}\\controller.war\""
    }
   }
      stage ('Start Tomcat Server') {
       steps {
         sleep(time:5,unit:"SECONDS") 
         bat "${tomcatBin}\\startup.bat"
         sleep(time:100,unit:"SECONDS")
       }
   }

  }
}
