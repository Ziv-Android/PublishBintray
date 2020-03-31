# Publish Library On Jcenter

## Sign Up
注册bintray账号：https://bintray.com/signup/oss

## Get API Key
获取API_Key：点击右上角用户名 -> Edit Profile -> API Key

## Set Project
### Root Gradle
项目根目录build.gradle添加
```groovy
buildscript {
    dependencies {
        classpath 'com.android.tools.build:gradle:3.6.1'

        classpath 'com.novoda:bintray-release:0.9.2'
    }
}
```

### Library Gradle
待发布的Library下的Gradle添加
```groovy
apply plugin: 'com.novoda.bintray-release'

publish {
    // bintray.com用户名
    userOrg = 'ziv-android'
    // jcenter上的路径
    groupId = 'com.ziv.develop.utils'
    // 项目名称
    artifactId = 'develop-tools'
    // 版本号
    publishVersion = '1.0.0'
    // 项目描述
    desc = 'tools for develop'
    // 网站
    website = 'https://github.com/Ziv-Android/PublishBintray'
}
```
 
## Upload
`./gradlew clean build bintrayUpload -PbintrayUser=ziv-android -PbintrayKey=8755c968760cc6ea9ff5a3f0822520771a34a192 -PdryRun=false`
 