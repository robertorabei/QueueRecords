import adapters.in.{CLIAdapter, BatchAdapter}
import adapters.out.{CSVRideRepository, InMemoryRideRepository}
import application.{RideService, RideServiceImpl}
import domain.{Ride, RideRepository}

import scala.io.Source
import java.io.FileNotFoundException

object Main extends App:
    try 
        val path = java.nio.file.Paths.get("src/main/scala/ressources/queuetimes.csv")
        val repo = new CSVRideRepository(path.toString)

        val rideService: RideService = new RideServiceImpl(repo)
        val controller = new CLIAdapter(rideService)
        controller.run()

        // If BatchAdapter is wanted
        // val inputFile = java.nio.file.Paths.get("src/main/scala/ressources/test.txt")
        // val controller = new BatchAdapter(rideService)
        // controller.run(inputFile.toString)

    catch {
        case e: FileNotFoundException =>
            println("[ERROR] File not found.")
    }