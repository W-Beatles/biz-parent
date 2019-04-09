echo on & color 0A
setlocal enabledelayedexpansion

if "%1"=="" goto BLANK
if "%2"=="" goto BLANK

set ProjectName=%1
set packageName=%2
set archetypeVersion=0.0.1-SNAPSHOT

::mvn dependency:copy -Dartifact=cn.waynechu:app-spring-cloud-archetype:%archetypeVersion% -Dmdep.stripVerison=true & echo Y | mvn archetype:generate -DarchetypeCatalog=local -DarchetypeGroupId=cn.waynechu -DarchetypeArtifactId=app-spring-cloud-archetype -DarchetypeVersion=%archetypeVersion% -DgroupId=com.waynechu -DartifactId=%ProjectName% -Dversion=0.0.1-SNAPSHOT -Dpackage=com.waynechu.%packageName% & rd/s/q ${project.basedir} & rd/s/q %ProjectName%\.idea & ren %ProjectName% app-spring-cloud-api-%ProjectName%

echo Y | mvn archetype:generate ^
    -DarchetypeCatalog=local ^
    -DarchetypeGroupId=cn.waynechu ^
    -DarchetypeArtifactId=app-spring-cloud-archetype ^
    -DarchetypeVersion=%archetypeVersion% ^
    -DgroupId=com.waynechu ^
    -DartifactId=%ProjectName% ^
    -Dversion=0.0.1-SNAPSHOT ^
    -Dpackage=com.waynechu.%packageName% ^
    & rd/s/q ${project.basedir} ^
    & rd/s/q %ProjectName%\.idea ^
    & ren %ProjectName% app-spring-cloud-api-%ProjectName%