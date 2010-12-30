  private String asPlainText(Markup markup){
    if (markup ==  null) return "";

    StringWriter writer = new StringWriter();
    PlaintextSerializer serializer = new PlaintextSerializer(writer);
    markup.writeOn(serializer);

    return writer.toString();
  }