#!/bin/bash
# Starts the web server assuming "mvn clean install" has completed successfully.

if ! [ -f web/target/user-transactions-web*-SNAPSHOT.jar ]; then
    echo "You must 'mvn clean install' before starting the server." 1>&2
    exit 1
fi

mvn spring-boot:run
