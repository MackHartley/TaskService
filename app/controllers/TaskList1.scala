package controllers

import javax.inject.{Inject, Singleton}
import models.TaskListInMemoryModel
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class TaskList1 @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def login = Action { implicit request =>
    Ok(views.html.login1())
  }

  def validateLoginGet(username: String, password: String) = Action {
    Ok(s"$username logged in with $password.")
  }

  def validateLoginPost = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.validateUser(username, password)) {
        Redirect(routes.TaskList1.taskList).withSession("username" -> username)
      } else {
        Redirect(routes.TaskList1.login).flashing("error" -> "Invalid username/password combo.")
      }
    }.getOrElse(
      Redirect(routes.TaskList1.login)
    )
  }

  def createUser = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.createUser(username, password)) {
        Redirect(routes.TaskList1.taskList).withSession("username" -> username)
      } else {
        Redirect(routes.TaskList1.login).flashing("error" -> "User creation failed.")
      }
    }.getOrElse(
      Redirect(routes.TaskList1.login)
    )
  }

  def taskList = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val tasks = TaskListInMemoryModel.getTasks(username)
      Ok(views.html.taskList1(tasks))
    }.getOrElse(Redirect(routes.TaskList1.login))
  }

  def logout = Action {
    Redirect(routes.TaskList1.login).withNewSession
  }

  def addTask = Action { request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val postVlas = request.body.asFormUrlEncoded
      postVlas.map { args =>
        val task = args("newTask").head
        TaskListInMemoryModel.addTask(username, task)
        Redirect(routes.TaskList1.taskList)
      }.getOrElse(Redirect(routes.TaskList1.taskList))
    }.getOrElse(Redirect(routes.TaskList1.login))
  }

  def deleteTask = Action { request =>
    val usernameOption = request.session.get("username")
    val postVals = request.body.asFormUrlEncoded
    usernameOption.map { username =>
      postVals.map { args =>
        val index = args("index").head.toInt
        TaskListInMemoryModel.removeTask(username, index)
        Redirect(routes.TaskList1.taskList)
      }.getOrElse(Redirect(routes.TaskList1.taskList))
    }.getOrElse(Redirect(routes.TaskList1.login))
  }
}
