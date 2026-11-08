import adapters.in.{ConsoleController, BatchExecution}
import adapters.out.{csvRideRepository, InMemoryRideRepository}
import application.RideService
import domain.{Ride, RideRepository}

import scala.io.Source
import java.io.FileNotFoundException




object Main extends App:
    try 
        val path = java.nio.file.Paths.get("src/main/scala/ressources/queuetimes.csv")
        val repo = new csvRideRepository(path.toString)

        val service = new RideService(repo)
        val controller = new BatchExecution(service)
        controller.run("src/main/scala/ressources/test.txt")
    catch {
        case e: FileNotFoundException =>
            println("[ERROR] File not found.")
    }