# Graduate design tutor selection tool

![](https://img.shields.io/badge/language-java-orange.svg) [![](https://img.shields.io/badge/license-MIT-green.svg)](#License)

## Table of Contents

- [Background](#background)
- [Features](#features)
    - [For teachers](#for-teachers)
    - [For students](#for-students)
- [Getting Startted in IDE](#getting-startted-in-ide)
- [Development Environment](#development-environment)
    - [Prerequisites](#prerequisites)
    - [Installation and unsage](#installation-and-unsage)
- [Deployment Environment](#deployment-environment)
    - [Local](#local)
    - [Remote (not yet)](#remote)
- [Maintainers](#maintainers)
- [Contributing](#contributing)
- [License](#license)

## Background

When students are seniors, they need to choose their graduate design tutor to help them complete their graduate design. And the teachers have their rights to determine which students can choose them as tutor. So they can set requirements, and only the students who meet all of the requirements the teacher set can they choose the teacher as their graduate design tutor. And of course, different teachers have different requirements and the maximum of students they can help. 

So the graduate design tutor selection tool is aiming to help students and tutors choose each other conveniently. Further more, this tool will first be tried to be used by Bo Wang, Lecturer in Software Engineering, Northeast Forestry University:school: and his students major in software engineering. 

## Features

### For teachers

- Set their requirements beforehand on the website. 
- Import students information form Excel files. 

### For students

- Submit their information, like GPAs, and directions of graduate design to check whether they can choose this teacher. 

## Getting Startted in IDE

This project hasn't been deployed in remote server yet, but by doing the following steps, you can get startted in your IDE, and then if you find some bugs or want to add some new features, you can open an issue or submit pull requests. :smiley:

### Prerequisites

The following is required to be installed in your machine

- Java 11 or newer
- Git commond line tool
- Your preferable IDE, like Intellij IDEA
- Maven (strongly recomended, it can help you install the package needed in the project)
- MySQL 8.0 or newer

### Installation and unsage

1. Click the 'Clone or download' button on the GitHub repository page, and copy the url of the code repository.

2. Open the Git commond line tool and input the following Git commond:

   ```git
   git clone <the url you just copyed>
   ```

3. Use your perferable IDE to import this project by importing the Maven pom.xml file.

   Take Intellij IDEA as an examle:

   ```
   File -> New -> Project form Exsisted Sourses... -> choose the pom.xml file in the project folder
   ```

4. Use Maven to reimport all the dependences.

5. Modify the database configurations in `application.properties`located in `tutor-selection-tool\src\main\resources\`.

6. Deploy in your local server and launch the project.

7. Open your web browser to enjoy it ~

## Development Environment

- Intellij IDEA - v2020.1 EAP
- OpenJDK - v11.0.6
- Springboot - v2.2.5
- Junit - v5
- Slf4j
- Lombok
- MySQL - v8.0
- Maven - v3.6.1
- Git - v2.24

## Deployment Environment

### Local

- Server: Tomcat v9.0.27
- Database: MySQL - v8.0
- Persistence Layer: JPA
  spring-data-jpa - v2.2.5

### Remote

- Docker - v2019.03

## Maintainers

- [**@Erjian Gao**](https://github.com/ErjianGao) - student in Software Engineering, Northeast Forestry University

## Contributing

Feel free to join in! 
Open an issue or submit pull requests.

## License

This project is licensed under the MIT License - see the [LICENSE](https://gist.github.com/PurpleBooth/LICENSE.md) file for details

[MIT](LICENSE) © Erjian Gao
