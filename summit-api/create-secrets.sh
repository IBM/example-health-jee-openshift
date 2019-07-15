#!/bin/bash
oc create secret generic db-secrets    \
    --from-literal=db_username=admin        \
    --from-literal=db_password=7f8ba0d975dcfe032c94fc8d8ce154192edafc1fcabd82a2353       \
    --from-literal=db_host=sl-us-south-1-portal.51.dblayer.com  \
    --from-literal=db_port=23411    \
    --from-literal=db_name=ex
