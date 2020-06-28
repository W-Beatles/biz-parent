echo on
setlocal enabledelayedexpansion

if "%1"=="" goto BLANK
if "%2"=="" goto BLANK

set PROJECT_NAME=%1
set PACKAGE_NAME=%2
set ARCHETYPE_VERSION=LATEST

echo Y | mvn archetype:generate ^
    -DarchetypeCatalog=local ^
    -DarchetypeGroupId=cn.waynechu ^
    -DarchetypeArtifactId=biz-archetype-template-service ^
    -DarchetypeVersion=%ARCHETYPE_VERSION% ^
    -DgroupId=cn.waynechu ^
    -DartifactId=%PROJECT_NAME% ^
    -Dversion=0.1.0-SNAPSHOT ^
    -Dpackage=cn.waynechu.%PACKAGE_NAME%