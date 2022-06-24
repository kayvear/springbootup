package com.javaapps.springbootup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class SpringbootupApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootupApplication.class, args);
	}

}

@RestController
@RequestMapping("/bands")
class RestApiController {

	private final BandRepository bandRepository;

	public RestApiController(BandRepository bandRepository) {
		this.bandRepository = bandRepository;

		this.bandRepository.saveAll(List.of(
				new Band("Dire Straits"),
				new Band("Foo Fighters"),
				new Band("Nirvana"),
				new Band("Guns N Roses")
		));
	}

	@GetMapping
	Iterable<Band> getBands() {
		return bandRepository.findAll();
	}

	@GetMapping("/{id}")
	Optional<Band> getBandById(@PathVariable String id) {
		return bandRepository.findById(id);
	}

	@PostMapping
	Band postBand(@RequestBody Band band) {
		return bandRepository.save(band);
	}

	@PutMapping("/{id}")
	ResponseEntity<Band> putBand(@PathVariable String id, @RequestBody Band band) {

		return (!bandRepository.existsById(id)) ?
				new ResponseEntity<>(bandRepository.save(band), HttpStatus.CREATED):
				new ResponseEntity<>(bandRepository.save(band), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	void deleteBand(@PathVariable String id) {
		bandRepository.deleteById(id);
	}



}

interface BandRepository extends CrudRepository<Band, String> {}
@Entity
class Band {
	@Id
	private String id;
	private String name;

	public Band() {
		}

	public Band(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public Band(String name) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}

}