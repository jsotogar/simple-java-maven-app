job('Java Maven App DSL') {
    description('Java Maven App DSL for Jenkins training')
    scm {
        git('https://github.com/macloujulian/simple-java-maven-app.git', 'main') { node ->
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
          echo "Deliver: Deploying app" 
          java -jar "/var/jenkins_home/workspace/Java Maven App DSL/target/my-app-1.0-SNAPSHOT.jar"
        ''')  
    }
    publishers {
        archiveArtifacts('target/*.jar')
        archiveJunit('target/surefire-reports/*.xml')
    }
}
