package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.request.NewItemRequest;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemRequestServiceImpl implements ItemRequestService{
    private final ItemRequestRepository itemRequestRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ItemRequestDto create(Long userId, NewItemRequest newItemRequest) {
        log.info("Создаем новый запрос вещи");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден"));

        ItemRequest itemRequest = itemRequestRepository.save(ItemRequestMapper.mapItemRequest(newItemRequest, user));

        return ItemRequestMapper.mapToItemRequestDto(itemRequest, new ArrayList<>());
    }

    @Override
    public List<ItemRequestDto> getUserItemRequest(Long userId) {
        log.info("Возвращаем все запросы пользователя {}", userId);

        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("Пользователь " + userId + " не найден");
        }

        List<ItemRequest> itemRequests = itemRequestRepository.findByOwnerId(userId);
        List<ItemRequestDto> itemRequestDtos = new ArrayList<>();

        for (ItemRequest itemRequest: itemRequests) {
            List<Item> items = itemRepository.findByRequest(itemRequest.getId());
            itemRequestDtos.add(ItemRequestMapper.mapToItemRequestDto(itemRequest, items));
        }

        return itemRequestDtos;
    }

    @Override
    public ItemRequestDto getItemRequest(Long userId, Long requestId) {
        log.info("Возвращаем запрос {} пользователю {}", requestId, userId);

        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("Пользователь " + userId + " не найден");
        }

        ItemRequest itemRequest = itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Запрос с id " + requestId + " не найден"));


        List<Item> items = itemRepository.findByRequest(requestId);

        return ItemRequestMapper.mapToItemRequestDto(itemRequest, items);
    }

    @Override
    public List<ItemRequestDto> getItemRequestAll(Long userId) {
        log.info("Возвращаем все запросы других пользователей пользователю {}", userId);

        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("Пользователь " + userId + " не найден");
        }

        List<ItemRequest> itemRequests = itemRequestRepository.findByOwnerIdNot(userId);
        List<ItemRequestDto> itemRequestDtos = new ArrayList<>();

        for (ItemRequest itemRequest: itemRequests) {
            List<Item> items = itemRepository.findByRequest(itemRequest.getId());
            itemRequestDtos.add(ItemRequestMapper.mapToItemRequestDto(itemRequest, items));
        }

        return itemRequestDtos;
    }

}
