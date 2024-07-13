package Nhom02.Nhom02HappyFarm.service;


import Nhom02.Nhom02HappyFarm.entities.Contacts;
import Nhom02.Nhom02HappyFarm.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactsService {
    private final ContactRepository contactRepository;
    public List<Contacts> getListContact() {
        return contactRepository.findAll().stream().sorted(Comparator.comparing(date -> LocalDate.parse(date.getDateSend()), Comparator.reverseOrder())).toList();
    }
    public void addNew(Contacts contact) {
        LocalDate now = LocalDate.now();
        contact.setDateSend(now.toString());
        contactRepository.save(contact);
    }
    public Contacts getDetails(String id){
        return contactRepository.findById(id).get();
    }
    public void delete(String id){
        contactRepository.deleteById(id);
    }
    public String checkExist(Contacts contacts){
        if (contacts.getName().isEmpty()){
            return "Name not found";
        }else if (contacts.getContent().isEmpty()){
            return "Content not found";
        }else if(contacts.getEmail().isEmpty()){
            return "Email not found";
        }else if(contacts.getPhoneNumber().isEmpty()){
            return "Phone not found";
        }
        return "OK";
    }
}
