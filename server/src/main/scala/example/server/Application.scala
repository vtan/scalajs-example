package example.server

import example.common

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import java.util.concurrent.atomic.AtomicInteger

object Application {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()

    val state = new AtomicInteger(0)

    val route =
      pathPrefix("api") {
        (get & path("state")) {
          complete { common.Response(state.get) }
        } ~
        (post & path("state" / "change") & entity(as[common.Request])) { request =>
          complete {
            val newValue = request.currentValue + request.modifier
            val updated = state.compareAndSet(request.currentValue, newValue)
            if (updated) {
              common.Response(newValue)
            } else {
              HttpResponse(status = StatusCodes.Conflict)
            }
          }
        }
      }.map(_.addHeader(`Access-Control-Allow-Origin`(HttpOriginRange.*)))

    Http().bindAndHandle(route, "localhost", 8080)
  }

}
