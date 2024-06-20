package Nhom02.Nhom02HappyFarm.service;


import Nhom02.Nhom02HappyFarm.entities.PaymentMethod;
import Nhom02.Nhom02HappyFarm.repository.PaymentMethodRepository;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;

    public List<PaymentMethod> getListPayment(){
        return paymentMethodRepository.findAll();
    }

    public List<PaymentMethod> getListNotDeletedPayment(){
        return paymentMethodRepository.findAll().stream().filter(s -> !s.isDelete()).toList();
    }

    public void AddOrEditPaymentMethod(PaymentMethod paymentMethod) {
        paymentMethodRepository.save(paymentMethod);
    }

    public void DeletePaymentMethod(String id) {
        PaymentMethod getPaymentMethod = paymentMethodRepository.findById(id).get();
        getPaymentMethod.setDelete(true);
        paymentMethodRepository.save(getPaymentMethod);
    }

    public PaymentMethod getPaymentMethod(String id) {
        return paymentMethodRepository.findById(id).get();
    }

}
