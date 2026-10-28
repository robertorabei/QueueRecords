import adapters.in.ConsoleController
import adapters.out.{csvQueueRepository, InMemoryQueueRepository}
import application.QueueTimeService
import domain.{QueueTime, QueueTimeRepository}

import scala.io.Source


object Main extends App:
    val path = java.nio.file.Paths.get("src/main/scala/ressources/queuetimes.csv")
    val repo = new csvQueueRepository(path.toString)

    val service = new QueueTimeService(repo)
    val controller = new ConsoleController(service)
    controller.run()