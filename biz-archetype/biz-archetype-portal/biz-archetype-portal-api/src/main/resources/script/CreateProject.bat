echo on
setlocal enabledelayedexpansion

if "%1"=="" goto BLANK
if "%2"=="" goto BLANK

set ARCHETYPE_ARTIFACT_ID=%1
set ARCHETYPE_VERSION=LATEST
set PROJECT_NAME=%2
set PACKAGE_NAME=%3

echo Y | mvn archetype:generate ^
    -DarchetypeCatalog=local ^
    -DarchetypeGroupId=cn.waynechu ^
    -DarchetypeArtifactId=%ARCHETYPE_ARTIFACT_ID% ^
    -DarchetypeVersion=%ARCHETYPE_VERSION% ^
    -DgroupId=cn.waynechu ^
    -DartifactId=%PROJECT_NAME% ^
    -Dversion=1.0.0-SNAPSHOT ^
    -Dpackage=cn.waynechu.%PACKAGE_NAME%