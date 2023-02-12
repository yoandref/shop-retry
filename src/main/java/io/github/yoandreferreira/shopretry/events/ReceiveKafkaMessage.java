package io.github.yoandreferreira.shopretry.events;

import io.github.yoandreferreira.shopretry.dto.ShopDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiveKafkaMessage {

    private final KafkaTemplate<String, ShopDTO> kafkaTemplate;
    private static final String SHOP_TOPIC = "SHOP_TOPIC";
    private static final String SHOP_TOPIC_RETRY = "SHOP_TOPIC_RETRY";

    @KafkaListener(topics = SHOP_TOPIC, groupId = "group_report")
    public void listenShopTopic(ShopDTO shopDTO) {
        log.info("Compra recebida no tópic {}.", shopDTO.getIdentifier());
        try{
            if(shopDTO.getItems() == null || shopDTO.getItems().isEmpty()) {
                log.info("Compra sem items.");
                throw new Exception();
            }
        } catch (Exception ex) {
            log.info("Erro na aplicação.");
            kafkaTemplate.send(SHOP_TOPIC_RETRY, shopDTO);
        }
    }

    @KafkaListener(topics = SHOP_TOPIC_RETRY, groupId = "group_retry")
    public void listenShopTopicRetry(ShopDTO shopDTO) {
        log.info("Retentativa de processamento: {}.", shopDTO.getIdentifier());
    }

}
