import org.jsoup.Jsoup
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils
import java.io.File
import java.net.URL
import java.nio.file.Path
import java.nio.file.Paths


fun main(args: Array<String>){
    if(args.isEmpty()){
        println("Použití: tomator <url>")
        return
    }
    val url = args[0]
    val doc = Jsoup.connect(url).get()
    val links = doc.select(".photoThumb").eachAttr("href")

    if(links.isEmpty()){
        println("Žádé obrázky ke stažení")
        return
    }

    val path = Paths.get(".").toAbsolutePath().normalize()
    val cnt = links.size
    var i = 0;
    links.forEach({
        downloadToDisk(it, path)
        i += 1
        print("Stahuji $i z $cnt\r")
    })
}

fun downloadToDisk(fileUrl: String, path: Path){
    val fixedFileUrl = fixUrl(fileUrl)
    val filename = FilenameUtils.getName(fixedFileUrl)
    val filePath = Paths.get(path.toString(),filename).toString()
    try {
        FileUtils.copyURLToFile(URL(fixedFileUrl), File(filePath))
    }
    catch(ex:Exception){
        println(fileUrl)
        println(ex.message)
    }
}
fun fixUrl(fileUrl: String):String{
    if(fileUrl.subSequence(0,3)!="http" && fileUrl.subSequence(0,2)=="//"){
        return "http:$fileUrl"
    }
    return fileUrl
}

