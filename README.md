# KCIS-chan

An anonymous imageboard for KCIS (Kaohsiung City International School), inspired by 4chan. Students can post anonymously, share images, and discuss various topics across different boards.

## Features

- **Anonymous Posting** - Post without revealing your identity
- **Multiple Boards** - Dedicated boards for different topics (homework, games, gossip, anime, tech, etc.)
- **Thread & Reply System** - Create threads and reply to existing posts
- **Image Attachments** - Upload and share images with posts
- **Tripcode System** - Optional identity verification using name#password format
- **Admin Panel** - Moderation tools for managing posts and boards
- **IP Rate Limiting** - Spam prevention
- **Hidden Boards** - Special boards only visible to admins

## Tech Stack

- **Backend**: Spring Boot 3.2.5
- **Database**: MariaDB
- **Session Management**: Redis
- **Template Engine**: Thymeleaf
- **Java Version**: 17

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MariaDB 10.x
- Redis server

## Installation

1. **Clone the repository**
```bash
git clone <repository-url>
cd kcischan
```

2. **Set up the database**
```bash
mysql -u root -p < kcischan.sql
```

3. **Configure application properties**

Create `src/main/resources/application.properties`:
```properties
# Database
spring.datasource.url=jdbc:mariadb://localhost:3306/kcischan
spring.datasource.username=kcischan
spring.datasource.password=your_password

# Redis
spring.data.redis.host=localhost
spring.data.redis.port=6379

# File upload
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Tripcode salt (change this!)
tripcode.salt=your_random_salt_here
```

4. **Build the project**
```bash
mvn clean package
```

5. **Run the application**
```bash
java -jar target/kcischan-2.2.1.jar
```

Or use Maven:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Board Structure

The platform includes various boards:
- **/b/** - Random (almost everything permitted)
- **/hw/** - Homework help & notes
- **/g/** - Games
- **/d/** - Deals & trading
- **/r/** - Romantic discussions & confessions
- **/gos/** - Gossip
- **/a/** - Anime & Manga
- **/c/** - Complaints
- **/tech/** - Technology
- **/p/** - Piracy
- **/sup/** - Support & suggestions

Hidden boards (admin only):
- **/hack/** - Hacking
- **/uc/** - Uncensored

## Usage

### Posting
1. Navigate to a board
2. Click "New Thread" or reply to existing thread
3. Enter title, content, and optionally attach an image
4. Submit anonymously

### Tripcode
To use a persistent identity without registration:
```
Name#password
```
Example: `John#mysecret` → displays as `John !Ab12Cd34`

### Admin Access
Navigate to `/admin/login` and use admin credentials to access moderation tools.

## Development

```bash
# Run in development mode with auto-reload
mvn spring-boot:run

# Run tests
mvn test

# Build without tests
mvn clean package -DskipTests
```

## Project Structure

```
src/
├── main/
│   ├── java/com/kcischan/kcischan/
│   │   ├── controller/     # Web controllers
│   │   ├── model/          # JPA entities
│   │   ├── repository/     # Data access layer
│   │   ├── service/        # Business logic
│   │   └── config/         # Configuration classes
│   └── resources/
│       ├── templates/      # Thymeleaf HTML templates
│       └── static/         # CSS, JS, images
```

## Security Features

- IP-based rate limiting to prevent spam
- CAPTCHA for post submission
- Admin authentication for moderation
- Session management via Redis
- XSS protection through content sanitization

## License

Built for KCIS internal use.

## Contributing

This is a school project. Contact the maintainer for contribution guidelines.
