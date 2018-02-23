import org.jsoup.Jsoup
import com.github.kittinunf.fuel.Fuel
import org.apache.commons.io.FilenameUtils
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths


fun main(args: Array<String>){
    if(args.size == 0){
        println("Usage: tomator <url>")
        return
    }
    val url = args[0]
    val doc = Jsoup.connect(url).get()
    val links = doc.select(".photoThumb").eachAttr("href")

    val path = Paths.get(".").toAbsolutePath().normalize()
    links.forEach({ DownloadToDisk(it, path) })
}

fun DownloadToDisk(fileUrl: String, path: Path){
    val filename = FilenameUtils.getName(fileUrl)
    val filePath = Paths.get(path.toString(),filename).toString()

    Fuel.download(fileUrl).destination { response, url ->
        File(filePath)
    }.response{req, res, result -> }
}

