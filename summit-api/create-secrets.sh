#!/bin/bash
oc create secret generic db-secrets    \
    --from-literal=db_username=admin        \
    --from-literal=db_password=       \
    --from-literal=db_host="jdbc:mysql://$HOST:$PORT/$DB?sessionVariables=sql_mode=''"
