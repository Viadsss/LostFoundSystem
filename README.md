
## 🚀 Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- **Java Development Kit (JDK)** (version 8 or higher) [Download JDK](https://www.oracle.com/java/technologies/downloads/#jdk23-windows)
- **Maven** (for building the application) [Download Maven](https://maven.apache.org/download.cgi?.)
- **MySQL** (for the database) [Download MySQL](https://dev.mysql.com/downloads/installer/)
- **Git** (to clone the repository) [Download Git](https://git-scm.com/downloads)

---

### 1. Clone the Repository

Clone the repository to your local machine:

```bash
git clone https://github.com/yourusername/your-repository.git
cd your-repository
```

---

### 2. Set Up Environment Variables

Before running the application, **copy** the `.env_example` file to `.env`:

- **Linux/macOS (Bash)**:
  ```bash
  cp .env_example .env
  ```

- **Windows (CMD)**:
  ```cmd
  copy .env_example .env
  ```

Then, open the `.env` file and update the placeholders with your actual values (e.g., database credentials, API keys).

---

### 3. Set Up Database

Run the `init.sql` script to set up the database:

- **For PowerShell**:
  ```powershell
  Get-Content .\init.sql | mysql -u root -p
  ```

- **For CMD, Bash, or Git Bash**:
  ```bash
  mysql -u root -p < init.sql
  ```

---

### 4. Run the Application

Compile and run the application in your IDE (e.g., NetBeans or VS Code).

## 🤝 Contributing

### For Collaborators:

If you're a collaborator on this repository, follow these steps to contribute:

1. **Clone the Repository** (if you haven’t already):
   ```bash
   git clone https://github.com/yourusername/your-repository.git
   cd your-repository
   ```

2. **Pull the Latest Changes**:
   Make sure your `main` branch is up to date:
   ```bash
   git checkout main
   git pull origin main
   ```

3. **Make Changes**:
   Make your changes directly to the `main` branch.

4. **Commit Changes**:
   After making changes, commit them:
   ```bash
   git add .
   git commit -m "feat: add new feature description"
   ```
    **Commit Message Guidelines:**
    - **feat**: For new features (e.g., `feat: add user authentication`).
    - **fix**: For bug fixes (e.g., `fix: resolve login error`).
    - **refactor**: For code refactoring (e.g., `refactor: optimize database queries`).
    - **docs**: For documentation updates (e.g., `docs: update README with setup instructions`).

5. **Push Changes**:
   Push your changes directly to the `main` branch:
   ```bash
   git push origin main
   ```

### Note for Non-Collaborators:
If you are not a collaborator on this repository, please **fork** the repository, make your changes, and create a **Pull Request**. Your changes will be reviewed and merged by one of the collaborators.