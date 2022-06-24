package com.javaapps.springbootup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
	private List<Band> bands = new ArrayList<>();

	public RestApiController() {
		bands.addAll(List.of(
					new Band("Dire Straits"),
					new Band("Foo Fighters"),
					new Band("Nirvana"),
					new Band("Guns N Roses")
		));
	}

	@GetMapping
	Iterable<Band> getBands() {
		return bands;
	}

	@GetMapping("/{id}")
	Optional<Band> getBandById(@PathVariable String id) {
		for (Band b: bands) {
			if (b.getId().equals(id)) {
				return Optional.of(b);
			}
		}
		return Optional.empty();
	}

	@PostMapping
	Band postBand(@RequestBody Band band) {
		bands.add(band);
		return band;
	}

	@PutMapping("/{id}")
	ResponseEntity<Band> putBand(@PathVariable String id, @RequestBody Band band) {
		int bandIndex = -1;

		for (Band b:bands) {
			if (b.getId().equals(id)) {
				bandIndex = bands.indexOf(b);
				bands.set(bandIndex, band);
			}
		}

		return (bandIndex == -1) ?
				new ResponseEntity<>(postBand(band), HttpStatus.CREATED):
				new ResponseEntity<>(band, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	void deleteBand(@PathVariable String id) {
		bands.removeIf(b -> b.getId().equals(id));
	}



}

@Entity
class Band {
	@Id
	private String id;
	private String name;

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