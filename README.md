## Spring Boot + JPA 게시판 REST API 구현 
<br/>

### Base Url: http://localhost:8090/api

<br/>

### Dependency
- _**Spring-boot-starter 2.6.7**_
- _**Spring-boot-starter-data-jpa 2.6.7**_
- **_Spring-boot-starter-jdbc 2.6.7_**
- **_Spring-boot-starter-web 2.6.7_**
- _**Spring-boot-devtools 2.6.7**_
- _**Lombok 1.18.24**_

<hr/> <br/>


+ ### DTO
  - _**Board Post Dto**_ 
    1. **private String username**
    2. **private String title**
    3. **private String content**
    4. **private String password**    
    >  `게시글 삭제를 위해 password를 추가`
  - _**Board Delete Dto**_
    1. **private String password**
    > `게시글을 삭제하기 위해 setting한 password를 받음.`
  - _**Board Edit Dto를**_
    1. **private Long id **
    2. **private String title**
    3. **private String content**
    4. **private String password**
    > `password 일치 시 위 내용으로 수정`

+ ### PRIVATE METHOD
   + **private void update_date(Long id)**  <br/>
   `게시글 좋아요, 싫어요 발생 시 update_at 컬럼을 현재 시간으로 설정.`
+ ### GET METHOD
   1. http://localhost:8090/api <br/>
    ` 게시글 전체 조회`
   2. http://localhost:8090/api/{id} <br/>
     `게시글 ID 조회`
   3. http://localhost:8090/api/search/title/{title}  <br/>
     `제목으로 조회 title에 "MYS" 입력시 MYSQL 게시글 조회 가능.`
   4. http://localhost:8090/api/search/username/{username}  <br/>
      `username 회원이 작성한 게시글 조회 가능.`
   5. http://localhost:8090/api/like/{n}  <br/>
      `좋아요 갯수가 n개인 게시글 조회 기능`
   6. http://localhost:8090/api/unlike/{n}  <br/>
      `싫어요 갯수가 n개인 게시글 조회 기능`
   7. http://localhost:8090/search/{likes}/{unlikes}  <br/>
      `좋아요 갯수가 likes 이상 싫어요 갯수가 unlikes개 이하인 게시글 조회 `
   8. 
+ ### POST METHOD
   1. http://localhost:8090/api/write <br/>
      `게시글 등록 (정보는 RequestBody로 받음.)`

+ ### PUT METHOD
   1. http://localhost:8090/api/{id}/up <br/>
      `좋아요 기능`
   2. http://localhost:8090/api/{id}/down <br/>
      `싫어요 기능`
   3. http://localhost:8090/api  <br/>
      `@RequestBody로 BoardEditDto를 받음`


+ ### DELETE METHOD
   1. http://localhost:8090/api/{id} <br/>
      `게시글 삭제 기능 (password 불 일치 시 삭제 안됨.)`
  
