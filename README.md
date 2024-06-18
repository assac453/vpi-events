# Project README

## Overview

This project comprises a server-based application built with Open Server Panel, PHP, and Laravel 10, accompanied by a mobile application developed using Kotlin and Jetpack Compose. The application is designed to facilitate event registration via QR code scanning, implement a point reward system, and send notifications through Firebase.

## Table of Contents
1. [Project Structure](#project-structure)
2. [Installation](#installation)
3. [Usage](#usage)
4. [Features](#features)
5. [Contributing](#contributing)
6. [License](#license)

## Project Structure

### Server Application
- **Framework:** Laravel 10
- **Language:** PHP
- **Environment:** Open Server Panel
- **Database:** MySQL

### Mobile Application
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose

## Installation

### Server Application
1. Clone the repository:
    ```bash
    git clone https://github.com/assac453/vpi-events
    ```
2. Navigate to the project directory:
    ```bash
    cd server-app
    ```
3. Install dependencies:
    ```bash
    composer install
    ```
4. Configure the environment:
    ```bash
    cp .env.example .env
    php artisan key:generate
    ```
5. Update the `.env` file with your database credentials and other settings.
6. Run the database migrations:
    ```bash
    php artisan migrate
    ```
7. Start the Open Server Panel and ensure the server is running.

### Mobile Application
1. Clone the repository:
    ```bash
    git clone https://github.com/assac453/vpi-events
    ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Configure Firebase in your project by adding the `google-services.json` file to the `app` directory.

## Usage

### Server Application
1. Start the Laravel development server:
    ```bash
    php artisan serve
    ```
2. Access the application at `http://localhost:80`.

### Mobile Application
1. Build and run the application on an Android device or emulator.

## Features

### Event Registration
- Scan QR codes for event registration.
- Store and manage participant data.

### Point Reward System
- Allocate points to participants based on their activity.
- Track and display points for each user.

### Notifications
- Send real-time notifications using Firebase Cloud Messaging.
- Notify participants about events, updates, and points earned.

## Contributing

1. Fork the repository.
2. Create a new branch:
    ```bash
    git checkout -b feature-name
    ```
3. Make your changes and commit them:
    ```bash
    git commit -m "Add feature-name"
    ```
4. Push to the branch:
    ```bash
    git push origin feature-name
    ```
5. Open a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
