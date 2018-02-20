import org.jsoup.Jsoup
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpGet

fun main(args: Array<String>){
    val url = "http://marcopolo11.rajce.idnes.cz/Zimni_postrehy/#"
    val doc = Jsoup.connect(url).get()
    val links = doc.select(".photoThumb").eachAttr("href")
    links.forEach({ print(it)})
}