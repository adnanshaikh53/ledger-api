CREATE USER root REPLICATION LOGIN CONNECTION LIMIT 1 ENCRYPTED PASSWORD 'root';
GRANT REPLICATION SLAVE ON *.* TO root;



