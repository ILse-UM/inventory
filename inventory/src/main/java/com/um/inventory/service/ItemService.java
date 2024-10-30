package com.um.inventory.service;

import com.um.inventory.dto.ItemCreationDto;
import com.um.inventory.dto.ItemResponseDto;

import java.util.List;
import java.util.Optional;


public interface ItemService {
    ItemResponseDto addItem(ItemCreationDto itemCreationDto);
    List<ItemResponseDto> getAllItem(int page, int size);
    Optional<ItemResponseDto> getItem(int id);
    ItemResponseDto updateItem(ItemCreationDto itemCreationDto, int id);
    void deleteItem(int id);

}
