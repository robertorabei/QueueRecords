import adapters.in.ConsoleController
import adapters.out.{csvRideRepository, InMemoryRideRepository}
import application.RideService
import domain.{Ride, RideRepository}

import scala.io.Source


object Main extends App:
    val path = java.nio.file.Paths.get("src/main/scala/ressources/queuetimes.csv")
    val repo = new csvRideRepository(path.toString)

    val service = new RideService(repo)
    val controller = new ConsoleController(service)
    controller.run()