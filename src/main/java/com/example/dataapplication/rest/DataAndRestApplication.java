package com.example.dataapplication.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.stream.Stream;

@SpringBootApplication
public class DataAndRestApplication {

	@Bean
	ApplicationRunner runner(CarRepository repository) {
		return args -> {
			Stream.of("A", "B", "C", "D").forEach(carModel -> repository.save(new Car(null, carModel)));
			repository.findAll().forEach(System.out::println);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(DataAndRestApplication.class, args);
	}
}


interface CarRepository extends JpaRepository<Car, Long> {
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
class Car {

	@Id
	@GeneratedValue
	private Long id;
	private String make;
}

@RestController
class CarRestController {

	@GetMapping("/{id}")
	Car get(@PathVariable("id") Car car) {
		return car;
	}
}