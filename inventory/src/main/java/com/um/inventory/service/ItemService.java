package com.um.inventory.service;

import com.um.inventory.dto.ItemRequestDto;
import com.um.inventory.dto.ItemResponseDto;

import java.util.List;
import java.util.Optional;


public interface ItemService {
    ItemResponseDto addItem(ItemRequestDto itemRequestDto);
    List<ItemResponseDto> getAllItem(int page, int size);
    Optional<ItemResponseDto> getItem(int id);
    ItemResponseDto updateItem(ItemRequestDto itemRequestDto, int id);
    void deleteItem(int id);

}
