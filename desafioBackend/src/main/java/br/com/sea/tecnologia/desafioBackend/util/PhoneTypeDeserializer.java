package br.com.sea.tecnologia.desafioBackend.util;

import br.com.sea.tecnologia.desafioBackend.domain.user.enums.PhoneType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Arrays;

public class PhoneTypeDeserializer extends JsonDeserializer<PhoneType> {

     @Override
     public PhoneType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
          String value = p.getText().toUpperCase();

          try {
               return PhoneType.valueOf(value);
          } catch (IllegalArgumentException e) {
               throw new IOException("Valor inválido para PhoneType: ( " + value + " ). Os valores esperados são: " +
                                     Arrays.toString(PhoneType.values()));
          }
     }
}
