<img src="https://github.com/seynax/Profiler/assets/30376290/bdfc3d6c-5022-4389-a544-5bc0c13fce5c" alt="Profiler Logo" width="80" height="80" align="left"/>

<br>

# 📊 Profiler

![Last Commit](https://img.shields.io/github/last-commit/seynax/Profiler?style=for-the-badge)
![Issues](https://img.shields.io/github/issues/seynax/Profiler?style=for-the-badge)
![Forks](https://img.shields.io/github/forks/seynax/Profiler?style=for-the-badge)
![Stars](https://img.shields.io/github/stars/seynax/Profiler?style=for-the-badge)
![PRs](https://img.shields.io/github/issues-pr/seynax/Profiler?style=for-the-badge)

![Build Status - production](https://img.shields.io/github/actions/workflow/status/seynax/Profiler/maven.yml?branch=main&style=for-the-badge)
![Build Status - develop](https://img.shields.io/github/actions/workflow/status/seynax/Profiler/maven.yml?branch=develop&style=for-the-badge)
![License](https://img.shields.io/github/license/seynax/Profiler?style=for-the-badge)
![Java Version](https://img.shields.io/badge/Java-21-007396?style=for-the-badge&logo=java)
![Maven](https://img.shields.io/badge/Maven-3.9.7-C71A36?style=for-the-badge&logo=apache-maven)

## 📚 Overview

**Profiler** is a comprehensive application designed for profiling various metrics in your Java application. It includes
CSV management, visualization using NanoVG, and integrates seamlessly with a variety of libraries to provide detailed
performance insights.

## 🌟 Features - TODO 🛠️

- **CSV Management**: Easily read, write, and manipulate CSV files.
- **NanoVG Viewer**: Render high-quality, vector-based visualizations.

## Developpement feature - TODO 🛠️

- **Branch Naming Enforcement**: Enforce branch naming conventions.
- **Commit Message Formatting**: Ensure consistent commit message formats.
- **Automated Code Quality Checks**: Spotless, Checkstyle, and more.
- **Continuous Integration**: GitHub Actions setup for automated testing and deployment.

## 🚀 Getting Started

### Prerequisites

- Java 21
- Maven 3.9.7

### Installation

1. **Clone the repository**:

    ```sh
    git clone https://github.com/Seynax/profiler.git
    cd profiler
    ```

2. **Install dependencies**:

    ```sh
    mvn clean install
    ```

### Configuration

Add your `settings.xml` for Maven in the root of the project. Example:

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
    <profiles>
        <profile>
            <id>dependency-check</id>
            <properties>
                <dependency-check.nvd.api.key>YOUR_API_KEY_HERE</dependency-check.nvd.api.key>
            </properties>
        </profile>
    </profiles>
    <activeProfiles>
        <activeProfile>dependency-check</activeProfile>
    </activeProfiles>
</settings>
