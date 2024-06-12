package Nhom02.Nhom02HappyFarm.scraper;

import Nhom02.Nhom02HappyFarm.scraper.model.Price;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/scraper")
public class ScrapPrice {


    @GetMapping("/getcoffeeprice")
    public ResponseEntity<List<Price>> GetPriceCoffee() throws IOException {
        List <Price> lst = new ArrayList<>();
        WebClient webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        String url = "https://giacaphe.com/gia-ca-phe-noi-dia/";
        HtmlPage page = webClient.getPage(url);
        HtmlTable table = page.getFirstByXPath(".//table[@class='price-table']");
        List<HtmlTableRow> rows = table.getByXPath(".//tbody/tr");
        if (!rows.isEmpty()){
            for(int i = 0; i < rows.size() - 1; i++){
                Price cp = new Price();
                List<HtmlTableCell> cells = rows.get(i).getByXPath(".//td");
                cp.setMarket(cells.get(0).asNormalizedText());
                cp.setPrice(Double.parseDouble(cells.get(1).asNormalizedText().replace(",","")));
                cp.setChange(Double.parseDouble(cells.get(2).asNormalizedText().replace(",",".")));
                lst.add(cp);
            }
        }
        return ResponseEntity.ok(lst);
    }


    @GetMapping("/getpepperprice")
    public ResponseEntity<List<Price>> getPepperPrices() throws IOException {
        List<Price> lst = new ArrayList<>();
        WebClient webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        String url = "https://giatieu.com/gia-tieu-hom-nay/";
        HtmlPage page = webClient.getPage(url);
        HtmlTable table = page.getFirstByXPath(".//table[@class='table-gia-noidia']");
        List<HtmlTableRow> rows = table.getByXPath(".//tbody/tr");
        if (!rows.isEmpty()){
            for(int i = 0; i < rows.size(); i++){
                Price cp = new Price();
                List<HtmlTableCell> cells = rows.get(i).getByXPath(".//td");
                cp.setMarket(cells.get(0).asNormalizedText());
                cp.setPrice(Double.parseDouble(cells.get(1).asNormalizedText().replace(",","")));
                cp.setChange(Double.parseDouble(cells.get(2).asNormalizedText().replace(",",".")));
                lst.add(cp);
            }
        }
        return ResponseEntity.ok(lst);
    }


}