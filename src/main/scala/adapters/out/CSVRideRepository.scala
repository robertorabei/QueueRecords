package adapters.out

import domain.{Ride, RideRepository}
import scala.collection.mutable.ListBuffer
import java.io.FileWriter
import com.github.tototoshi.csv._ 
import java.io.File


class CSVRideRepository(filename: String) extends RideRepository:
    private val rides: ListBuffer[Ride] = ListBuffer.empty[Ride]
    private val reader = CSVReader.open(new File(filename))

    // Transforming received data into workable data types
    private def load(): List[Ride] = 
        val values = reader.all().drop(1).map {values =>
            Ride(
                rideId    = values.head.toInt,
                rideName  = values(1),
                isOpen    = values(2).toBoolean,
                waitTime  = values(3).toShort,
                date      = values(4),
                timeStamp = values(5)
            )
        }
        rides.addAll(values)
        rides.toList

    def findAll(): List[Ride] = load()

    // Not used in this project since it was not asked but did implement it
    def save(newRide: Ride): Unit = 
        val newLine: String = 
            s"${newRide.rideId.toString()}" +
            s",${newRide.rideName}" +
            s",${newRide.isOpen.toString().toUpperCase()}" + 
            s",${newRide.waitTime.toString()}" + 
            s",${newRide.date}" +
            s",${newRide.timeStamp}"

        val fileWriter: FileWriter = new FileWriter(filename)
        fileWriter.write(newLine)
        fileWriter.close()


