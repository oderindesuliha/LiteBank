# Environment Variables Setup

To run this application securely, you need to set up environment variables instead of hardcoding sensitive information in configuration files.

## Required Environment Variables

### Database Configuration
- `DB_URL` - Database connection URL (default: jdbc:postgresql://localhost:5432/joblync)
- `DB_USERNAME` - Database username (default: joblync)
- `DB_PASSWORD` - Database password (default: joblync)

### JWT Configuration
- `JWT_SECRET` - Secret key for JWT token generation
- `JWT_EXPIRATION` - Token expiration time in milliseconds (default: 86400000)
- `JWT_ALGORITHM` - Algorithm for JWT (default: HS512)
- `JWT_ISSUER` - Token issuer (default: JobLync)
- `JWT_AUDIENCE` - Token audience (default: JobLyncUsers)

### Email Configuration
- `MAIL_HOST` - SMTP server host (default: smtp.gmail.com)
- `MAIL_PORT` - SMTP server port (default: 587)
- `MAIL_USERNAME` - Email account username
- `MAIL_PASSWORD` - Email account password or app-specific password

## Setting Environment Variables

### Windows (Command Prompt)
```cmd
set DB_URL=jdbc:postgresql://localhost:5432/joblync
set DB_USERNAME=joblync
set DB_PASSWORD=your_password
set JWT_SECRET=your_secure_jwt_secret
set MAIL_USERNAME=your_email@gmail.com
set MAIL_PASSWORD=your_app_password
```

### Windows (PowerShell)
```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/joblync"
$env:DB_USERNAME="joblync"
$env:DB_PASSWORD="your_password"
$env:JWT_SECRET="your_secure_jwt_secret"
$env:MAIL_USERNAME="your_email@gmail.com"
$env:MAIL_PASSWORD="your_app_password"
```

### Linux/macOS
```bash
export DB_URL=jdbc:postgresql://localhost:5432/joblync
export DB_USERNAME=joblync
export DB_PASSWORD=your_password
export JWT_SECRET=your_secure_jwt_secret
export MAIL_USERNAME=your_email@gmail.com
export MAIL_PASSWORD=your_app_password
```

## Using a .env File

You can also create a `.env` file in the project root directory with the following content:

```env
DB_URL=jdbc:postgresql://localhost:5432/joblync
DB_USERNAME=joblync
DB_PASSWORD=your_password
JWT_SECRET=your_secure_jwt_secret
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password
```

Note: The `.env` file is included in `.gitignore` and will not be committed to the repository.

## Gmail App Passwords

If using Gmail, you should use an App Password instead of your regular password:
1. Enable 2-Factor Authentication on your Google account
2. Go to Google Account settings
3. Navigate to Security > App passwords
4. Generate a new app password for "Mail"
5. Use this app password as your `MAIL_PASSWORD`

## Security Best Practices

1. Never commit sensitive credentials to version control
2. Use strong, randomly generated secrets for JWT
3. Use app-specific passwords for email services
4. Regularly rotate secrets and passwords
5. Use environment variables or secure vaults for configuration