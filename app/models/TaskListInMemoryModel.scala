package models

import scala.collection.mutable

object TaskListInMemoryModel {
  private val users = mutable.Map[String, String]("Mark" -> "Pass")
  private val tasks = mutable.Map[String, List[String]]("Mark" -> List("Make viddeos", "eat", "sleep", "repeat"))

  def validateUser(username: String, password: String): Boolean = {
    users.get(username).map {_ == password}.getOrElse(false)
  }

  def createUser(username: String, password: String): Boolean = {
    if (users.contains(username)) false else {
      users(username) = password
      true
    }
  }

  def getTasks(username: String): Seq[String] = {
    tasks.getOrElse(username, Nil)
  }

  def addTask(username: String, task: String): Unit = {
    tasks(username) = task :: tasks.getOrElse(username, Nil)
  }

  def removeTask(username: String, index: Int): Boolean = {
    if (index < 0 || tasks.get(username).isEmpty || index >= tasks(username).length) false
    else {
      tasks(username) = tasks(username).patch(index, Nil, 1)
      true
    }
  }
}
