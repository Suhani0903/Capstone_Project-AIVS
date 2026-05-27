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
                set JMETER_HOME=%JMETER_HOME%

                if exist jmeter\\html-report rmdir /s /q jmeter\\html-report
                if exist jmeter\\results\\result_01.jtl del jmeter\\results\\result_01.jtl

                jmeter -n ^
                -t "%WORKSPACE%\\jmeter\\test-plans\\notes_load_test.jmx" ^
                -l "%WORKSPACE%\\jmeter\\results\\result_01.jtl" ^
                -e -o "%WORKSPACE%\\jmeter\\html-report\\run1"
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
                jmeter/html-report/**,
                jmeter/results/**,
                screenshots/**
            ''', fingerprint: true
        }

        success {
            echo 'BUILD SUCCESS - All tests passed'
        }

        failure {
            echo 'BUILD FAILED - Check logs'
        }
    }
}
