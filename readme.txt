Database tables and table data are stored in "create_alter_insert_update.sql".

Queries, triggers, stored procedures and user creation statements can be found 
in "queries_triggers_procedures.sql".

It is important that triggers for questions A.4.b, A.4.c are created after the
insertion of all additional data needed for part B (as triggers for logging user
activity will not recognise root user as a user of the erecruitment platform 
[no entry for 'root' in table 'user']);

User accounts should also be created before triggers for questions A.4.b and A.4.c.

Sometimes user account creation will not grant requested privileges to new user
because of a MySQL bug. In case this happens, privileges need to be flushed both 
after user creation and after privilege grant statement.
