<p align="center">
  <img width="320px" src="http://picbed.erjiangao.com/img/20200624105118.png"/>
</p>

<p align="center">
  <a href="https://www.oracle.com/java/" target="_blank"><img src="https://img.shields.io/badge/language-java-orange.svg"></a>
  <a href="#License"><img src="https://img.shields.io/badge/license-MIT-green.svg"></a>
  <a><img src="https://visitor-badge.glitch.me/badge?page_id=ErjianGao.tutor-selection-tool"></a>
</p>

[前端地址](https://github.com/ErjianGao/tutor-selection-tool-vue)

语言：[English](https://github.com/ErjianGao/tutor-selection-tool) | 中文

## 目录

- [Background](#background)
- [Features](#features)
    - [For teachers](#for-teachers)
    - [For students](#for-students)
- [Getting Started in IDE](#getting-started-in-ide)
- [Development Environment](#development-environment)
    - [Prerequisites](#prerequisites)
    - [Installation and usage](#installation-and-usage)
- [Deployment Environment](#deployment-environment)
    - [Local](#local)
    - [Remote (not yet)](#remote)
- [Maintainers](#maintainers)
- [Contributing](#contributing)
- [License](#license)

## Background

当学生大三时，他们需要选择毕业设计导师来帮助他们完成毕业设计。 教师有权决定哪些学生可以选择他们作为导师。 因此，他们可以设置要求，只有满足老师设置的所有要求的学生才能选择老师作为毕业设计导师。 当然，不同的老师有不同的要求，他们可以设置帮助的最大学生人数。

因此，毕业设计导师选择工具旨在帮助学生和导师方便地相互选择。 东北林业大学软件学院的软件工程讲师Bo Wang和他的软件工程专业的学生将首先尝试使用该工具。

## Features

### For teachers

- 事先在网站上设置他们的要求。
- 从Excel文件导入学生信息。

### For students

- 提交他们的信息（如GPA）和毕业设计方向，以确定他们是否可以选择该老师。

## Getting Started in IDE

该项目尚未部署在远程服务器中，但是通过执行以下步骤，您可以在IDE中开始，如果您发现一些错误或想要添加一些新功能，则可以提交issue或者pull request。:smiley:

### Prerequisites

您的机器中需要安装以下内容

- Java 11 or newer
- Git command line tool
- Your preferable IDE, like Intellij IDEA
- Maven (strongly recommended, it can help you install the package needed in the project)
- MySQL 8.0 or newer

### Installation and usage

1.单击GitHub仓库页面上的“克隆或下载”按钮，然后复制代码存储库的URL。

2.打开Git命令行工具，然后输入以下Git命令：

   ```git
   git clone <the url you just copyed>
   ```

3.通过导入Maven pom.xml文件，使用IDE导入此项目。

    以Intellij IDEA为例：

   ```
   File -> New -> Project form Exsisted Sourses... -> choose the pom.xml file in the project folder
   ```

4.使用Maven重新导入所有依赖项。

5.在`tutor-selection-tool \ src \ main \ resources \`中的` application.properties`中修改数据库配置。

6.在本地服务器中部署并启动项目。

7.打开您的浏览器运行它 ~

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

- [**@Erjian Gao**](https://github.com/ErjianGao) - 东北林业大学软件工程专业学生

## Contributing

随时加入！
提交一个issue或pull request。

## License

该项目已获得MIT许可证的许可，有关详细信息请参见[LICENSE](https://gist.github.com/PurpleBooth/LICENSE.md)文件

[MIT](LICENSE) © Erjian Gao
