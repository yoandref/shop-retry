package io.github.yoandreferreira.shopretry.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ShopDTO {

    private String identifier;
    private String status;
    private String buyerIdentifier;
    private LocalDate dateShop;
    private List<ShopItemDTO> items;

}
