package app.bibbi.bff

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.blockhound.BlockHound
import reactor.blockhound.integration.ReactorIntegration


@SpringBootApplication
class BibbiMobileBffApplication

fun main(args: Array<String>) {
	BlockHound
		.builder()
		.with(ReactorIntegration())
		.install()
	runApplication<BibbiMobileBffApplication>(*args)
}
