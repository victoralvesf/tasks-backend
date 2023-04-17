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
  }
}