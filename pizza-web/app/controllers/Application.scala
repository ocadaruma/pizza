package controllers

import com.ironman.common.watson.NLC
import org.json4s.ext.JodaTimeSerializers
import org.json4s.jackson.Serialization
import play.api._
import play.api.mvc._

object Application extends Controller {
  implicit val formats = org.json4s.DefaultFormats.lossless ++ JodaTimeSerializers.all

  val classifierId = "3AE103x13-nlc-1269"

  def index = Action {
    Ok(views.html.index())
  }

  def classify(text: String) = Action {
    Ok(Serialization.write(NLC.classify(classifierId, text).classes)).as("application/json")
  }
}
