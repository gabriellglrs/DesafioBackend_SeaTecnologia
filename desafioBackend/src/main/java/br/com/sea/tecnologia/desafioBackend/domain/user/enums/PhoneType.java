package br.com.sea.tecnologia.desafioBackend.domain.user.enums;

import br.com.sea.tecnologia.desafioBackend.util.PhoneTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = PhoneTypeDeserializer.class)
public enum PhoneType {
     RESIDENCIAL,
     CELULAR,
     COMERCIAL;
}
