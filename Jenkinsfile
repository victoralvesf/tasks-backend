pipeline {
  agent {
    docker {
      image 'maven:3.9.1-amazoncorretto-8-debian'
    }
  }
  stages {
    stage ('Build Backend') {
      steps {
        sh 'mvn clean package -DskipTests=true'
      }
    }
    stage ('Unit Tests') {
      steps {
        sh 'mvn test'
      }
    }
    stage ('SonarQube Analysis') {
      agent {
        docker {
          image 'sonarsource/sonar-scanner-cli:latest'
        }
      }
      steps {
        withCredentials([string(credentialsId: 'sonar_scanner_token', variable: 'SCANNER_TOKEN')]) {
          sh 'sonar-scanner -Dsonar.login=$SCANNER_TOKEN'
        }
      }
    }
  }
}