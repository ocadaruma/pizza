import java.io.File
import org.apache.commons.io.FileUtils
import org.jsoup.Jsoup

import collection.JavaConverters._
import scala.util.Try

object Main {
  def main(args: Array[String]) {
    val root = new File("/Users/hokada/develop/scala/pizza/files-tmp/email.chottu.net/example")
    val files = FileUtils.listFiles(root, null, true)
    val texts = files.asScala.map(FileUtils.readFileToString).flatMap(d => Try(Jsoup.parse(d).getElementsByClass("mail").text()).getOrElse("").split("\n|。")).filter(_.nonEmpty)

    FileUtils.write(new File("/Users/hokada/develop/scala/pizza/corpus.txt"), texts.mkString("\n"))
  }
}
