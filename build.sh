#!/usr/bin/env bash

echo "Step 1: maven build"
echo
mvn -Dmaven.test.skip=true clean install

echo "Step 2: copy files to docker working folder"
echo
cp docker/* target/.

echo "Step 3: docker image build"
echo
mvn -e dockerfile:build

echo "Step 4: login openshift using develper id"
echo
oc login -u=developer -p=developer

echo "Step 5: go to correct project"
echo
oc project myproject

echo "Step 6: delete old projects"
echo
oc delete dc fuzzy-matching-engine
oc delete svc fuzzy-matching-engine
oc delete route fuzzy-matching-engine

echo "step 7: create new template and objects"
echo
oc process -f docker/fuzzy-matching-engine-template.json `cat docker/fuzzy-matching-engine.dat` | oc create -f -

echo "step 8: clean old images"
docker rmi $(docker images -q -f dangling=true)