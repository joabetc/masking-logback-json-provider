package com.premonition.logging.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.core.JsonGenerator;
import com.premonition.logging.mask.MaskRules;
import net.logstash.logback.composite.JsonWritingUtils;
import net.logstash.logback.composite.loggingevent.MessageJsonProvider;

import java.io.IOException;

/**
 * Custom message json provider
 * @see net.logstash.logback.composite.loggingevent.MessageJsonProvider
 */
public class MaskingMessageProvider extends MessageJsonProvider {

  private MaskRules rules;

  @Override
  public void writeTo(JsonGenerator generator, ILoggingEvent event) throws IOException {
    JsonWritingUtils.writeStringField(generator, getFieldName(), rules.apply(event.getFormattedMessage()));
  }

  @SuppressWarnings("unused")
  public void setRules(MaskRules rules) {
    this.rules = rules;
  }
}
