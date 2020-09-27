#!/bin/bash
set -e

# Init DB
for file in $(find /sql -name "*.sql" -exec ls {} \; | grep -v postgres | sort | tr ' ' '|' | tr '\n' ' ')
do
    file=$(echo "${file}" | tr '|' ' ')
    printf "Applying sql file: %s\n" "${file}"
    mysql -uroot -p"${MASTER_MYSQL_ROOT_PASSWORD}"  < "${file}"
done

# Create replication user
mysql -u root -p"${MASTER_MYSQL_ROOT_PASSWORD}" \
-e "CREATE USER '${MYSQL_REPLICATION_USER}'@'172.20.0.%' IDENTIFIED WITH mysql_native_password BY '${MYSQL_REPLICATION_PASSWORD}'; \
GRANT REPLICATION SLAVE ON *.* TO '${MYSQL_REPLICATION_USER}'@'172.20.0.%'; \
flush privileges;"