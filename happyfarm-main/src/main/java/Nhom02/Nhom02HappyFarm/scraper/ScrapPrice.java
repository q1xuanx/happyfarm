package Nhom02.Nhom02HappyFarm.scraper;

import Nhom02.Nhom02HappyFarm.response.ResponseHandler;
import Nhom02.Nhom02HappyFarm.scraper.model.Price;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLDivElement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/scraper")
@RequiredArgsConstructor
public class ScrapPrice {
    private final ResponseHandler responseHandler;

    @GetMapping("/getcoffeeprice")
    public ResponseEntity<Object> GetPriceCoffee() throws IOException {
        List<Price> lst = new ArrayList<>();
        WebClient webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        String url = "https://giacaphe.com/gia-ca-phe-noi-dia/";
        HtmlPage page = webClient.getPage(url);
        HtmlTable table = page.getFirstByXPath(".//table[@class='price-table']");
        List<HtmlTableRow> rows = table.getByXPath(".//tbody/tr");
        if (!rows.isEmpty()) {
            for (int i = 0; i < rows.size() - 1; i++) {
                Price cp = new Price();
                List<HtmlTableCell> cells = rows.get(i).getByXPath(".//td");
                cp.setMarket(cells.get(0).asNormalizedText());
                cp.setPrice(cells.get(1).asNormalizedText());
                cp.setChange(cells.get(2).asNormalizedText().replace(",", "."));
                lst.add(cp);
            }
        }
        return ResponseEntity.ok(responseHandler.successResponse("Get list success", lst));
    }

    @GetMapping("/getpepperprice")
    public ResponseEntity<Object> getPepperPrices() throws IOException {
        List<Price> lst = new ArrayList<>();
        WebClient webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        String url = "https://giatieu.com/gia-tieu-hom-nay/";
        HtmlPage page = webClient.getPage(url);
        HtmlTable table = page.getFirstByXPath(".//table[@class='table-gia-noidia']");
        List<HtmlTableRow> rows = table.getByXPath(".//tbody/tr");
        if (!rows.isEmpty()) {
            for (int i = 0; i < rows.size(); i++) {
                Price cp = new Price();
                List<HtmlTableCell> cells = rows.get(i).getByXPath(".//td");
                cp.setMarket(cells.get(0).asNormalizedText());
                cp.setPrice(cells.get(1).asNormalizedText());
                cp.setChange(cells.get(2).asNormalizedText().replace(",", "."));
                lst.add(cp);
            }
        }
        return ResponseEntity.ok(responseHandler.successResponse("Get list success", lst));
    }

    @GetMapping("/getpricevegetables")
    public ResponseEntity<Object> listVegetables() throws IOException {
        List<Price> lst = new ArrayList<>();
        WebClient webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        String url = "https://nongsanbaophuong.com/bang-bao-gia-rau-cu-qua/#cac-loai-rau-la";
        HtmlPage page = webClient.getPage(url);
        HtmlTable table = page.getFirstByXPath(".//div[@id='cac-loai-cu-qua']//table");
        for (HtmlTableRow row : table.getRows()) {
            Price cp = new Price();
            boolean isDown = row.getAttribute("class").contains("label-danger");
            boolean isUp = row.getAttribute("class").contains("labe-success");
            List<HtmlTableCell> cells = row.getByXPath(".//td");
            if (cells.size() == 4) {
                if (isDown) {
                    cp.setMarket(cells.get(1).asNormalizedText());
                    cp.setPrice(cells.get(3).asNormalizedText() + "/kg");
                    cp.setChange("Đang giảm");
                } else if (isUp) {
                    cp.setMarket(cells.get(1).asNormalizedText());
                    cp.setPrice(cells.get(3).asNormalizedText() + "/kg");
                    cp.setChange("Đang tăng");
                } else {
                    cp.setMarket(cells.get(1).asNormalizedText());
                    if (cells.get(3).asNormalizedText().equals("Hết mùa")) {
                        cp.setPrice(cells.get(3).asNormalizedText());
                        cp.setChange("Hết mùa");
                    } else {
                        cp.setPrice(cells.get(3).asNormalizedText() + "/kg");
                        cp.setChange("Bình ổn");
                    }
                }
                lst.add(cp);
            }
        }
        return ResponseEntity.ok(responseHandler.successResponse("Get list success", lst));
    }

    @GetMapping("/getpricedurian")
    public ResponseEntity<Object> listDurian() throws IOException {
        List<Map<String, List<Price>>> lst = new ArrayList<>();
        WebClient webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        String url = "https://giacaphe.com/gia-sau-rieng-hom-nay/";
        HtmlPage page = webClient.getPage(url);
        HtmlTable table = page.getFirstByXPath(".//div[@class='entry-content']//table");
        Map<String, List<Price>> temp = new HashMap<>();
        String currentKey = "";
        for (HtmlTableRow row : table.getRows()) {
            List<HtmlTableCell> cells = row.getByXPath(".//td");
            if (cells.size() == 1) {
                if (!currentKey.equals("")) {
                    lst.add(new HashMap<>(temp));
                    temp.clear();
                }
                temp.put(cells.get(0).asNormalizedText(), new ArrayList<>());
                currentKey = cells.get(0).asNormalizedText();
            } else if (cells.size() == 2) {
                if (temp.containsKey(currentKey)) {
                    List<Price> tt = temp.get(currentKey);
                    Price price = new Price();
                    price.setMarket(cells.get(0).asNormalizedText());
                    price.setPrice(cells.get(1).asNormalizedText());
                    tt.add(price);
                }
            }
        }
        if (temp != null && !currentKey.isEmpty()) {
            lst.add(new HashMap<>(temp));
        }
        return ResponseEntity.ok(responseHandler.successResponse("Get list success", lst));
    }

    @GetMapping("/getpriceferilizer")
    public ResponseEntity<Object> listFertilizer() throws IOException {
        List<Price> lst = new ArrayList<>();
        WebClient webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        String url = "https://giacaphe.com/gia-phan-bon/";
        HtmlPage page = webClient.getPage(url);
        HtmlTable table = page.getFirstByXPath(".//div[@class='phanbon-body']//table");
        int turn = 0;
        for (HtmlTableRow row : table.getRows()) {
            if (turn == 0) {
                turn++;
                continue;
            }
            List<HtmlTableCell> cells = row.getByXPath(".//td");
            Price price = new Price();
            price.setMarket(cells.get(1).asNormalizedText());
            price.setPrice(cells.get(2).asNormalizedText());
            lst.add(price);
        }
        return ResponseEntity.ok(responseHandler.successResponse("Get list success", lst));
    }

    @GetMapping("/getpricecuqua")
    public ResponseEntity<Object> listCuQua() throws IOException {
        List<Price> lst = new ArrayList<>();
        WebClient webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        String url = "https://nongsanbaophuong.com/bang-bao-gia-rau-cu-qua/#cac-loai-cu-qua";
        HtmlPage page = webClient.getPage(url);
        HtmlTable table = page.getFirstByXPath("//div[@id='cac-loai-cu-qua']//table");
        for (HtmlTableRow row : table.getRows()) {
            Price cp = new Price();
            List<HtmlTableCell> cells = row.getByXPath(".//td");
            if (cells.size() == 4) {
                cp.setMarket(cells.get(1).asNormalizedText());
                cp.setPrice(cells.get(3).asNormalizedText() + "/kg");
                lst.add(cp);
            }
        }
        return ResponseEntity.ok(responseHandler.successResponse("Get list success", lst));
    }

}