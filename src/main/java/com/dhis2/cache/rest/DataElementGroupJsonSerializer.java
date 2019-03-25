package com.dhis2.cache.rest;

import java.io.IOException;
import java.util.List;

import com.dhis2.cache.data.DataElementGroup;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class DataElementGroupJsonSerializer extends StdSerializer<List<DataElementGroup>> {

  public DataElementGroupJsonSerializer(Class<List<DataElementGroup>> t) {
    super(t);
}

  @Override
  public void serialize(
    List<DataElementGroup> groups,
    JsonGenerator jsonGenerator,
    SerializerProvider serializerProvider
  ) throws IOException {
    jsonGenerator.writeString(String.format("[%s]", "Hallo Welt"));
  }
  
}
