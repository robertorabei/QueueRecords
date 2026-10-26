package ressources 

import java.io.FileReader
import scala.io.Source
import java.io.BufferedReader
import scala.collection.mutable.ListBuffer


class ReaderExample1 {
    val filename = "test.csv"
    val delimiter = ","
    val fileReader = new BufferedReader(new FileReader(filename))

    def handleRead(line: String) : Unit = {
        val newLine = fileReader.readLine()
        
        val l = line.split(",")
        print("101".toInt)
        if (newLine != null)
            handleRead(newLine)

    }
    
    def main(args: Array[String]): Unit = {
        fileReader.readLine()
        handleRead(fileReader.readLine())
        fileReader.close()
    }
}