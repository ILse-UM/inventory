package com.um.inventory.controller;

import com.um.inventory.dto.ItemCreationDto;
import com.um.inventory.dto.ItemResponseDto;
import com.um.inventory.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("item")
    public ResponseEntity<List<ItemResponseDto>> getItems(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size
    ){
        return new ResponseEntity<>(itemService.getAllItem(page, size), HttpStatus.OK);
    }

    @GetMapping("item/{id}")
    public ResponseEntity<Optional<ItemResponseDto>> getItemById(@PathVariable("id") int id){
        return new ResponseEntity<>(itemService.getItem(id), HttpStatus.OK);
    }

    @PostMapping("item/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ItemResponseDto> createItem(@RequestBody ItemCreationDto itemCreationDto){
        return new ResponseEntity<>(itemService.addItem(itemCreationDto), HttpStatus.CREATED);
    }

    @PutMapping("item/{id}/update")
    public ResponseEntity<ItemResponseDto> updateItem(@RequestBody ItemCreationDto itemCreationDto, @PathVariable("id") int id){
        return new ResponseEntity<>(itemService.updateItem(itemCreationDto, id), HttpStatus.OK);
    }

    @DeleteMapping("item/{id}/delete")
    public ResponseEntity<String> deleteItem(@PathVariable("id") int id){
        itemService.deleteItem(id);
        return new ResponseEntity<>("Item deleted", HttpStatus.OK);
    }
}
