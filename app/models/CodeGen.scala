package models

object CodeGen extends App {
  slick.codegen.SourceCodeGenerator.run(
    "slick.jdbc.PostgresProfile",
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost/task_service_db?user=taskuser&password=541Harry",
    "/Users/mackhartley/code/personal/taskService/taskservice/app/",
    "models", None, None, true, false
  )
}
