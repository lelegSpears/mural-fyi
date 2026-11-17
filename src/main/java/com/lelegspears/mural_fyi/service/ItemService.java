package com.lelegspears.mural_fyi.service;

import com.lelegspears.mural_fyi.entity.Item;
import com.lelegspears.mural_fyi.entity.dto.ItemUpdateDTO;
import com.lelegspears.mural_fyi.entity.enums.ItemStatus;
import com.lelegspears.mural_fyi.exception.BusinessRuleException;
import com.lelegspears.mural_fyi.exception.ResourceNotFoundException;
import com.lelegspears.mural_fyi.repository.ItemRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional(readOnly = true)
    public List<Item> findAll() {
        return itemRepository.findAll(Sort.by(Sort.Direction.ASC, "localFound"));
    }

    @Transactional(readOnly = true)
    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com o ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Item> findByName(String name) {
        return itemRepository.findByName(name);
    }

    @Transactional
    public Item create(Item item) {
        return itemRepository.save(item);
    }

    @Transactional
    public void patchById(Long id, ItemUpdateDTO dto) {
        Item itemEntity = findById(id);
        itemEntity.updateItemDetails(dto);
    }

    @Transactional
    public void markAsRedeemed(Long id) {
        Item itemEntity = findById(id);

        if (itemEntity.getStatus() == ItemStatus.REDEEMED) {
            throw new BusinessRuleException("Este item já foi marcado como resgatado anteriormente.");
        }

        itemEntity.setRedeemed();
    }

    @Transactional
    public void deleteById(Long id) {
        Item itemEntity = findById(id);
        itemRepository.delete(itemEntity);
    }
}