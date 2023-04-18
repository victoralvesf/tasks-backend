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
      post {
        always {
          junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
        }
      }
    }
    stage ('SonarQube Analysis') {
      agent {
        docker {
          image 'sonarsource/sonar-scanner-cli:latest'
        }
      }
      environment {
        SCANNER_TOKEN = credentials('sonar_scanner_token')
      }
      steps {
        withSonarQubeEnv('SONAR_LOCAL') {
          sh 'sonar-scanner -Dsonar.token=$SCANNER_TOKEN'
        }
      }
    }
    stage ('Quality Gate') {
      steps {
        sleep(5)
        timeout(time: 1, unit: 'MINUTES') {
          waitForQualityGate abortPipeline: true
        }
      }
    }
    stage ('Deploy Backend') {
      agent {
        docker {
          image 'maven:3.9.1-amazoncorretto-8-debian'
        }
      }
      steps {
        deploy adapters: [
          tomcat8(
            credentialsId: 'login_tomcat',
            path: '',
            url: "$PIPELINE_TOMCAT_URL"
          )
        ],
        contextPath: 'tasks-backend',
        onFailure: false,
        war: 'target/tasks-backend.war'
      }
    }
    stage ('Api Test') {
      agent {
        docker {
          image 'maven:3.9.1-amazoncorretto-8-debian'
        }
      }
      steps {
        dir('tasks-api-test') {
          git 'https://github.com/victoralvesf/tasks-api-test'
          sh "mvn test -Dapi.base.url=$BACKEND_URL"
        }
      }
      post {
        always {
          junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
        }
      }
    }
    stage ('Build Frontend') {
      agent {
        docker {
          image 'maven:3.9.1-amazoncorretto-8-debian'
        }
      }
      steps {
        dir('tasks-frontend') {
          git 'https://github.com/victoralvesf/tasks-frontend'
          sh 'mvn clean package'
        }
      }
    }
    stage ('Deploy Frontend') {
      agent {
        docker {
          image 'maven:3.9.1-amazoncorretto-8-debian'
        }
      }
      steps {
        dir('tasks-frontend') {
          deploy adapters: [
            tomcat8(
              credentialsId: 'login_tomcat',
              path: '',
              url: "$PIPELINE_TOMCAT_URL"
            )
          ],
          contextPath: 'tasks',
          onFailure: false,
          war: 'target/tasks.war'
        }
      }
    }
    stage ('E2E Test') {
      agent {
        docker {
          image 'maven:3.9.1-amazoncorretto-8-debian'
        }
      }
      steps {
        dir('functional-test') {
          git 'https://github.com/victoralvesf/tasks-functional-tests'
          sh "mvn test -Dselenium.grid.hub.url=$SELENIUM_HUB_URL -Dselenium.app.url=$FRONTEND_URL"
        }
      }
      post {
        always {
          junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
        }
      }
    }
  }
}