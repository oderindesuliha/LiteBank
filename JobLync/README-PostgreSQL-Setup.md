# PostgreSQL Setup Guide for JobLync Application

This guide will help you set up PostgreSQL for the JobLync application.

## Prerequisites

- PostgreSQL installed on your system
- Basic knowledge of SQL commands
- Access to a terminal or command prompt

## Step 1: Install PostgreSQL

If you haven't installed PostgreSQL yet, follow these steps:

### Windows

1. Download the PostgreSQL installer from the [official website](https://www.postgresql.org/download/windows/)
2. Run the installer and follow the installation wizard
3. Remember the password you set for the postgres user during installation
4. Complete the installation with the default options

### macOS

Using Homebrew:
```bash
brew install postgresql
brew services start postgresql
```

### Linux (Ubuntu/Debian)

```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

## Step 2: Verify PostgreSQL Installation

Ensure PostgreSQL is running:

### Windows
```
# Check if PostgreSQL service is running
services.msc
# Look for "postgresql-x64-xx" service and make sure it's running
```

### macOS/Linux
```bash
sudo systemctl status postgresql
# or
pg_isready
```

## Step 3: Create Database and User

1. Connect to PostgreSQL as the postgres user:

   ### Windows
   ```
   # Open Command Prompt as Administrator
   psql -U postgres
   ```

   ### macOS/Linux
   ```bash
   sudo -u postgres psql
   ```

2. Create the database and user for JobLync:

   ```sql
   -- Create the database
   CREATE DATABASE joblync;
   
   -- Create the user with password
   CREATE USER joblync WITH PASSWORD 'joblync';
   
   -- Grant privileges to the user
   GRANT ALL PRIVILEGES ON DATABASE joblync TO joblync;
   
   -- Connect to the joblync database
   \c joblync
   
   -- Grant schema privileges
   GRANT ALL ON SCHEMA public TO joblync;
   
   -- Create test database for running tests
   CREATE DATABASE joblync_test;
   GRANT ALL PRIVILEGES ON DATABASE joblync_test TO joblync;
   \c joblync_test
   GRANT ALL ON SCHEMA public TO joblync;
   ```

3. Exit PostgreSQL:
   ```
   \q
   ```

## Step 4: Configure Application Properties

The JobLync application is already configured to connect to PostgreSQL with the following settings in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/joblync?connectTimeout=5&socketTimeout=30&tcpKeepAlive=true&ssl=false
spring.datasource.username=joblync
spring.datasource.password=joblync
spring.datasource.driver-class-name=org.postgresql.Driver
```

If you created the database and user with different names or passwords, update these settings accordingly.

## Step 5: Configure Email Settings

The JobLync application includes email functionality for user registration, password resets, and notifications. By default, the application is configured to use console output for email during development.

For production use, you can configure the application to use real SMTP providers:

### Gmail SMTP Configuration
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### SendGrid SMTP Configuration
```properties
spring.mail.host=smtp.sendgrid.net
spring.mail.port=587
spring.mail.username=apikey
spring.mail.password=your-sendgrid-api-key
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### Twilio SendGrid Integration
If you prefer to use Twilio SendGrid's Java library directly instead of SMTP, you can add the SendGrid dependency to your pom.xml:

```xml
<dependency>
    <groupId>com.sendgrid</groupId>
    <artifactId>sendgrid-java</artifactId>
    <version>4.9.3</version>
</dependency>
```

Then create a custom email service that uses the SendGrid API instead of Spring's JavaMailSender.

## Step 6: JWT Authentication

The JobLync application uses JWT (JSON Web Tokens) for authentication. The JWT configuration is already set up in the application.properties file:

```properties
# JWT Configuration
jwt.secret=mySecretKeyForJobLyncTalentManagementSystemWhichIsVerySecureAndLongEnough
jwt.expiration=86400000
```

For production, you should change the JWT secret to a more secure random string.

JWT authentication works as follows:
1. Users register via POST /api/auth/register
2. Users login via POST /api/auth/login to receive a JWT token
3. For subsequent requests, include the JWT token in the Authorization header as "Bearer {token}"

## Step 7: Enhanced Employee Management Features

The JobLync application now includes comprehensive employee management features:

### Employee Information Management
- Track comprehensive employee details including personal information, employment details, and compensation
- Manage employee hierarchy with manager-subordinate relationships
- Update employee roles and departments
- Track employment status (Active, Inactive, Terminated, On Leave)

### Internal Job Postings
- Employees can apply for internal positions to advance their careers
- Separate endpoints for internal job postings: `/api/internal-job-postings`
- Filter by department, experience level, and open status

### Performance Reviews
- Track employee performance with regular reviews
- Support for different review types (annual, quarterly, peer, self)
- Endpoints: `/api/performance-reviews`

### Career Development Plans
- Create individual development plans with managers
- Track goals, required skills, and training activities
- Endpoints: `/api/career-development-plans`

### Succession Planning
- Identify and develop future leaders
- Track readiness levels for critical positions
- Endpoints: `/api/succession-plans`

### Skills Management
- Maintain a comprehensive skills inventory
- Track employee proficiency levels
- Identify skill gaps and development needs
- Endpoints: `/api/skills` and `/api/user-skills`

### Learning Management
- Integrate with external learning platforms
- Track employee learning progress
- Link learning activities to skill development
- Endpoints: `/api/learning-modules` and `/api/user-learning-progress`

### Analytics Dashboard
- View key HR metrics and statistics
- Monitor employee distribution by role and department
- Track recent hires and other key metrics
- Endpoints: `/api/dashboard`

## Step 8: Test the Connection

1. Start the JobLync application:
   ```bash
   # Navigate to the project directory
   cd path/to/JobLync
   
   # Run the application using Maven
   mvn spring-boot:run
   ```

2. Check the application logs for successful database connection messages.

3. If you encounter any errors:
   - Verify PostgreSQL is running
   - Check that the database and user exist with the correct permissions
   - Ensure the credentials in application.properties match what you created
   - Check firewall settings if connecting to a remote database

## Troubleshooting

### Connection Refused
- Ensure PostgreSQL is running
- Check if PostgreSQL is listening on the default port (5432)
- Verify firewall settings

### Authentication Failed
- Verify the username and password in application.properties
- Check PostgreSQL's pg_hba.conf file for authentication settings

### Database Does Not Exist
- Connect to PostgreSQL and create the database manually
- Check for typos in the database name

## Additional Resources

- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Spring Boot Database Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/data.html#data.sql.datasource)
- [Spring Boot Email Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.email)
- [JWT.io](https://jwt.io/) - For debugging JWT tokens