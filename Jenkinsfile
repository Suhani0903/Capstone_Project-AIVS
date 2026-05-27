pipeline {
    agent any

    tools {
        jdk 'JDK17'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/Suhani0903/Capstone_Project-AIVS.git'
            }
        }

        stage('Clean Build & Run TestNG Tests') {
            steps {
                bat 'mvn clean test'
            }
        }

        stage('Run JMeter Performance Tests') {
            steps {
                bat '''
                if exist report rmdir /s /q report
                if exist results.jtl del results.jtl

                jmeter -n ^
                -t src\\test\\resources\\jmeter\\test-plans\\notes_load_test.jmx ^
                -l results.jtl ^
                -e -o report
                '''
            }
        }

        stage('Allure Report Generation Check') {
            steps {
                bat '''
                echo Checking Allure Results Folder
                dir target\\allure-results
                '''
            }
        }
    }

    post {

        always {

            junit '**/target/surefire-reports/*.xml'

            archiveArtifacts artifacts: '''
                target/**,
                test-output/**,
                report/**,
                results.jtl,
                screenshots/**
            ''', fingerprint: true
        }

        success {
            echo 'BUILD SUCCESS  All tests passed'
        }

        failure {
            echo 'BUILD FAILED  Check logs'
        }
    }
}
