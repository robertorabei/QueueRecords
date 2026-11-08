package adapters.in

import application.RideService
import scala.io.Source
import java.io.FileWriter
import java.io.File
import scala.util.control.Breaks._

// Doit lire un fichier txt 

class BatchExecution(service: RideService):
    def run(filename: String): Unit=
        var requestNumber: Int = 1
        val source = Source.fromFile(filename)
        val outputFile = new File("output.txt")
        val fileWriter = new FileWriter(outputFile)
        try {
            for (line <- source.getLines().takeWhile(_ != "exit")) do
                val lineContent = line.split(" ")
                fileWriter.write(s"Answer for request number $requestNumber:\n")

                lineContent(0) match 
                    case "list" =>
                        val rides = service.listCurrentStates()
                        if rides.isEmpty then fileWriter.write("No rides currently \n")
                        else rides.foreach(ride => fileWriter.write(service.printInfo(ride) + "\n"))

                    case "find" => 
                        val requestedRideID = lineContent(1).toInt
                        if (service.exists(requestedRideID))
                            val requestedRide = service.findCurrentState(requestedRideID)
                            fileWriter.write(service.printInfo(requestedRide) + "\n")
                        else 
                            fileWriter.write((s"This ride: $requestedRideID does not exist.\n"))

                    case "recommend" =>
                        fileWriter.write("Here are 3 rides we recommend:\n")
                        val recommendedRides = service.recommendRides()
                        if recommendedRides.isEmpty then fileWriter.write("No open rides currently.\n")
                        else recommendedRides.foreach(ride => fileWriter.write(service.printInfo(ride) + "\n"))

                    case _ =>
                            fileWriter.write(s"Unknown command: ${lineContent(0)}\n")
                requestNumber += 1
            fileWriter.write("Exiting...\n")
        } finally {
            fileWriter.close()
            source.close()
        }
            

