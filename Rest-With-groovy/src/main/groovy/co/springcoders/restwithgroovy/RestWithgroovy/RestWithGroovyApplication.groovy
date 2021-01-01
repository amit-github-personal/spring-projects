package co.springcoders.restwithgroovy.RestWithgroovy

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class RestWithGroovyApplication {

	static void main(String[] args) {
		SpringApplication.run(RestWithGroovyApplication, args)
	}

}

@RestController
class Controller {

	@GetMapping("/getServerStatus")
	def getServerStatus(){
		return "Server is running"
	}

	@GetMapping("/user/{userId}")
	def getUser(@PathVariable def userId){
		User user = new User()

		user.email = "someemail@gmail.com"
		user.username = "fancy Username"
		return user;
	}
}

class User {
	String username
	String email
	String name
}