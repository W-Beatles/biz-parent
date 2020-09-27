#!/bin/bash
set -e

# Init DB
for file in $(find /sql -name "*.sql" -exec ls {} \; | grep -v postgres | sort | tr ' ' '|' | tr '\n' ' ')
do
    file=$(echo "${file}" | tr '|' ' ')
    printf "Applying sql file: %s\n" "${file}"
    mysql -uroot -p"${SLAVES_MYSQL_ROOT_PASSWORD}"  < "${file}"
done

# Waiting for Master available...
until mysql -h"mysql-master" -u"root" -p"${MASTER_MYSQL_ROOT_PASSWORD}" -e ";"; do
   echo "mysql-master is unavailable, waiting..."
   sleep 3
done

# Start slave and show slave status
LOG_FILE=$(eval "mysql --host mysql-master -uroot -p${MASTER_MYSQL_ROOT_PASSWORD} -e 'show master status \G' | grep File | sed -n -e 's/^.*: //p'")
LOG_POS=$(eval "mysql --host mysql-master -uroot -p${MASTER_MYSQL_ROOT_PASSWORD} -e 'show master status \G' | grep Position | sed -n -e 's/^.*: //p'")

mysql -u root -p"${SLAVES_MYSQL_ROOT_PASSWORD}" \
-e "CHANGE MASTER TO MASTER_HOST='mysql-master', master_port=3306, \
MASTER_USER='${MYSQL_REPLICATION_USER}', \
MASTER_PASSWORD='${MYSQL_REPLICATION_PASSWORD}', \
MASTER_LOG_FILE='${LOG_FILE}', \
MASTER_LOG_POS=${LOG_POS};"

mysql -u root -p"${SLAVES_MYSQL_ROOT_PASSWORD}" \
-e "flush privileges; start slave; show slave status\G;"
