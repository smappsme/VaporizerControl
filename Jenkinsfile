node {
 def flavorCombination=''

 stage "checkout"
 checkout scm

 stage 'assemble'
 sh "./gradlew clean assemble${flavorCombination}Release"
 archive 'mobile/build/outputs/apk/*'

 stage 'lint'
 try {
  sh "./gradlew lint${flavorCombination}Release"
 } catch(err) {
  currentBuild.result = FAILURE
 } finally {
  androidLint canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '', unHealthy: ''
 }

 stage 'test'
 sh "./gradlew test${flavorCombination}DebugUnitTest"
 publishHTML(target:[allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'mobile/build/reports/tests/', reportFiles: "*/index.html", reportName: 'UnitTest'])
 
}