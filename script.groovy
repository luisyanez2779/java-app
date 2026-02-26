def testApp() {
    echo "testing the application for ${BRANCH_NAME}..."
} 

def buildJar() {
    echo "building the application for ${BRANCH_NAME}..."
    sh 'mvn package'
} 

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t luisyanez27/demo-app:jma-2.0 .'
        sh 'echo $PASS | docker login -u $USER --password-stdin'
        sh 'docker push luisyanez27/demo-app:jma-2.0'
    }
} 

def deployApp() {
    echo "deploying the application for ${BRANCH_NAME}..."
} 

return this
