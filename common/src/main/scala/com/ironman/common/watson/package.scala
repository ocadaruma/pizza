package com.ironman.common

import org.json4s.ext.JodaTimeSerializers

package object watson {
  val username = "334a8008-ff76-43c3-b014-239e29d63710"
  val password = "TiECS4jvULak"

  implicit val formats = org.json4s.DefaultFormats.lossless ++ JodaTimeSerializers.all
}
