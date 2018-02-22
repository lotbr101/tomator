import org.jsoup.Jsoup
import com.github.kittinunf.fuel.Fuel
import org.apache.commons.io.FilenameUtils
import java.io.File
import java.nio.file.Paths


fun main(args: Array<String>){
    if(args.size == 0){
        println("Usage: tomator <url>")
        return
    }
    val url = args[0]
    val doc = Jsoup.connect(url).get()
    val links = doc.select(".photoThumb").eachAttr("href")

    links.forEach({ DownloadToDisk(it) })
}

fun DownloadToDisk(fileUrl: String){
    val filename = FilenameUtils.getName(fileUrl)
    val path = Paths.get(".").toAbsolutePath().normalize().toString()


    Fuel.download(fileUrl).destination { response, url ->
        File(path+"/"+filename)
    }.response{req, res, result -> }
}

