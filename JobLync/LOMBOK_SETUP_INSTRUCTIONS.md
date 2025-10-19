# Lombok Setup Instructions for IntelliJ IDEA

To resolve the Lombok-related compilation issues, follow these steps:

## 1. Install Lombok Plugin

1. Open IntelliJ IDEA
2. Go to File → Settings (IntelliJ IDEA → Preferences on macOS)
3. Navigate to Plugins
4. In the Marketplace tab, search for "Lombok"
5. Click "Install" next to the Lombok plugin
6. Restart IntelliJ IDEA when prompted

## 2. Enable Annotation Processing

1. Go to File → Settings (IntelliJ IDEA → Preferences on macOS)
2. Navigate to Build, Execution, Deployment → Compiler → Annotation Processors
3. Check the box for "Enable annotation processing"
4. Click OK
5. Restart IntelliJ IDEA

## 3. Rebuild Your Project

1. Go to Build → Rebuild Project
2. This will recompile your project with Lombok annotation processing enabled

## 4. Verification

After completing these steps, the Lombok-generated methods (getters, setters, etc.) should be properly recognized by IntelliJ IDEA and available during compilation.

If you continue to experience issues, try:
- Invalidating caches: File → Invalidate Caches / Restart → Invalidate and Restart
- Verifying that the lombok dependency is present in your pom.xml (which it is in your project)