# Log Masking for JSON formatted events
[![Java CI with Maven](https://github.com/joabetc/masking-logback-json-provider/actions/workflows/maven.yml/badge.svg)](https://github.com/joabetc/masking-logback-json-provider/actions/workflows/maven.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=joabetc_masking-logback-json-provider&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=joabetc_masking-logback-json-provider)

Provides an extension to [Logstash JSON Encoder](https://github.com/logstash/logstash-logback-encoder) to mask sensitive contents of log events using rules like below:

```xml
<encoder class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
  <providers>
    <timestamp>
      <timeZone>UTC</timeZone>
    </timestamp>
    <provider class="com.premonition.logging.logback.MaskingMessageProvider">
      <rules>
        <rule>
          <name>credit card</name>
          <pattern>\d{13,18}</pattern>
          <unmasked>4</unmasked>
          <position>END</position>
        </rule>
        <rule>
          <name>SSN</name>
          <pattern>\d{3}-?\d{3}-?\d{4}</pattern>
        </rule>
      </rules>
    </provider>
    <stackTrace/>
    <pattern>
      <pattern>
        {
        "severity": "%level",
        "thread": "%thread",
        "class": "%logger{40}"
        }
      </pattern>
    </pattern>
  </providers>
</encoder>
```

## Rule tags

| tag        | description                                                                      |
|------------|----------------------------------------------------------------------------------|
| `name`     | an optional friendly name for the rule                                           |
| `prefix`   | an optional literal prefix preceding the actual search pattern                   |
| `suffix`   | an optional literal suffix following the actual search pattern                   |
| `pattern`  | a regular expression pattern to identify the personally identifiable information |
| `unmasked` | the number of characters to leave unmasked                                       |
| `position` | the position of the mask                                                         |

## Samples

### Position BEGIN (default)
```xml
<rule>
  <pattern>\d{3}-?\d{3}-?\d{4}</pattern>
  <unmasked>4</unmasked><!-- 4 digits will remain unmasked -->
  <position>BEGIN</position><!-- mask position -->
</rule>
```
* input: `123-123-1234`
* output: `********1234`

### Position END
```xml
<rule>
  <pattern>\d{3}-?\d{3}-?\d{4}</pattern>
  <unmasked>4</unmasked><!-- 4 digits will remain unmasked -->
  <position>END</position><!-- mask position -->
</rule>
```
* input: `123-123-1234`
* output: `123-********`

## Also see

* [Logstash JSON Encoder](https://github.com/logstash/logstash-logback-encoder)
* [Logback](https://logback.qos.ch)
