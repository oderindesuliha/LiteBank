# JWT Configuration Guide for JobLync Application

This guide explains how to properly configure JWT (JSON Web Tokens) for the JobLync application.

## Current JWT Configuration

The application is configured with the following JWT properties in `application.properties`:

```properties
# JWT Configuration
jwt.secret=5B0970DFF635D80A4E9B2D9E8F4A7C3B1F2E4D7A9C0B3E6F8A1D5C9E2F7A4B8C
jwt.expiration=86400000
jwt.algorithm=HS512
jwt.issuer=JobLync
jwt.audience=JobLyncUsers
```

## Configuration Properties Explained

1. **jwt.secret**: A base64-encoded secret key used to sign JWT tokens. This should be a long, random string for security.
2. **jwt.expiration**: Token expiration time in milliseconds (86400000 = 24 hours)
3. **jwt.algorithm**: The signing algorithm (HS512 = HMAC using SHA-512)
4. **jwt.issuer**: The issuer of the token (your application name)
5. **jwt.audience**: The intended audience for the token

## Generating a Secure JWT Secret

For production environments, you should generate a new secure secret. The application includes a utility class to help with this:

### Using the JwtSecretGenerator Utility

Run the `JwtSecretGenerator` class to generate a new secure secret:

```bash
# Navigate to the project directory
cd path/to/JobLync

# Run the secret generator
mvn exec:java -Dexec.mainClass="org.peejay.joblync.utils.JwtSecretGenerator"
```

Or run it directly from your IDE by executing the `main` method in `JwtSecretGenerator.java`.

The output will be similar to:
```
Generated JWT Secret:
Xgx4aZmV9F8p2Kq3Nw7Rt5Yv8Bc1Ew9Qz6Xs3Ad5Fg2Hj8Kl1Mn4Bv7Cx9Dz6Ew3

Add this to your application.properties file:
jwt.secret=Xgx4aZmV9F8p2Kq3Nw7Rt5Yv8Bc1Ew9Qz6Xs3Ad5Fg2Hj8Kl1Mn4Bv7Cx9Dz6Ew3
```

## Security Best Practices

1. **Never commit secrets to version control**: Add `application.properties` to `.gitignore` if it contains sensitive information
2. **Use different secrets for different environments**: Development, staging, and production should each have unique secrets
3. **Rotate secrets regularly**: Change your JWT secret periodically for enhanced security
4. **Use strong algorithms**: HS512 is recommended for strong security
5. **Set appropriate expiration times**: Balance security with user experience

## Environment-Specific Configuration

### Development
For development, you can use the provided secret in `application.properties`.

### Production
For production, use environment variables or external configuration:

```properties
jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION:86400000}
```

Then set the environment variables:
```bash
export JWT_SECRET="your-very-secure-random-secret-here"
export JWT_EXPIRATION=86400000
```

## Testing JWT Functionality

The application includes unit tests for JWT functionality in `JwtUtilTest.java`. Run these tests to ensure JWT is working correctly:

```bash
# Run all tests
mvn test

# Run only JWT tests
mvn test -Dtest=JwtUtilTest
```

## Troubleshooting

### Common Issues

1. **Invalid signature errors**: Ensure the secret in `application.properties` matches what was used to generate the token
2. **Token expired errors**: Check the expiration time configuration
3. **Algorithm mismatch errors**: Ensure the algorithm in configuration matches what's used in code

### Debugging JWT Tokens

You can decode JWT tokens for debugging purposes using online tools like [jwt.io](https://jwt.io/). However, never paste production tokens into online tools.

## Additional Resources

- [JWT.io](https://jwt.io/) - Official JWT website
- [JJWT Documentation](https://github.com/jwtk/jjwt) - Java JWT library documentation
- [OAuth 2.0 and JWT Best Practices](https://curity.io/resources/oauth/jwt/)