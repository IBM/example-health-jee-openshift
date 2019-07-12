#!/bin/bash
oc create secret generic db-secrets                 \
    --from-literal=db_username=admin                \
    --from-literal=db_password=BXGCYZWXFMSIFTHD     \
    --from-literal=db_host=url   \
    --from-literal=db_port=23411 \
    --from-literal=db_name=ex
