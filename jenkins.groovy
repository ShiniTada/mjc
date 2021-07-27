#!groovy
properties([disableConcurrentBuilds()])

pipeline {
    agent any

    stages {
        stage("first")
        steps {
            script {
                bat ''
            }
        }
    }
}
