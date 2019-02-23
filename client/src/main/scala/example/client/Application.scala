package example.client

import example.common
import example.common.Response

import io.circe.parser.decode
import org.scalajs.dom.ext.Ajax

object Application {

  def main(args: Array[String]): Unit = {
    import scala.concurrent.ExecutionContext.Implicits.global

    Ajax.get("http://localhost:8080/api/state")
      .map(_.responseText)
      .map(decode[common.Response])
      .foreach {
        case Left(err) => println(err)
        case Right(Response(state)) => println(state)
      }
  }

}
