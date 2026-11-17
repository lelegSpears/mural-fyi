package com.lelegspears.mural_fyi.controller;

import com.lelegspears.mural_fyi.entity.Item;
import com.lelegspears.mural_fyi.entity.dto.ItemUpdateDTO;
import com.lelegspears.mural_fyi.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<Item>> findAll() {
        return ResponseEntity.ok(itemService.findAll());
    }

    @GetMapping(params = "name")
    public ResponseEntity<List<Item>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(itemService.findByName(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> findById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Item> create(
            @RequestBody @Valid Item item,
            UriComponentsBuilder uriBuilder) {

        Item createdItem = itemService.create(item);

        URI uri = uriBuilder
                .path("/items/{id}")
                .buildAndExpand(createdItem.getItemId())
                .toUri();

        return ResponseEntity.created(uri).body(createdItem);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchById(
            @PathVariable Long id,
            @RequestBody @Valid ItemUpdateDTO dto) {

        itemService.patchById(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/redeem")
    public ResponseEntity<Void> markAsRedeemed(@PathVariable Long id) {
        itemService.markAsRedeemed(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        itemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}