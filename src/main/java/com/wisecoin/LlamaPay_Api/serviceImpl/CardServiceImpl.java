package com.wisecoin.LlamaPay_Api.serviceImpl;

import com.wisecoin.LlamaPay_Api.dtos.CardDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.CardRequestDTO;
import com.wisecoin.LlamaPay_Api.entities.Card;
import com.wisecoin.LlamaPay_Api.entities.Client;
import java.time.format.DateTimeParseException;
import java.time.YearMonth;

import com.wisecoin.LlamaPay_Api.entities.Goal;
import com.wisecoin.LlamaPay_Api.exceptions.ResourceNotFoundException;
import com.wisecoin.LlamaPay_Api.exceptions.ValidationException;
import com.wisecoin.LlamaPay_Api.repositories.CardRepository;
import com.wisecoin.LlamaPay_Api.repositories.ClientRepository;
import com.wisecoin.LlamaPay_Api.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    CardRepository cardRepository;

    @Autowired
    ClientRepository clientRepository;

    public void validarExpirationDate(YearMonth expirationDate){
        YearMonth currentMonth = YearMonth.now();
        if (expirationDate.isBefore(currentMonth)) {
            throw new ValidationException("La fecha de expiración no puede ser anterior a la actual");
        }
    }

    public Long cardIdByCardNumber(String cardNumber){
        List<Card> listDuplicados = cardRepository.findByCardNumber(cardNumber);
        if (!listDuplicados.isEmpty()) {
            return listDuplicados.get(0).getId();
        }
        return null;
    }

    @Override
    public Card addCard(Long clientId, CardDTO cardDto) {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client==null) {
            throw new ResourceNotFoundException("Cliente no encontrado");
        }
        YearMonth today = YearMonth.from(LocalDate.now());

        //Validar cardNumber
        if(!(cardDto.getCardNumber().matches("\\d+"))|| cardDto.getCardNumber().length() != 16){
            throw new ValidationException("El numero de tarjeta debe tener exactamente 16 dígitos");
        }
        if(cardRepository.existsByCardNumber(cardDto.getCardNumber())){
            throw new ValidationException("El numero de tarjeta ya se encuentra registrado");
        }

        //Validar ownerName
        if(cardDto.getOwnerName().length()<=10){
            throw new ValidationException("El nombre del propietario debe contener el nombre completo");
        }
        //Validar expirationDate
        try{
            YearMonth expirationDate = YearMonth.parse(cardDto.getExpirationDate(), FORMATTER);
            validarExpirationDate(expirationDate);

        } catch (DateTimeParseException e){
            throw new ValidationException("El formato de la fecha de expiración es inválido");
        }

        //validar cvv
        if(!(cardDto.getCvv().matches("\\d+"))|| cardDto.getCvv().length() != 3){
            throw new ValidationException("El cvv debe tener exactamente 3 dígitos");
        }

        Card card = new Card(cardDto.getId(), cardDto.getCardNumber(), cardDto.getOwnerName(), cardDto.getExpirationDate(),
                cardDto.getCvv(),client);

        card.setClient(client);
        return cardRepository.save(card);
    }

    @Override
    public Card findByClient(Long clientId) {
        Client client = clientRepository.findById(clientId).orElse(null);
        if(client==null){
            throw new ValidationException("Cliente no encontrado");
        }
        return cardRepository.findByClient(client);
    }

    @Override
    public CardRequestDTO findResponseByClient(Long clientId) {
        Card card = findByClient(clientId);
        CardRequestDTO cardRequestDTO = new CardRequestDTO(card.getId(), card.getCardNumber(),
                card.getOwnerName(),card.getExpirationDate(), card.getCvv());
        return cardRequestDTO;
    }

    @Override
    public List<Card> listAll() {
        return cardRepository.findAll();
    }

    @Override
    public void deleteCard(Long id) {
        Card cardDelete = getCardById(id);
        cardRepository.deleteById(id);
    }

    @Override
    public Card getCardById(Long id) {
        Card card = cardRepository.findById(id).orElse(null);
        if (card==null) {
            throw new ResourceNotFoundException("Tarjeta no encontrada");
        }
        return card;
    }

    @Override
    public Card updateCard(Long id, CardRequestDTO cardRequestDto) {
        Card cardFound = getCardById(id);
        if(cardFound!=null){

            //Validando cardNumber
            if(!cardRequestDto.getCardNumber().isBlank()){
                if(!(cardRequestDto.getCardNumber().matches("\\d+"))|| cardRequestDto.getCardNumber().length() != 16){
                    throw new ValidationException("El numero de tarjeta debe tener exactamente 16 dígitos");
                }
                Long existingCardId = cardIdByCardNumber(cardRequestDto.getCardNumber());
                if (existingCardId != null && !existingCardId.equals(id)) {
                    throw new ValidationException("El numero de tarjeta ya se encuentra registrado");
                }
                cardFound.setCardNumber(cardRequestDto.getCardNumber());
            }

            //Validando ownerName
            if(!cardRequestDto.getOwnerName().isBlank()){
                if(cardRequestDto.getOwnerName().length()<=10){
                    throw new ValidationException("El nombre del propietario debe contener el nombre completo");
                }
                cardFound.setOwnerName(cardRequestDto.getOwnerName());
            }

            //Validando expirationDate
            if(cardRequestDto.getExpirationDate()!=null && !cardRequestDto.getExpirationDate().isBlank()){
                try{
                    YearMonth expirationDate = YearMonth.parse(cardRequestDto.getExpirationDate(), FORMATTER);
                    validarExpirationDate(expirationDate);

                } catch (DateTimeParseException e){
                    throw new ValidationException("El formato de la fecha de expiración es inválido");
                }
                cardFound.setExpirationDate(cardRequestDto.getExpirationDate());
            }

            //validando cvv
            if(!cardRequestDto.getCvv().isBlank()){
                if(!(cardRequestDto.getCvv().matches("\\d+"))|| cardRequestDto.getCvv().length() != 3){
                    throw new ValidationException("El cvv debe tener exactamente 3 dígitos");
                }
                cardFound.setCvv(cardRequestDto.getCvv());
            }
            return cardRepository.save(cardFound);
        }

        if(!cardRepository.existsById(id)){
            throw new ResourceNotFoundException("Tarjeta no encontrado");
        }
        return null;
    }
}
