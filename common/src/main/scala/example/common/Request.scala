package example.common

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

final case class Request(
  modifier: Int,
  currentValue: Int
)

object Request {
  implicit val decoder: Decoder[Request] = deriveDecoder
  implicit val encoder: Encoder[Request] = deriveEncoder
}
