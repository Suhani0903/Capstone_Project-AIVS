pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    environment {
        ALLURE_RESULTS = 'allure-results'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/Suhani0903/Capstone_Project-AIVS.git'
            }
        }

        stage('Clean Build') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test -DsuiteXmlFile=testng.xml'
            }
        }

        stage('Generate Allure Report') {
            steps {
                script {
                   
                    if (fileExists("${ALLURE_RESULTS}")) {
                        echo "Allure results found"
                    } else {
                        echo "No Allure results folder found"
                    }
                }
            }
        }
    }

    post {

        always {
      
            junit '**/target/surefire-reports/*.xml'

            archiveArtifacts artifacts: '**/target/**, **/test-output/**, **/allure-report/**', fingerprint: true
        }

        success {
            echo 'BUILD SUCCESS '
        }

        failure {
            echo 'BUILD FAILED '
        }
    }
}
