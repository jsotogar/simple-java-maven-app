job('Java Maven App DSL 2') {
    description('Java Maven DSL App for Jenkins training')
    scm {
        git('https://github.com/jsotogar/simple-java-maven-app.git', 'main') { node ->
            node / gitConfigName('jsotogar')
            node / gitConfigEmail('jsotogar@gmail.com')
        }
    }
    steps {
        maven {
          mavenInstallation('mavenjenkins')
          goals('-B -DskipTests clean package')
        }
        maven {
          mavenInstallation('mavenjenkins')
          goals('test')
        }
        shell('''
          echo "Delivery: Deploying app" 
          java -jar "/var/jenkins_home/workspace/Java Maven App DSL 2/target/my-app-1.0-SNAPSHOT.jar"
        ''')  
    }
    publishers {
        archiveArtifacts('target/*.jar')
        archiveJunit('target/surefire-reports/*.xml')
    }
}
