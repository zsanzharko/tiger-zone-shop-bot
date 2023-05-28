package kz.tiger.zone.tigerzoneshopbot.service.item;

import kz.tiger.zone.tigerzoneshopbot.repository.ShopItemEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopItemServiceImpl implements ShopItemService{
    private final ShopItemEntityRepository itemRepository;
}
