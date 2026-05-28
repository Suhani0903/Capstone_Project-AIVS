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

        stage('Clean Workspace Artifacts') {
            steps {
                bat '''
                echo Cleaning old reports and artifacts...

                if exist target\\allure-results rmdir /s /q target\\allure-results
                if exist target\\surefire-reports rmdir /s /q target\\surefire-reports
                if exist test-output rmdir /s /q test-output

                if exist jmeter\\html-report rmdir /s /q jmeter\\html-report
                if exist jmeter\\results rmdir /s /q jmeter\\results
                if exist jmeter\\jmeter.log del /f /q jmeter\\jmeter.log

                if not exist jmeter\\results mkdir jmeter\\results
                if not exist jmeter\\html-report mkdir jmeter\\html-report
                '''
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
                echo Running JMeter Tests...

                jmeter -n ^
                -t "%WORKSPACE%\\jmeter\\test-plans\\notes_load_test.jmx" ^
                -l "%WORKSPACE%\\jmeter\\results\\result_01.jtl" ^
                -e ^
                -o "%WORKSPACE%\\jmeter\\html-report" ^
                -j "%WORKSPACE%\\jmeter\\jmeter.log"
                '''
            }
        }

        stage('Allure Results Check') {
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
                jmeter/**,
                screenshots/**
            ''', fingerprint: true

            allure([
                includeProperties: false,
                jdk: '',
                results: [[path: 'target/allure-results']]
            ])
        }

        success {
            echo 'BUILD SUCCESS - All tests passed'
        }

        failure {
            echo 'BUILD FAILED - Check logs'
        }
    }
}
