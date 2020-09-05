# Spring Boot 공부 정리
## 개발 환경
  - Eclipse 2020
  - Spring Boot 2.3.1
  - STS 4
  - Gradle
  - DB : MySQL
  - MySQL GUI Tool : SQLyog
  - SQL Mapper : MyBatis
  - Cloud Server : GCP
  - API 문서화 : Swagger
  - 자동 배포 : Jenkins
  
## 1. AOP
  - 애플리케이션 전반에서 사용되는 기능을 여러 코드에 쉽게 적용할 수 있도록 도와줌
  - 예를 들어, 로그, 권한 체크, 인증, 예외 처리를 위한 부가 기능 클래스를 만들고 사용할 때
  - OOP에선 필요한 부분에서 만들었던 클래스를 생성, 메소드를 호출하는 방식이어서 중복되는 코드가 매우 많았음
  - AOP는 이런 문제를 해결하기 위해 **부가 기능의 관점에서 핵심 로직의 시작이나 종료시점에만 호출되도록 도와줌** 
  - <AOP 용어>
    - **Aspect** : 공통적으로 적용될 기능. 한 개 이상의 포인트 컷과 어드바이스의 조합으로 생성.
    - **Advice** : 조인포인트에 삽입되어 동작하는 것을 의미. 동작시점에 따라 다섯 종류로 구분됨.
      - **@Before** : 메소드가 실행되기 전에 적용할 어드바이스를 정의
      - **@AfterReturning** : 메소드가 성공적으로 실행된후 결과값을 리턴 후 적용할 어드바이스를 정의
      - **@Afterthrowing** : 메소드에서 예외 발생 시 적용할 어드바이스를 정의. try/catch 구문의 catch와 흡사
      - **@After** : 메소드의 정상적인 수행 여부와 상관없이 무조건 실행되는 어드바이스를 정의. 즉 예외가 발생해도 실행되기 때문에 finally와 비슷함.
      - **@Around** : 메소드의 호출 전, 후 그리고 예외 등 모든 시점에 적용할 수 있는 어드바이스를 정의. 가장 범용적임. 
    - **Joinpoint** : 어드바이스를 적용하는 지점. 스프링에선 항상 메소드 실행 단계만 가능.
    - **Pointcut** : 어드바이스를 적용할 조인포인트를 선별하는 과정이나 그 기능을 정의한 모듈. 정규표현식이나 AspectJ의 문법으로 어떤 조인포인트를 사용할지 결정.
      - **execution** : 가장 대표적이고 강력한 지시자로 접근 제어자, 리턴 타입, 타입 패턴, 메소드, 파라미터 타입, 예외 타입등을 조합해서 가장 정교한 포인트컷을 만듬.
      - **within** : 특정 타입에 속하는 메소드를 포인트컷으로 설정
      - **bean** : 스프링 빈이름의 패턴으로 포인트 컷을 설정
## 2. Log & Transaction

## 3. Interceptor
  - URI를 호출했을 떄 해당 요청의 컨트롤러가 처리되기 전후 작업을 위해 사용된다
  - Filter의 기능과 흡사해보이지만 차이가 있다
    - 필터는 디스패처 서블릿의 앞 단에서 동작
    - 인터셉터는 디스패처 서블릿에서 핸들러 컨트롤러로 가기전에 동작
    - 인터셉터는 스프링 프레임워크에서 지원되는 기능으로, 필터와는 달리 Spring Bean 사용 가능
    - **문자열 인코딩과 같은 웹 전반에 사용되는 기능은 필터로, 클라이언트 요청과 관련 있는 처리는(로그인, 인증, 권한 등) 인터셉터**
## 4. File Up & Download
  - Apache Common Fileupload ( CommonsMultipartResolver )
  - Gradle에 dependency 추가시 Gradle refresh 필요
  - 다운로드를 할 경우 HttpServletResponse로 사용자에게 데이터 전달
  - Apache FileUtils class로 저장 경로 파일을 읽어 byte[]로 변환
  - UTF-8 인코딩 설정 중요
## 5. REST API
  - REST 한줄 설명 : 리소스를 HTTP URI로 잘 표현하고 (명사형), HTTP 메소드로 동작시키는 것. 여기서 리소스는 JSON, XML 등으로 표현 가능.
  - **Spring Boot 2.1 이상은 HiddenHttpMethodFilter가 탑재되어 있는데 이게 default값이 false임**
  - 따라서 application.properties에 **꼭** 이것을 기입할 것.
    - <pre><code> spring.mvc.hiddenmethod.filter.enabled=true </pre></code>
  
## 6. JPA
  - 웹 개발에 있어 공부했던 데이터베이스를 다루는 기술은 다음과 같음
    - JDBC를 직접 사용
    - MyBatis, iBatis 같은 SQL Mapper
    - Hibernate, EclipseLink 같은 JPA 프로바이더
  - JPA가 발전하는 이유
    - JPA는 자바 객체와 테이블 간의 매핑을 처리하는 ORM의 표준, OOP의 객체와 테이블의 구조가 비슷하다는 점에서 시작됨
    - 장점
      - 개발이 편리함 (반복적으로 작성되는 기본적인 CRUD SQL을 작성할 필요가 없음)
      - 특정 데이터베이스에 종속적이지 않아 데이터베이스가 바뀌어도 JPA가 알맞은 쿼리를 생성해줌
      - 유지보수가 쉽다 (테이블이 변경되어도 객체(엔티티)만 수정하면 됨)
    - 단점
      - SQL을 직접 작성하지 않기 떄문에 튜닝에 어려움이 있다
      - 특정 데이터베이스의 기능을 쓸 수 없다 (쓸려고 해도 독립적인 개발이 불가능하고, JPA의 장점을 누리지 못한다)
      - 객체지향적인 설계가 필요하다
  - Spring Data JPA : Spring에서 제공하며 내부적으로 Hibernate를 사용하고 있음. 
    - Repository Interface
      - 이걸 상속받아서 정해진 규칙에 맞게 메소드를 작성하면 된다.
    - application.properties 설정 주의
      - 실제 개발에선 DDL을 false로 해야함 (데이터 삭제될 수 있음)
      - <pre><code> spring.jpa.generate-ddl=false</code></pre>
    - Annotation
      - @EntityScan : 애플리케이션이 실행될 때 basePackages로 지정된 패키지에서 JPA의 @Entity가 설정된 클래스를 검색한다 (여기에 Jsr310JpaConverters를 등록해야 함)
      - <pre><code> @EntityScan(basePackageClasses = {Jsr310Converters.class}, basePackages = {"board"}) </code></pre>
      - @Query : 복잡한 쿼리를 만들어야한다면 이 어노테이션으로 만들 수 있다. 다만 주의점은 FROM 절에 테이블명이 아니라 엔티티명이 들어가야한다. 다음 예문을 참고하자
      - <pre><code> SELECT file FROM BoardFileEntity file WHERE board_idx = :boardIdx </code></pre>


## 7. GCP

## 8. Swagger & 배포
