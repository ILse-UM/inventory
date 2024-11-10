package com.um.inventory.service.impl;

import com.um.inventory.dto.ItemRequestDto;
import com.um.inventory.dto.ItemResponseDto;
import com.um.inventory.model.*;
import com.um.inventory.repository.CategoryRepository;
import com.um.inventory.repository.ItemLogRepository;
import com.um.inventory.repository.ItemRepository;
import com.um.inventory.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {


    private final CategoryRepository categoryRepository;
    ItemRepository itemRepository;

    ItemLogRepository itemLogRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ItemLogRepository itemLogRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.itemLogRepository = itemLogRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ItemResponseDto addItem(ItemRequestDto itemRequestDto) {
        Item item = toItem(itemRequestDto);
        Item itemSaved = itemRepository.save(item);

        ItemLog log = new ItemLog();
        log.setItem(itemSaved);
        log.setPreviousAmount(0);
        log.setCurrentAmount(itemSaved.getAmount());
        log.setChange(Change.PLUS);
        log.setActionType(ActionType.ADD);
        itemLogRepository.save(log);

        return toItemDto(itemSaved);
    }


    @Override
    public List<ItemResponseDto> getAllItem(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Item> items = itemRepository.findAll(pageable);
        return items.getContent().stream().map(this::toItemDto).toList();
    }

    @Override
    public Optional<ItemResponseDto> getItem(int id) {
        Optional<ItemResponseDto> response = itemRepository.findById(id).map(this::toItemDto);
        return response;
    }

    @Override
    public ItemResponseDto updateItem(ItemRequestDto itemRequestDto, int id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item tidak ditemukan"));

        ItemLog log = new ItemLog();
        log.setItem(item);
        log.setPreviousAmount(item.getAmount());
        log.setCurrentAmount(itemRequestDto.getAmount());
        log.setChange(itemRequestDto.getAmount() > item.getAmount() ? Change.PLUS : Change.MINUS);
        log.setActionType(ActionType.UPDATE);

        itemLogRepository.save(log);

        item.setBarcode(itemRequestDto.getBarcode());
        item.setName(itemRequestDto.getName());
        Category category = categoryRepository.findByName(itemRequestDto.getCategory()).orElse(null);
        item.setCategory(category);
        item.setDescription(itemRequestDto.getDescription());
        item.setAmount(itemRequestDto.getAmount());
        item.setPurchasePrice(itemRequestDto.getPurchasePrice());
        item.setSellPrice(itemRequestDto.getSellPrice());
        item.setImage(itemRequestDto.getImage());
        Item itemUpdated = itemRepository.save(item);

        return toItemDto(itemUpdated);
    }

    @Override
    public void deleteItem(int id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item tidak ditemukan"));

        ItemLog log = new ItemLog();
        log.setItem(item);
        log.setPreviousAmount(item.getAmount());
        log.setCurrentAmount(0);
        log.setChange(Change.MINUS);
        log.setActionType(ActionType.DELETE);
        itemLogRepository.save(log);

        itemRepository.deleteById(id);
    }

    private Item toItem(ItemRequestDto itemRequestDto) {
        Category category = categoryRepository.findByName(itemRequestDto.getCategory()).orElse(null);
        return Item.builder()
                .barcode(itemRequestDto.getBarcode())
                .name(itemRequestDto.getName())
                .category(category)
                .description(itemRequestDto.getDescription())
                .amount(itemRequestDto.getAmount())
                .purchasePrice(itemRequestDto.getPurchasePrice())
                .sellPrice(itemRequestDto.getSellPrice())
                .image(itemRequestDto.getImage())
                .build();
    }

    private ItemResponseDto toItemDto(Item item) {
        Category category = categoryRepository.findByName(item.getCategory().getName()).orElse(null);
        assert category != null;
        return ItemResponseDto.builder()
                .id(item.getId())
                .barcode(item.getBarcode())
                .name(item.getName())
                .category(String.valueOf(category.getName()))
                .description(item.getDescription())
                .amount(item.getAmount())
                .purchasePrice(item.getPurchasePrice())
                .sellPrice(item.getSellPrice())
                .image(item.getImage())
                .build();
    }
}
