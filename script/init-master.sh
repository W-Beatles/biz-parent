#!/bin/bash
set -e
# Create replication user
mysql -u root -p"${MASTER_MYSQL_ROOT_PASSWORD}" \
-e "CREATE USER '${MYSQL_REPLICATION_USER}'@'172.20.0.%' IDENTIFIED WITH mysql_native_password BY '${MYSQL_REPLICATION_PASSWORD}'; \
GRANT REPLICATION SLAVE ON *.* TO '${MYSQL_REPLICATION_USER}'@'172.20.0.%'; \
flush privileges;"