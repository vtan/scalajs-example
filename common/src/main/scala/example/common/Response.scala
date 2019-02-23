package example.common

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

final case class Response(
  state: Int
)

object Response {
  implicit val decoder: Decoder[Response] = deriveDecoder
  implicit val encoder: Encoder[Response] = deriveEncoder
}
