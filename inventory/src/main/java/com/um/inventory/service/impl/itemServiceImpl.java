package com.um.inventory.service.impl;

import com.um.inventory.dto.ItemCreationDto;
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
public class itemServiceImpl implements ItemService {


    private final CategoryRepository categoryRepository;
    ItemRepository itemRepository;

    ItemLogRepository itemLogRepository;

    @Autowired
    public itemServiceImpl(ItemRepository itemRepository, ItemLogRepository itemLogRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.itemLogRepository = itemLogRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ItemResponseDto addItem(ItemCreationDto itemCreationDto) {
        Item item = toItem(itemCreationDto);
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
    public ItemResponseDto updateItem(ItemCreationDto itemCreationDto, int id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item tidak ditemukan"));

        ItemLog log = new ItemLog();
        log.setItem(item);
        log.setPreviousAmount(item.getAmount());
        log.setCurrentAmount(itemCreationDto.getAmount());
        log.setChange(itemCreationDto.getAmount() > item.getAmount() ? Change.PLUS : Change.MINUS);
        log.setActionType(ActionType.UPDATE);

        itemLogRepository.save(log);

        item.setName(itemCreationDto.getName());
        Category category = categoryRepository.findByName(itemCreationDto.getCategory()).orElse(null);
        item.setCategory(category);
        item.setDescription(itemCreationDto.getDescription());
        item.setAmount(itemCreationDto.getAmount());
        item.setPurchasePrice(itemCreationDto.getPurchasePrice());
        item.setSellPrice(itemCreationDto.getSellPrice());
        item.setImage(itemCreationDto.getImage());
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

    private Item toItem(ItemCreationDto itemCreationDto) {
        Category category = categoryRepository.findByName(itemCreationDto.getCategory()).orElse(null);
        return Item.builder()
                .name(itemCreationDto.getName())
                .category(category)
                .description(itemCreationDto.getDescription())
                .amount(itemCreationDto.getAmount())
                .purchasePrice(itemCreationDto.getPurchasePrice())
                .sellPrice(itemCreationDto.getSellPrice())
                .image(itemCreationDto.getImage())
                .build();
    }

    private ItemResponseDto toItemDto(Item item) {
        return ItemResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .category(String.valueOf(item.getCategory().getName()))
                .description(item.getDescription())
                .amount(item.getAmount())
                .purchasePrice(item.getPurchasePrice())
                .sellPrice(item.getSellPrice())
                .image(item.getImage())
                .build();
    }
}
