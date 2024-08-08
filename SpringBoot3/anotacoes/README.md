# As 4 associações do Spring Data JPA

## @OneToOne (Um para Um)

##### Entidades Java:
```java
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;
}

@Entity
public class Profile {
    @Id
    @GeneratedValue
    private Long id;
    
    @OneToOne(mappedBy = "profile")
    private User user;
}
```

##### Tradução SQL:
```sql
CREATE TABLE user (
    id BIGINT PRIMARY KEY,
    profile_id BIGINT UNIQUE,
    FOREIGN KEY (profile_id) REFERENCES profile(id)
);

CREATE TABLE profile (
    id BIGINT PRIMARY KEY
);
```

## @OneToMany / @ManyToOne (Um para Muitos / Muitos para Um)

##### Entidades Java:
```java
@Entity
public class Department {
    @Id
    @GeneratedValue
    private Long id;
    
    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}

@Entity
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
```

##### Tradução SQL:
```sql
CREATE TABLE department (
    id BIGINT PRIMARY KEY
);

CREATE TABLE employee (
    id BIGINT PRIMARY KEY,
    department_id BIGINT,
    FOREIGN KEY (department_id) REFERENCES department(id)
);
```

## @ManyToMany (Muitos para Muitos)

##### Entidades Java:
```java
@Entity
public class Student {
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;
}

@Entity
public class Course {
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToMany(mappedBy = "courses")
    private List<Student> students;
}
```

##### Tradução SQL:
```sql
CREATE TABLE student (
    id BIGINT PRIMARY KEY
);

CREATE TABLE course (
    id BIGINT PRIMARY KEY
);

CREATE TABLE student_course (
    student_id BIGINT,
    course_id BIGINT,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES student(id),
    FOREIGN KEY (course_id) REFERENCES course(id)
);
```
