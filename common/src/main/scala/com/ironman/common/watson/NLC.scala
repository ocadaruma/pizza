package com.ironman.common.watson

import org.apache.commons.io.IOUtils
import org.apache.http.auth.{AuthScope, UsernamePasswordCredentials}
import org.apache.http.client.methods.{HttpGet, HttpPost}
import org.apache.http.client.utils.URIBuilder
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.impl.client.{BasicCredentialsProvider, HttpClientBuilder}
import org.joda.time.DateTime
import org.json4s.jackson.{Serialization, JsonMethods}

case class TrainingRow(word: String, clazz: String)

case class NLCCreateResponse(classifier_id: String,
                             name: String,
                             language: String,
                             created: DateTime,
                             url: String,
                             status: String,
                             status_description: String)

case class NLCClassifyResponse(classifier_id: String,
                               url: String,
                               text: String,
                               top_class: String,
                               classes: Seq[NLCClass])

case class NLCClass(class_name: String, confidence: Double)

trait NLC {
  import NLC._

  def classify(classifierId: String, text: String): NLCClassifyResponse = {
    val uri = new URIBuilder(s"$endpoint/$classifierId/classify").setParameter("text", text).build()
    val get = new HttpGet(uri)

    val response = httpClient.execute(get)
    val json = IOUtils.toString(response.getEntity.getContent)
    JsonMethods.parse(json).extract[NLCClassifyResponse]
  }

  def create(training: Seq[TrainingRow]): NLCCreateResponse = {
    val post = new HttpPost(endpoint)

    val entity = MultipartEntityBuilder.create()
    entity.addTextBody("training_metadata", Serialization.write(
      Map("language" -> "ja", "name" -> "Classifier")
    ))
    entity.addBinaryBody("training_data", training.map { case TrainingRow(word, clazz) => s"$word,$clazz" }.mkString("\n").getBytes)
    post.setEntity(entity.build())

    val response = httpClient.execute(post)
    val json = IOUtils.toString(response.getEntity.getContent)
    JsonMethods.parse(json).extract[NLCCreateResponse]
  }
}

object NLC extends NLC {
  val httpClient = {
    val provider = new BasicCredentialsProvider
    val credential = new UsernamePasswordCredentials(username, password)
    provider.setCredentials(AuthScope.ANY, credential)

    HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build()
  }
  val endpoint = "https://gateway.watsonplatform.net/natural-language-classifier/api/v1/classifiers"
}
