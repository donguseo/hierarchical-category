# Hierarchical Category

계층이 있는 카테고리 시스템을 구현합니다.
Closure Table 패턴을 사용하여 개발하였습니다.
categories 테이블과 category_tree 테이블을 사용하여 계층 구조를 관리합니다.

## 어플리케이션 실행 방법

### 필요 사항

- Java 21 이상
- Gradle 8.x

### 빌드 및 실행

```bash
# 프로젝트 빌드
./gradlew build

# 애플리케이션 실행
./gradlew bootRun
```

애플리케이션은 기본적으로 `http://localhost:8080`에서 실행됩니다.

## Database 명세

### categories

| 컬럼명        | 타입           | 설명         | 제약조건                        |
|------------|--------------|------------|-----------------------------|
| id         | BIGINT       | 카테고리 ID    | PRIMARY KEY, AUTO_INCREMENT |
| name       | VARCHAR(255) | 카테고리 이름    | NOT NULL                    |
| parent_id  | BIGINT       | 부모 카테고리 ID | NULL                        |
| created_at | TIMESTAMP    | 생성 시간      | NULL                        |
| updated_at | TIMESTAMP    | 수정 시간      | NULL                        |

- 인덱스
    - **idx_category_name**: name 컬럼에 대한 단일 인덱스
    - **idx_category_parent_id**: parent_id 컬럼에 대한 단일 인덱스

### category_tree

| 컬럼명           | 타입        | 설명         | 제약조건                     |
|---------------|-----------|------------|--------------------------|
| ancestor_id   | BIGINT    | 조상 카테고리 ID | PRIMARY KEY, FOREIGN KEY |
| descendant_id | BIGINT    | 자손 카테고리 ID | PRIMARY KEY, FOREIGN KEY |
| depth         | INTEGER   | 계층 깊이      | NOT NULL                 |
| created_at    | TIMESTAMP | 생성 시간      | NULL                     |

복합 Primary Key: (ancestor_id, descendant_id)

- 인덱스
    - **PRIMARY KEY 인덱스**: (ancestor_id, descendant_id) 복합 인덱스 자동 생성
    - **idx_descendant_id**: descendant_id 컬럼에 대한 단일 인덱스

## API 명세

### Swagger UI

API 문서는 Swagger UI를 통해 확인할 수 있습니다.

- URL: `http://localhost:8080/swagger-ui.html`

