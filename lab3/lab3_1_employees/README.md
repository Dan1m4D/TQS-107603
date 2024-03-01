# Lab3_1 - Employees

## Review questions

### a) Identify a couple of examples that use AssertJ expressive methods chaining.

- In `A_EmployeeRepositoryTest.java#givenSetOfEmployees_whenFindAll_thenReturnAllEmployees()`:

```java
assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
```

- In `B_EmployeeService_UnitTest.java#given3Employees_whengetAll_thenReturn3Records()`:

```java
assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(), john.getName(), bob.getName());
```

- In `D_EmployeeRestControllerIT.java#givenSetOfEmployees_whenFindAll_thenReturnAllEmployees()`:

```java
assertThat(found).extracting(Employee::getName).containsOnly("bob");
```

- In `E_EmployeeRestControllerTemplateIT.java#givenEmployees_whenGetEmployees_thenStatus200()`:

```java
assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex");
```
### b) Identify an example in which you mock the behavior of the repository (and avoid involving a  database). 

- In `D_EmployeeRestControllerIT.java`
```java
@Mock( lenient = true)
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setUp() {

        //these expectations provide an alternative to the use of the repository
        Employee john = new Employee("john", "john@deti.com");
        john.setId(111L);

        Employee bob = new Employee("bob", "bob@deti.com");
        Employee alex = new Employee("alex", "alex@deti.com");

        List<Employee> allEmployees = Arrays.asList(john, bob, alex);

        Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
        Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
        Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
        Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
        Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
        Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
    }
```

### c) What is the difference between standard @Mock and @MockBean?

 `@Mock` is used for unit tests with Mockito to create mock objects, while `@MockBean` is used for integration tests with Spring Boot to create mock beans within the Spring ApplicationContext.

 ### d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?

 The file `application-integrationtest.properties` plays a role in configuring properties specific to integration tests in a Spring Boot application.

 It'll be used when we run the application integration tests for configure the environment.

 ### e) the sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?

 In reality, they both serve different purposes and environments. While `@WebMvcTest` is used for testing Spring MCV Controllers in isolation, `WebEnvironment.MOCK`is used for testing within a mock servlet environment, suitable for unit tests. `WebEnvironment.RANDOM_PORT` is used for integration testing with a real embedded web server, suitable for end-to-end testing.

