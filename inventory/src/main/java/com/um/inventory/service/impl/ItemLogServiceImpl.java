package com.um.inventory.service.impl;

import com.um.inventory.dto.ItemLogDto;
import com.um.inventory.dto.ItemResponseDto;
import com.um.inventory.model.ItemLog;
import com.um.inventory.repository.ItemLogRepository;
import com.um.inventory.service.ItemLogService;
import com.um.inventory.util.ImageUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemLogServiceImpl implements ItemLogService {
    public final ItemLogRepository itemLogRepository;

    public ItemLogServiceImpl(ItemLogRepository itemLogRepository) {
        this.itemLogRepository = itemLogRepository;
    }

    public List<ItemLogDto> getAllItemLog() {
        return itemLogRepository.findAll().stream().map(this::toItemLogDto).collect(Collectors.toList());
    }

    private ItemLogDto toItemLogDto(ItemLog itemLog) {
        ItemLogDto itemLogDto = new ItemLogDto();
        itemLogDto.setId(itemLog.getId());
        itemLogDto.setPreviousAmount(itemLog.getPreviousAmount());
        itemLogDto.setCurrentAmount(itemLog.getCurrentAmount());
        itemLogDto.setCreatedDate(itemLog.getCreatedDate().toString());
        itemLogDto.setChange(itemLog.getChange().toString());
        itemLogDto.setActionType(itemLog.getActionType().toString());

        ItemResponseDto itemDto = new ItemResponseDto();
        itemDto.setId(itemLog.getItem().getId());
        itemDto.setBarcode(itemLog.getItem().getBarcode());
        itemDto.setName(itemLog.getItem().getName());
        itemDto.setCategory(itemLog.getItem().getCategory().getName());
        itemDto.setDescription(itemLog.getItem().getDescription());
        itemDto.setAmount(itemLog.getItem().getAmount());
        itemDto.setPurchasePrice(itemLog.getItem().getPurchasePrice());
        itemDto.setSellPrice(itemLog.getItem().getSellPrice());
        itemDto.setImageBase64(ImageUtil.encodeToBase64(itemLog.getItem().getImage()));

        itemLogDto.setItem(itemDto);
        return itemLogDto;
    }
}
