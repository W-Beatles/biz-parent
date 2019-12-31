echo on & color 0A
setlocal enabledelayedexpansion

if "%1"=="" goto BLANK
if "%2"=="" goto BLANK

set projectName=%1
set packageName=%2
set archetypeVersion=LATEST

::mvn dependency:copy -Dartifact=cn.waynechu:biz-spring-cloud-archetype:%archetypeVersion% -Dmdep.stripVerison=true & echo Y | mvn archetype:generate -DarchetypeCatalog=local -DarchetypeGroupId=cn.waynechu -DarchetypeArtifactId=biz-spring-cloud-archetype -DarchetypeVersion=%archetypeVersion% -DgroupId=com.waynechu -DartifactId=%projectName% -Dversion=0.0.1-SNAPSHOT -Dpackage=com.waynechu.%packageName% & rd/s/q ${project.basedir} & rd/s/q %projectName%\.idea & ren %projectName% biz-spring-cloud-api-%projectName%

echo Y | mvn archetype:generate ^
    -DarchetypeCatalog=local ^
    -DarchetypeGroupId=cn.waynechu ^
    -DarchetypeArtifactId=biz-spring-cloud-archetype ^
    -DarchetypeVersion=%archetypeVersion% ^
    -DgroupId=com.waynechu ^
    -DartifactId=%projectName% ^
    -Dversion=0.0.1-SNAPSHOT ^
    -Dpackage=com.waynechu.%packageName% ^
    & ren %projectName% biz-spring-cloud-api-%projectName%

color