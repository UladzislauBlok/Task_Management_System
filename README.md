**Project Management System**

**Description:**

The Project Management System is designed to efficiently plan, track and control project tasks. The major components of the project include a web application based on Java technologies such as HttpServlet, JSP (JavaServer Pages), and JDBC (Java Database Connectivity).

**Main functions:**

1. *Authorization and Authentication:*

Registration and login for users with different roles (administrator, project manager, project participant).

2. *Project Management:*

Creating new projects with basic parameters (title, description, deadlines).
View the list of active projects.

3. *Tasks Management:*

Adding tasks to projects by specifying priority and due dates

4. *Tasks Tracking:*

Viewing the status of tasks and projects.
Updating task statuses when tasks are completed by their executors.

**Requirements to start the project:**

1. Postgresql 15.2+;
2. Tomcat 10.1.17+;

**Installation and start-up process:**

1. Clone the project to a folder convenient for you. Command: *git clone https://github.com/UladzislauBlok/Task_Management_System.git*
2. Run all sql queries to customize the database. File *createDB_script.SQL*
3. Create a web archive. Command: *./mvnw clean package*
4. Clone the task-system.war file into the tomcat webapps folder
5. Start tomcat. Command *startup.but* (windown)/ *startup.sh* (linux) in the bin directory
6. Tomcat will zip the project. A folder with the same name as the archive will be available in the webapps folder. There in the WEB-INF/classes folder there will be an application.propeties configuration file. In it the project settings are available: data for database connection, number of connections in the connection pool, and the path to the folder with user photos. 
7. Create a folder for storing photos, specify the path to it in application.properties, create a users folder there, save the photo for admin under the name *1_admin* (**png format**).

