#**讨论社区**

##**资料**
[es 社区]{https://elasticsearch.cn/explore/}


##**工具**

[bootstrap](https://v3.bootcss.com/)

[spring 文档](https://spring.io/guides)

[Git](https://git-scm.com/download)

[Visual Paradiam](https://www.visual-paradigm.com)

[flyway] (https://flywaydb.org/getstarted/firststeps/maven)

[lombox] (https://projectlombok.org/setup/maven)
##**脚本**
`sql
CREATE CACHED TABLE USER(
    "ID" INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    "ACCOUNT_ID" VARCHAR(100),
    "NAME" VARCHAR(50),
    "TOKEN" CHAR(36),
    "GMT_CREATE" BIGINT,
    "GMT_MODIFIED" BIGINT
)
`

```bash
    mvn flyway:migrate
    mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate


    git status
    git add .
    git commit -m ""
    git push
    /
    git add .
    git status
    git commit --amend --no-edit
```
