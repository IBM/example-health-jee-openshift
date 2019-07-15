#!/bin/bash
oc create secret generic db-secrets    \
    --from-literal=db_username=        \
    --from-literal=db_password=        \
    --from-literal=db_host=            \
    --from-literal=db_port=            \
    --from-literal=db_name=
