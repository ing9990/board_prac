## Spring Boot + JPA 게시판 REST API 구현 

### Base Url: http://localhost:8090/api

<hr/> <br/>


+ ### DTO
  - _**Board Post Dto**_ 
    * **private String username**
    * **private String title**
    + **private String content**
    + **private String password**    
    >  `게시글 삭제를 위해 password를 추가`
  - _**Board Delete Dto**_
    + **private String password**
    > `게시글을 삭제하기 위해 setting한 password를 받음.`

+ ### PRIVATE METHOD
   + **private void update_date(Long id)**  <br/>
   `게시글 좋아요, 싫어요 발생 시 update_at 컬럼을 현재 시간으로 설정.`
+ ### GET METHOD
   1. http://localhost:8090/api <br/>
    ` 게시글 전체 조회`
   2. http://localhost:8090/api/{id} <br/>
     `게시글 ID 조회`
   3. http://localhost:8090/api/search/{token}  <br/>
     `제목으로 조회 token에 "MYS" 입력시 MYSQL 게시글 조회 가능.`
+ ### POST METHOD
   1. http://localhost:8090/api/write <br/>
      `게시글 등록 (정보는 RequestBody로 받음.)`

+ ### PUT METHOD
   1. http://localhost:8090/api/{id}/up <br/>
      `좋아요 기능`
   2. http://localhost:8090/api/{id}/down <br/>
      `싫어요 기능`


+ ### DELETE METHOD
   1. http://localhost:8090/api/{id} <br/>
      `게시글 삭제 기능 (password 불 일치 시 삭제 안됨.)`
  
