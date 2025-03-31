package br.com.dio.persistence.converter;

import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

import static java.time.ZoneOffset.UTC;
import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class OffsetDateTimeConverter {

    public static OffsetDateTime toOffsetDateTime(final Timestamp value){
        return nonNull(value) ? OffsetDateTime.ofInstant(value.toInstant(), UTC) : null; // Operador Ternário ? : → condição ? valor_se_verdadeiro : valor_se_falso
    } // value.toInstant() - Converte o objeto Timestamp em um objeto Instant. Um Instant representa um momento específico no tempo, como "2023-11-15T10:15:30Z", mas sem informações de fuso horário.
    // OffsetDateTime.ofInstant(instant, zoneOffset) - Este é um method estático da classe OffsetDateTime que cria um novo objeto OffsetDateTime a partir de um Instant e um ZoneOffset.
    public static Timestamp toTimestamp(final OffsetDateTime value){
        return nonNull(value) ? Timestamp.valueOf(value.atZoneSameInstant(UTC).toLocalDateTime()) : null; // value.atZoneSameInstant(UTC) - Este method converte o OffsetDateTime para um ZonedDateTime no fuso horário UTC, mantendo o mesmo instante no tempo. Um ZonedDateTime é similar ao OffsetDateTime, mas inclui informações completas de fuso horário, não apenas o deslocamento.
    } // .toLocalDateTime() - Converte o ZonedDateTime para um LocalDateTime, que é uma data e hora sem informações de fuso horário. Esta conversão descarta as informações de fuso horário, mas mantém os valores de ano, mês, dia, hora, minuto, segundo e fração de segundo.
    // Timestamp.valueOf(localDateTime) - Este é um method estático da classe java.sql.Timestamp que cria um novo objeto Timestamp a partir de um LocalDateTime. O Timestamp resultante representa o mesmo momento no tempo, mas no formato esperado pelo JDBC para interagir com bancos de dados.

}
