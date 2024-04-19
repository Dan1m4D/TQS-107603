package tqs.student;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

@SpringBootTest
@Testcontainers
class StudentApplicationTests {

	@Autowired
	private StudentRepository studentRepository;

	@Container
	@Order(1)
	public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14")
			.withDatabaseName("d479")
			.withUsername("d479")
			.withPassword("tqsex7_3");

	@DynamicPropertySource
	@Order(2)
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
		registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
	}

	@Test
	@DisplayName("Test add student")
	@Order(3)
	void testAddStudent() {
		Student student = new Student(123456, "John Doe", "jonh@ua.pt");

		studentRepository.save(student);

		Student student2 = studentRepository.findById(123456).get();

		assertEquals(student.getNmec(), student2.getNmec());
		assertEquals(student.getNome(), student2.getNome());
		assertEquals(student.getEmail(), student2.getEmail());
	}

	@Test
	@DisplayName("Test update student")
	@Order(4)
	void testUpdateStudent() {
		Student student = new Student(123456, "John Doe", "jonh@ua.pt");

		studentRepository.save(student);

		Student student2 = studentRepository.findById(123456).get();
		student2.setNome("Jane Doe");

		studentRepository.save(student2);

		Student result = studentRepository.findById(123456).get();

		assertEquals("Jane Doe", result.getNome());
	}

	@Test
	@DisplayName("Test delete student")
	@Order(5)
	void testDeleteStudent() {
		Student student = new Student(123456, "John Doe", "jonh@ua.pt");

		studentRepository.save(student);

		assertTrue(studentRepository.existsById(123456));

		studentRepository.deleteById(123456);

		assertFalse(studentRepository.existsById(123456));
	}

	@Test
	@DisplayName("Test get student inserted in .sql")
	@Order(6)
	void testget() {

		Student result = studentRepository.findById(111111).get();

		assertEquals("Daniel", result.getNome());
	}

}
