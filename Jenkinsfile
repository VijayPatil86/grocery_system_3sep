pipeline {
    agent any
    environment {
        PATH_GIT_REPO="https://github.com/VijayPatil86/grocery_system_3sep.git"
        PATH_MAVEN="E:/softwares/apache-maven/apache-maven-3.9.9/bin/"
    }
    stages {
        stage ('EMPTY_WORKSPACE') {
            steps {
                cleanWs()
            }
        }
        stage ('GIT_CHECKOUT') {
            steps {
                git branch: "main", url:"${PATH_GIT_REPO}"
            }
        }
        stage ('MAVEN_CLEAN') {
            steps{
                bat "${PATH_MAVEN}mvn clean"
            }
        }
        stage ('MAVEN_PACKAGE') {
            steps {
                bat "${PATH_MAVEN}mvn package"
            }
        }
        stage ('DOCKER_BUILD') {
		    steps {
		        bat "docker build . -t grocery"
		    }
		}
		stage ('DOCKER_RUN') {
		    steps {
				bat "docker run -v E:/grocery-prod-logs/:/app -p 9002:9002 grocery"
		    }
		}
    }
}
