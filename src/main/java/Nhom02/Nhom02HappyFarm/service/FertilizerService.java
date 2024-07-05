package Nhom02.Nhom02HappyFarm.service;

import Nhom02.Nhom02HappyFarm.entities.CartItems;
import Nhom02.Nhom02HappyFarm.entities.Fertilizer;
import Nhom02.Nhom02HappyFarm.repository.CartItemRepository;
import Nhom02.Nhom02HappyFarm.repository.FertilizerRepository;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FertilizerService {
    private final Cloudinary cloudinary;
    private final FertilizerRepository fertilizerRepository;
    private final CartItemRepository cartItemRepository;
    private final int sizeOfPage = 10;

    public List<Fertilizer> listFertilizer() {
        int getTotalItems = fertilizerRepository.findAll().size();
        Pageable page = PageRequest.of(((getTotalItems + sizeOfPage - 1) / sizeOfPage) - 1, sizeOfPage);
        Page<Fertilizer> paged = fertilizerRepository.findAll(page);
        return paged.getContent();
    }

    public void addNew(Fertilizer fertilizer) throws IOException {
        try {
            CompletableFuture<String> getImage = uploadImageAsync(fertilizer.getFileImageRepresent());
            List<CompletableFuture<String>> listImageOptional = new ArrayList<>();
            for (MultipartFile image : fertilizer.getFileImageOptional()) {
                listImageOptional.add(uploadImageAsync(image));
            }
            fertilizer.setImageRepresent(getImage.get());
            fertilizer.setImageOptional(listImageOptional.stream().map(CompletableFuture::join).collect(Collectors.toList()));
            fertilizer.setUrl(generateUrl(fertilizer.getNameFertilizer()));
            fertilizerRepository.save(fertilizer);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }


    public Fertilizer GetFertilizer(String id) throws IOException {
        try {
            Optional<Fertilizer> fertilizer = fertilizerRepository.findById(id);
            return fertilizer.orElseThrow(() -> new NoSuchElementException("Not found " + id));
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }

    public void EditFertilizer(Fertilizer fertilizer) throws ExecutionException {
        try {
            if (fertilizer.getFileImageRepresent() != null) {
                CompletableFuture<String> getNewRepresent = uploadImageAsync(fertilizer.getFileImageRepresent());
                fertilizer.setImageRepresent(getNewRepresent.get());
            }
            if (fertilizer.getFileImageOptional() != null) {
                List<CompletableFuture<String>> listImageOptional = new ArrayList<>();
                for (MultipartFile image : fertilizer.getFileImageOptional()) {
                    listImageOptional.add(uploadImageAsync(image));
                }
                fertilizer.setImageOptional(listImageOptional.stream().map(CompletableFuture::join).collect(Collectors.toList()));
            }
            fertilizer.setUrl(generateUrl(fertilizer.getNameFertilizer()));
            fertilizerRepository.save(fertilizer);
        } catch (Exception ex) {
            throw new ExecutionException(ex);
        }
    }

    public void DeleteFertilizer(String id) throws ExecutionException {
        try {
            Optional<Fertilizer> fertilizer = fertilizerRepository.findById(id);
            if (fertilizer.isPresent()) {
                fertilizer.get().setDelete(true);
                fertilizerRepository.save(fertilizer.get());
            }
        } catch (Exception ex) {
            throw new ExecutionException(ex);
        }
    }

    public int deleteFertilizerOut(String id) {
        try {
            List<CartItems> listCart = cartItemRepository.findAll().stream().filter(s -> s.getIdFertilizer().getIdFertilizer().equals(id)).toList();
            cartItemRepository.deleteAll(listCart);
            fertilizerRepository.deleteById(id);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean checkName(String name) {
        return fertilizerRepository.findAll().stream().anyMatch(s -> s.getNameFertilizer().equals(name));
    }

    public Fertilizer findExacfertilizer(String name) {
        return fertilizerRepository.findAll().stream().filter(s -> s.getNameFertilizer().equals(name)).findFirst().get();
    }

    //Tìm phân bón qua tên
    public List<Fertilizer> findFertilizerByName(String name) {
        Specification<Fertilizer> spec = Specification.where(FertilizerSpecifiation.hasName(name)).and(FertilizerSpecifiation.isNotDelete());
        Pageable page = PageRequest.of(0, sizeOfPage);
        Page<Fertilizer> paged = fertilizerRepository.findAll(spec, page);
        return paged.getContent();
    }

    //Lọc các loại phân bón
    public List<Fertilizer> filter(String brand, String origin, String typeFer) {
        Specification<Fertilizer> spec = Specification.where(FertilizerSpecifiation.hasBrand(brand)).and(FertilizerSpecifiation.hasOrigin(origin)).and(FertilizerSpecifiation.hasType(typeFer)).and(FertilizerSpecifiation.isNotDelete());
        Pageable page = PageRequest.of(0, sizeOfPage);
        Page<Fertilizer> paged = fertilizerRepository.findAll(spec, page);
        return paged.getContent();
    }

    public List<Fertilizer> filterByType(String typeFer) {
        Specification<Fertilizer> spec = Specification.where(FertilizerSpecifiation.hasType(typeFer).and(FertilizerSpecifiation.isNotDelete()));
        Pageable page = PageRequest.of(0, sizeOfPage);
        Page<Fertilizer> paged = fertilizerRepository.findAll(spec, page);
        return paged.getContent();
    }

    //Tìm phân bón chưa delete
    public List<Fertilizer> FertilizerNotDelete() {
        Specification<Fertilizer> spec = Specification.where(FertilizerSpecifiation.isNotDelete());
        Pageable page = PageRequest.of(0, sizeOfPage);
        Page<Fertilizer> paged = fertilizerRepository.findAll(spec, page);
        return paged.getContent();
    }

    public Page<Fertilizer> disPlayFertilizer(int numberOfPage, int sizePage) {
        Specification<Fertilizer> spec = Specification.where(FertilizerSpecifiation.isNotDelete());
        Pageable page = PageRequest.of(numberOfPage, sizePage);
        Page<Fertilizer> paged = fertilizerRepository.findAll(spec, page);
        return paged;
    }


    public String checkExistForAdd(Fertilizer fertilizer) {
        if (fertilizer.getNameFertilizer().isEmpty()) {
            return "Name not found";
        } else if (fertilizer.getPrice() == 0) {
            return "Price not found";
        } else if (fertilizer.getDetails().isEmpty()) {
            return "Details not found";
        } else if (fertilizer.getDescription().isEmpty()) {
            return "Description not found";
        } else if (fertilizer.getFileImageRepresent() == null) {
            return "Image represent not found";
        } else if (fertilizer.getFileImageOptional() == null) {
            return "Image optional not found";
        } else if (fertilizer.getDonViTinh().isEmpty()) {
            return "Don vi tinh not found";
        } else if (fertilizer.getThanhPhan().isEmpty()) {
            return "Thanh phan not found";
        }
        return "OK";
    }

    public String checkExistForEdit(Fertilizer fertilizer) {
        if (fertilizer.getNameFertilizer().isEmpty()) {
            return "Name not found";
        } else if (fertilizer.getPrice() == 0) {
            return "Price not found";
        } else if (fertilizer.getDetails().isEmpty()) {
            return "Details not found";
        } else if (fertilizer.getDescription().isEmpty()) {
            return "Description not found";
        } else if (fertilizer.getDonViTinh().isEmpty()) {
            return "Don vi tinh not found";
        } else if (fertilizer.getThanhPhan().isEmpty()) {
            return "Thanh phan not found";
        }
        return "OK";
    }

    public Fertilizer getFertlizerByURL(String url){
        return fertilizerRepository.findAll().stream().filter(s -> s.getUrl().equals(url)).findFirst().get();
    }

    public List<Fertilizer> FertilizerDel() {
        Specification<Fertilizer> spec = Specification.where(FertilizerSpecifiation.isDelete());
        Pageable page = PageRequest.of(0, sizeOfPage);
        Page<Fertilizer> paged = fertilizerRepository.findAll(spec, page);
        return paged.getContent();
    }

    private Map<Character, Character> checkAccented() {
        Map<Character, Character> map = new HashMap<>();
        map.put('á', 'a');
        map.put('à', 'a');
        map.put('ạ', 'a');
        map.put('ă', 'a');
        map.put('â', 'a');
        map.put('ả', 'a');
        map.put('ã', 'a');
        map.put('ằ', 'a');
        map.put('ắ', 'a');
        map.put('ặ', 'a');
        map.put('ẵ', 'a');
        map.put('ẳ', 'a');
        map.put('ầ', 'a');
        map.put('ấ', 'a');
        map.put('ậ', 'a');
        map.put('ẫ', 'a');
        map.put('ẩ', 'a');
        map.put('ó', 'o');
        map.put('ò', 'o');
        map.put('ọ', 'o');
        map.put('ơ', 'o');
        map.put('ỏ', 'o');
        map.put('õ', 'o');
        map.put('ờ', 'o');
        map.put('ớ', 'o');
        map.put('ợ', 'o');
        map.put('ỡ', 'o');
        map.put('ơ', 'o');
        map.put('ở', 'o');
        map.put('ô', 'o');
        map.put('ồ', 'o');
        map.put('ố', 'o');
        map.put('ộ', 'o');
        map.put('ỗ', 'o');
        map.put('ổ', 'o');
        map.put('đ', 'd');
        map.put('é', 'e');
        map.put('è', 'e');
        map.put('ẽ', 'e');
        map.put('ẻ', 'e');
        map.put('ê', 'e');
        map.put('ể', 'e');
        map.put('ề', 'e');
        map.put('ế', 'e');
        map.put('ễ', 'e');
        map.put('ệ', 'e');
        map.put('í', 'i');
        map.put('ì', 'i');
        map.put('ị', 'i');
        map.put('ĩ', 'i');
        map.put('ỳ', 'y');
        map.put('ý', 'y');
        map.put('ỹ', 'y');
        map.put('ỵ', 'y');
        return map;
    }

    public String generateUrl(String url) {
        url = url.replace(' ', '-');
        char[] urls = url.toCharArray();
        Map<Character, Character> alpha = checkAccented();
        StringBuilder resultURL = new StringBuilder();
        for (char c : urls) {
            if (c == '-') {
                resultURL.append(c);
                continue;
            }
            c = Character.toLowerCase(c);
            if (alpha.containsKey(c)) {
                c = alpha.get(c);
            }
            resultURL.append(c);
        }
        return resultURL.toString();
    }

    //Xử lý đa luồng
    public CompletableFuture<String> uploadImageAsync(MultipartFile file) throws IOException {
        return CompletableFuture.supplyAsync(() ->
        {
            try {
                return UploadImage(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public String UploadImage(MultipartFile image) throws IOException {
        return cloudinary.uploader().upload(image.getBytes(), Map.of()).get("url").toString();
    }
}
