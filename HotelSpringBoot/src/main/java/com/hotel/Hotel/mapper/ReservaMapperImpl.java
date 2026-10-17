package com.hotel.Hotel.mapper;

import com.hotel.Hotel.domain.Reserva;
import com.hotel.Hotel.dto.response.ReservaResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservaMapperImpl implements ReservaMapper {

    @Override
    public ReservaResponse toResponse(Reserva reserva) {
        if (reserva == null) {
            return null;
        }
        return new ReservaResponse(
                reserva.getId(),
                reserva.getCliente().getNombre(),
                reserva.getHabitacion().getNumero(),
                reserva.getPeriodo().fechaInicio(),
                reserva.getPeriodo().fechaFin(),
                reserva.getCostoTotal(),
                reserva.getEstado().name());
    }

    @Override
    public List<ReservaResponse> toResponseList(List<Reserva> reservas) {
        return reservas.stream().map(this::toResponse).toList();
    }
}
