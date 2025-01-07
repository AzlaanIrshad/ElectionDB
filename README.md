## How to run this project with Docker

This project is set up to run with Docker. To get started, make sure you have Docker installed on your system. If you don't, you can download it [here](https://www.docker.com/products/docker-desktop).

### Running the project

1. Clone this repository to your local machine.
2. Open a terminal and navigate to the project directory.
3. Run the following command to build the Docker image:

```bash
docker compose up --build
```
The first time you run this command, it will take a few minutes to build the image. Subsequent runs will be faster.

4. Open your browser and navigate to `http://localhost:3000` to view the project.

5. The database is running on port 3306. You can connect to it using your favorite MySQL client.

### Stopping the project

To stop the project, run the following command in the terminal:

```bash
docker compose down
```

You can also find our documentation [here](https://yaadaasuuwii50-semester-3-hbo-ict-onderwijs-stud-a1e6aea9d517c6.dev.hihva.nl/). In the documentation you can find more information about the project and the ethical considerations we made.