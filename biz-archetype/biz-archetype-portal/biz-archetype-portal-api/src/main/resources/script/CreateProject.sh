#!/bin/bash
ARCHETYPE_VERSION=LATEST

generate() {
  echo Y |
    mvn archetype:generate \
      -DarchetypeCatalog=local \
      -DarchetypeGroupId=cn.waynechu \
      -DarchetypeArtifactId=biz-archetype-template-service \
      -DarchetypeVersion="$ARCHETYPE_VERSION" \
      -DgroupId=cn.waynechu \
      -DartifactId="$PROJECT_NAME" \
      -Dversion=0.1.0-SNAPSHOT \
      -Dpackage=cn.waynechu."$PACKAGE_NAME"
}

show_usage() {
  printf "usage: CreateProject.sh [-n PROJECT_NAME] [-p PACKAGE_NAME] -v ARCHETYPE_VERSION\n"
  printf "\t-n PROJECT_NAME the name of project\n"
  printf "\t-p PACKAGE_NAME the name of package\n"
  printf "\t-v ARCHETYPE_VERSION the version of archetype, default version is LATEST\n"
  printf ""
}

while getopts 'hn:p:v:d:' flag; do
  case "${flag}" in
  h)
    show_usage
    exit
    ;;
  n) PROJECT_NAME=${OPTARG} ;;
  p) PACKAGE_NAME=${OPTARG} ;;
  v) ARCHETYPE_VERSION=${OPTARG} ;;
  *)
    show_usage
    exit
    ;;
  esac
done

if [ -z "$PROJECT_NAME" ]; then
  show_usage
  exit 1
fi

if [ -z "$PACKAGE_NAME" ]; then
  show_usage
  exit 1
fi

generate
