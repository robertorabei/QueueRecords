import adapters.in.{ConsoleController, BatchExecution}
import adapters.out.{CSVRideRepository, InMemoryRideRepository}
import application.{RideService, RideServiceImpl}
import domain.{Ride, RideRepository}

import scala.io.Source
import java.io.FileNotFoundException

object Main:
    try 
        val path = java.nio.file.Paths.get("src/main/scala/ressources/queuetimes.csv")
        val repo = new CSVRideRepository(path.toString)

        val rideService: RideService = new RideServiceImpl(repo)
        val controller = new BatchExecution(rideService)
        controller.run("src/main/scala/ressources/test.txt")
    catch {
        case e: FileNotFoundException =>
            println("[ERROR] File not found.")
    }