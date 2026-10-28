package domain 

trait QueueTimeRepository:
    def findAll(): List[QueueTime]
    def save(queueTime: QueueTime): Unit //Not sure is that important