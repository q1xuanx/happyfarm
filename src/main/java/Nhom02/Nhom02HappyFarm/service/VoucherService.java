package Nhom02.Nhom02HappyFarm.service;


import Nhom02.Nhom02HappyFarm.entities.VoucherDiscount;
import Nhom02.Nhom02HappyFarm.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public List<VoucherDiscount> getListVoucher(){
        return voucherRepository.findAll();
    }

    public List<VoucherDiscount> getListNotDelteVoucher(){
        return voucherRepository.findAll().stream().filter(s -> !s.isDelete()).toList();
    }

    public void AddOrEditPaymentMethod(VoucherDiscount voucherDiscount) {
        voucherRepository.save(voucherDiscount);
    }

    public void DeleteVoucher(String id) {
        VoucherDiscount voucher = voucherRepository.findById(id).get();
        voucher.setDelete(true);
        voucherRepository.save(voucher);
    }

    public VoucherDiscount getVoucher(String id) {
        return voucherRepository.findById(id).get();
    }
    public boolean isValid (String nameVoucher){
        return voucherRepository.findAll().stream().filter(s -> s.getCodeVoucher().equals(nameVoucher) && !s.isDelete()).findFirst().isPresent();
    }
    public VoucherDiscount getVoucherByName(String nameVoucher){
        return voucherRepository.findAll().stream().filter(s -> s.getCodeVoucher().equals(nameVoucher)).findFirst().get();
    }
}
