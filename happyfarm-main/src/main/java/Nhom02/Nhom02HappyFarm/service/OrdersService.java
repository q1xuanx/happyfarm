package Nhom02.Nhom02HappyFarm.service;

import Nhom02.Nhom02HappyFarm.config.VnpayConfig;
import Nhom02.Nhom02HappyFarm.entities.CartItems;
import Nhom02.Nhom02HappyFarm.entities.DetailsOrders;
import Nhom02.Nhom02HappyFarm.entities.Fertilizer;
import Nhom02.Nhom02HappyFarm.entities.Orders;
import Nhom02.Nhom02HappyFarm.repository.OrdersRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Order;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final CartItemService cartItemService;
    private final DetailsOrderService detailsOrderService;
    private final FertilizerService fertilizerService;
    private final float discount_for_htx = 0.5F;
    private final float discount_for_agency = 0.4F;
    private final float discount_for_farmer = 0.3F;
    private final JavaMailSender mailSender;

    //Role Hop tac xa giam 5%, role dai ly 4%, nhan vuon 3%
    public List<Orders> getAll() {
        return ordersRepository.findAll();
    }

    public Orders getOrders(String idOrder) {
        return ordersRepository.findById(idOrder).get();
    }
    public boolean checkQuantity(List<CartItems> cartItems) throws IOException {
        for(CartItems cart: cartItems){
            Fertilizer fer = fertilizerService.GetFertilizer(cart.getIdFertilizer().getIdFertilizer());
            if (cart.getQuantity() > fer.getNums()){
                return false;
            }
        }
        return true;
    }
    public void addNewOrder(Orders order, List<CartItems> cartItems) throws ExecutionException {
        LocalDate getCurrentDate = LocalDate.now();
        order.setOrderDate(Date.valueOf(getCurrentDate));
        order.setStatusOrders("Đã Đặt");
        ordersRepository.save(order);
        float total = 0;
        for (CartItems cartItem : cartItems) {
            detailsOrderService.addItemToDetailsOrders(cartItem, order);
            total += (cartItem.getQuantity() * cartItem.getIdFertilizer().getPrice());
            cartItemService.deleteCart(cartItem);
        }
        if (order.getIdUserOrder().getRoles().getNameRoles().equals("ROLE_HTX")) {
            total *= discount_for_htx;
        } else if (order.getIdUserOrder().getRoles().getNameRoles().equals("ROLE_AGENCY")) {
            total *= discount_for_agency;
        } else if (order.getIdUserOrder().getRoles().getNameRoles().equals("ROLE_FARMER")) {
            total *= discount_for_farmer;
        }
        if (order.getIdVoucher() != null) {
            total -= (total * (((float) order.getIdVoucher().getDiscountPercent() / 100)));
        }
        order.setTotalAmont(total);
        ordersRepository.save(order);
    }

    public void editOrder(Orders order) throws ExecutionException {
        ordersRepository.save(order);
    }

    public String vnpayHandle(HttpServletRequest req, Orders orders) throws UnsupportedEncodingException {
        String vnp_TxnRef = VnpayConfig.getRandomNumber(8);
        long amount = (long) (orders.getTotalAmont() * 100);
        String vnp_IpAddr = VnpayConfig.getIpAddress(req);
        String vnp_TmnCode = VnpayConfig.vnp_TmnCode;
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VnpayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VnpayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orders.getIdOrders());
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_ReturnUrl", VnpayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        return VnpayConfig.vnp_PayUrl + "?" + queryUrl;
    }

    public List<Orders> getHistoryOrder(String idUser) {
        return ordersRepository.findAll().stream().filter(s -> s.getIdUserOrder().getIdUser().equals(idUser)).toList();
    }

    public List<DetailsOrders> getDetailsOrder(String idOrder) {
        return detailsOrderService.getByIdOrder(idOrder);
    }

    public void sendEmail(String emailSend, List<DetailsOrders> detailsOrders) throws MessagingException {
        Map<Object, String> response = new HashMap<Object, String>();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom("happyfarm.cskh.vn@gmail.com");
        helper.setTo(emailSend);
        helper.setSubject("[Happy Farm] Cảm ơn bạn");
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("<html>")
                .append("<body>")
                .append("<h1>Cảm ơn bạn đã mua hàng tại Happy Farm!</h1>")
                .append("<p>Dưới đây là danh sách các sản phẩm bạn đã mua:</p>")
                .append("<table style='border-collapse: collapse; width: 100%;'>")
                .append("<tr>")
                .append("<th style='border: 1px solid black; padding: 8px;'>Sản phẩm</th>")
                .append("<th style='border: 1px solid black; padding: 8px;'>Số lượng</th>")
                .append("</tr>");

        for (DetailsOrders detailsOrder : detailsOrders) {
            emailContent.append("<tr>")
                    .append("<td style='border: 1px solid black; padding: 8px;'>").append(detailsOrder.getNameFertilizer()).append("</td>")
                    .append("<td style='border: 1px solid black; padding: 8px;'>").append(detailsOrder.getQuantity()).append("</td>")
                    .append("</tr>");
        }

        emailContent.append("</table>")
                .append("<p>Chúng tôi hy vọng bạn sẽ hài lòng với các sản phẩm của chúng tôi. Nếu bạn có bất kỳ câu hỏi nào, xin vui lòng liên hệ với chúng tôi.</p>")
                .append("<p>Trân trọng,</p>")
                .append("<p>Đội ngũ Happy Farm</p>")
                .append("</body>")
                .append("</html>");

        helper.setText(emailContent.toString(), true);
        mailSender.send(mimeMessage);
    }

    public List<Object[]> getMonthlyStatistics(int year) {
        return ordersRepository.getMonthlyStatistics(year);
    }

    public List<Orders> getOrdersByMonthAndYear(int year, int month) {
        return ordersRepository.findOrdersByMonthAndYear(year, month, "Đã Đặt".toLowerCase().trim());
    }
}
