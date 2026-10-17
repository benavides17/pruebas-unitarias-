package com.hotel.Hotel.mapper;

import com.hotel.Hotel.domain.Reserva;
import com.hotel.Hotel.dto.response.ReservaResponse;

import java.util.List;

public interface ReservaMapper {

    ReservaResponse toResponse(Reserva reserva);

    List<ReservaResponse> toResponseList(List<Reserva> reservas);
}