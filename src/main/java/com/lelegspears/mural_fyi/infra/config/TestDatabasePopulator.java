package com.lelegspears.mural_fyi.infra.config;

import com.lelegspears.mural_fyi.entity.Item;
import com.lelegspears.mural_fyi.entity.User;
import com.lelegspears.mural_fyi.entity.enums.ItemCategory;
import com.lelegspears.mural_fyi.entity.enums.ItemStatus;
import com.lelegspears.mural_fyi.repository.ItemRepository;
import com.lelegspears.mural_fyi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class TestDatabasePopulator implements CommandLineRunner {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User admin = new User(
                    "Administrador",
                    "admin",
                    passwordEncoder.encode("123456")
            );
            userRepository.save(admin);
        }
        if (itemRepository.count() > 0) {
            return;
        }

        String redeemLocal = "Sala de Achados e Perdidos - Bloco Central";

        Item item1 = new Item(
                "Garrafa Térmica Stanley Preta",
                "Garrafa preta de 750ml com adesivo de programação na lateral.",
                ItemCategory.ACCESSORIES,
                "Biblioteca - 2º andar",
                LocalDate.now().minusDays(2),
                redeemLocal,
                "https://exemplo.com/imagens/garrafa.jpg",
                ItemStatus.UNCLAIMED
        );

        Item item2 = new Item(
                "Carregador Notebook USB-C 65W",
                "Carregador original Lenovo encontrado próximo às tomadas do auditório.",
                ItemCategory.ELECTRONICS,
                "Auditório Principal",
                LocalDate.now().minusDays(1),
                redeemLocal,
                null,
                ItemStatus.UNCLAIMED
        );

        Item item3 = new Item(
                "Carteira de Identidade (RG)",
                "Documento encontrado próximo à catraca de entrada.",
                ItemCategory.DOCUMENTS,
                "Portaria Principal",
                LocalDate.now().minusDays(5),
                redeemLocal,
                null,
                ItemStatus.REDEEMED
        );
        item3.setRedeemed();

        Item item4 = new Item(
                "Moletom Cinza com Capuz",
                "Moletom tamanho G esquecido na arquibancada da quadra.",
                ItemCategory.CLOTHING,
                "Ginásio de Esportes",
                LocalDate.now().minusDays(3),
                redeemLocal,
                "https://exemplo.com/imagens/moletom.jpg",
                ItemStatus.UNCLAIMED
        );

        Item item5 = new Item(
                "Caderno Universitário Espiral",
                "Caderno capa dura azul com anotações de Estrutura de Dados.",
                ItemCategory.OTHER,
                "Cantina Central",
                LocalDate.now(),
                redeemLocal,
                null,
                ItemStatus.UNCLAIMED
        );

        itemRepository.saveAll(List.of(item1, item2, item3, item4, item5));
    }
}