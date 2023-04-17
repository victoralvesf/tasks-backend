pipeline {
  agent any
  stages {
    stage ('Build Backend') {
      agent {
        docker {
          image 'maven:3.9.1-amazoncorretto-8-debian'
        }
      }
      steps {
        sh 'mvn clean package -DskipTests=true'
      }
    }
    stage ('Unit Tests') {
      agent {
        docker {
          image 'maven:3.9.1-amazoncorretto-8-debian'
        }
      }
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
          sh 'sonar-scanner -Dsonar.token=$SCANNER_TOKEN'
        }
      }
    }
    stage ('Quality Gate') {
      steps {
        withSonarQubeEnv('SONAR_LOCAL') {
          sleep(5)
          timeout(time: 1, unit: 'MINUTES') {
            waitForQualityGate abortPipeline: true
          }
        }
      }
    }
  }
}