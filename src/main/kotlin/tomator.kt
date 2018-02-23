import org.jsoup.Jsoup
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils
import java.io.File
import java.net.URL
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

    if(links.isEmpty()){
        println("No pictures to download")
        return
    }

    val path = Paths.get(".").toAbsolutePath().normalize()
    links.forEach({ DownloadToDisk(it, path) })
}

fun DownloadToDisk(fileUrl: String, path: Path){
    val filename = FilenameUtils.getName(fileUrl)
    val filePath = Paths.get(path.toString(),filename).toString()
    FileUtils.copyURLToFile(URL(fileUrl), File(filePath));
}

