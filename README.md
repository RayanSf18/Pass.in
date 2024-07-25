<h1 align="center" style="font-weight: bold;">Pass.in</h1>

<p align="center">
 <a href="#functionalities">Functionalities</a> • 
 <a href="#technologies">Technologies</a> • 
 <a href="#getting-started">Getting Started</a> • 
 <a href="#api-endpoints">API Endpoints</a> •
 <a href="#developer">Developer</a> •
 <a href="#contribute">Contribute</a>
</p>

<p align="center">
    <b>Pass.in is a robust application for managing participants at face-to-face events. This tool offers a series of functionalities designed to facilitate the organization and participation in events, ensuring a smooth and efficient experience for both organizers and participants.</b>
</p>

<h2 id="functionalities">Functionalities</h2>

- **Event registration:** Organizers can register new events, specifying details such as title, description and maximum number of participants allowed. Each event is identified by a unique slug generated automatically.
- **Public Registration Page:** After registration, each event has a public page where participants can register. This page is accessible via the event slug.
- **Participant management:** Participants can register for events by providing their personal details. The system ensures that there are no duplicate registrations through email verification.
- **Issuing Credentials:** Registered participants can issue a credential for check-in on the day of the event. This credential is essential for validating entry.
- **Check-In at the Event:** On the day of the event, the system allows participants to check-in by scanning the credentials issued. This process is quick and guarantees security and organization at the entrance to the event.
- **Participant monitoring:** Organizers can view the list of participants registered for each event, as well as the total number of participants.
<h2 id="technologies">Technologies</h2>

- Java 17
- Postgres
- Spring Framework 3.3.2
- Spring Web 
- Spring Data JPA
- Flyway
- Swagger 2.5.0
- Apache Maven 3.3.2
- Lombok
- Spring Validation I/O
- GIT 2.34.1
- Docker
- Docker Compose
- ProblemDetail

<h2 id="getting-started">🚀 Getting started</h2>

This section guides you through running the project locally.

<h3>Pre-requisites</h3>

Before you begin, ensure you have the following software installed:

* Java Development Kit (JDK) -  https://www.oracle.com/java/technologies/downloads/
* Maven - https://maven.apache.org/download.cgi
* Docker - https://www.docker.com/products/docker-desktop/
* Docker compose - https://docs.docker.com/compose/install/
* Git - https://git-scm.com/

**Optional:**
* IDE (Integrated Development Environment) - (e.g., IntelliJ IDEA, Eclipse)
* Client HTTP - (Postman, Insominia, Bruno)

<h3>Running the Project</h3>

1.  **Clone the Repository:**
```
git clone git@github.com:RayanSf18/Pass.in.git
```
2. **Navigate to the Project Directory:**
```
cd passin
```
3. **Run Postgres on Docker compose:**
```
cd/docker

docker-compose up -d
```
4. **Start the Application:**
```
mvn spring-boot:run
```
5. **server of application:**
```
http://localhost:8080/
```

<h2 id="api-endpoints">API Endpoints</h2>

<p>View endpoint results in:  <a href="http://localhost:8080/swagger-ui/index.html#/">Swagger</a></p>

| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>POST /events</kbd>     | This endpoint register a new event with the provided details.
| <kbd>POST /events/{slug}/participants</kbd>     | This endpoint is used to add a new participant to a specific event.
| <kbd>GET /events/{slug}</kbd>     | This endpoint is used to fetch all the important details of the event.
| <kbd>GET /events/{slug}/participants</kbd>     | This endpoint is used to search for all participants of a specific event.
| <kbd>POST /participants/{participantId}/check-in</kbd>     | This endpoint is used to check in the participant to the specific event.
| <kbd>GET /participants/{participantId}/badge</kbd>     | This endpoint is used to fetch the participant's badge details.

<h2 id="developer">👨‍💻 Developer</h2>
<table>
  <tr>
    <td align="center">
      <a href="#">
        <img src="https://avatars.githubusercontent.com/u/127986772?v=4" width="100px;" alt="Rayan Silva Profile Picture"/><br>
        <sub>
          <b>Rayan silva</b>
        </sub>
      </a>
    </td>
  </tr>
</table>

<h2 id="contribute">🤝 Contribute</h2>

1. Fork the repository.
2. Create a new branch (git checkout -b feature/AmazingFeature).
3. Make your changes.
4. Commit your changes (git commit -m 'Add some AmazingFeature').
5. Push to the branch (git push origin feature/AmazingFeature).
6. Open a pull request.

<h3>Documentations that might help</h3>

[📝 How to create a Pull Request](https://www.atlassian.com/br/git/tutorials/making-a-pull-request)

[💾 Commit pattern](https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716)

Enjoy coding! 😄
