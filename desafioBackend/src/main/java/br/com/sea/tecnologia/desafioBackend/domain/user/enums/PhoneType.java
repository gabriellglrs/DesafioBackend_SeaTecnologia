package br.com.sea.tecnologia.desafioBackend.domain.user.enums;

import br.com.sea.tecnologia.desafioBackend.exceptions.handler.error.PhoneTypeDeserializerError;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = PhoneTypeDeserializerError.class)
public enum PhoneType {
     RESIDENCIAL,
     CELULAR,
     COMERCIAL;
}
