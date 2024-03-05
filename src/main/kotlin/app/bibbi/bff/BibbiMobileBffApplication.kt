package app.bibbi.bff

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.blockhound.BlockHound

@SpringBootApplication
class BibbiMobileBffApplication

fun main(args: Array<String>) {
	BlockHound.install()
	runApplication<BibbiMobileBffApplication>(*args)
}
