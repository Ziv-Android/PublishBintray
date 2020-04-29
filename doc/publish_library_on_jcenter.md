# Publish Library On JCenter

## Sign Up
注册bintray账号
```text
//个人账号
https://bintray.com/signup/oss
//企业账号
https://bintray.com/
```

## Get API Key
获取API_Key：点击右上角用户名 -> Edit Profile -> API Key

## Create Repository
创建账号后，存储项目的Maven仓库需要手动创建
Add New Repository -> name: maven, type: maven -> create

得到maven仓库后 -> Add New Package创建项目，也就是publish中的artifactId
此处注意有一个`Version control`必填的参数，这里需要填写**项目的Github地址**，否则后续Add to JCenter无法执行

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
    // jcenter上的路径，一般使用包名
    groupId = 'com.ziv.develop.utils'
    // 项目名称
    artifactId = 'DevelopUtils'
    // 版本号
    publishVersion = '1.0.0'
    // 项目描述
    desc = 'tools for develop'
    // 网站
    website = 'https://github.com/Ziv-Android/PublishBintray'
}
```

## Upload
工程根目录下执行
`./gradlew clean build bintrayUpload -PbintrayUser=ziv-android -PbintrayKey=8755c968760cc6ea9ff5a3f0822520771a34a192 -PdryRun=false`

## Add to JCenter
网页页面点: Add to JCenter -> Send

## How To Use
需要使用的项目中添加
```groovy
implementation 'com.ziv.develop.utils:DevelopUtils:1.0.0'
```

## Thanks
ok，大功告成，至此完成了