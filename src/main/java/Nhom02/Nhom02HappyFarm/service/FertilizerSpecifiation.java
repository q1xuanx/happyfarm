package Nhom02.Nhom02HappyFarm.service;

import Nhom02.Nhom02HappyFarm.entities.Fertilizer;
import org.springframework.data.jpa.domain.Specification;

public class FertilizerSpecifiation{
    public static Specification<Fertilizer> hasBrand(String brand){
        return (root, query, criteriaBuilder) -> brand == null || brand.isEmpty() ?
                criteriaBuilder.conjunction() :
                criteriaBuilder.equal(root.get("brandName").get("nameBrand"), brand);
    }
    public static Specification<Fertilizer> hasOrigin(String origin){
        return (root, query, criteriaBuilder) -> origin == null || origin.isEmpty() ?
                criteriaBuilder.conjunction() :
                criteriaBuilder.equal(root.get("originFer").get("nameOrigin"), origin);
    }
    public static Specification<Fertilizer> hasType(String type){
        return (root, query, criteriaBuilder) -> type == null || type.isEmpty() ?
                criteriaBuilder.conjunction() :
                criteriaBuilder.equal(root.get("type").get("nameTypeFertilizer"), type);
    }
    public static Specification<Fertilizer> hasName(String name){
        return (root, query, criteriaBuilder) -> name == null || name.isEmpty() ?
                criteriaBuilder.conjunction() :
                criteriaBuilder.equal(root.get("nameFertilizer"), name);
    }
    public static Specification<Fertilizer> isNotDelete(){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isDelete"), false);
    }
}
