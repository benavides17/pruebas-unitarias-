package com.hotel.Hotel.mapper;

import com.hotel.Hotel.domain.Cliente;
import com.hotel.Hotel.dto.request.CrearClienteRequest;
import com.hotel.Hotel.dto.response.ClienteResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClienteMapperImpl implements ClienteMapper {

    @Override
    public ClienteResponse toResponse(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.isActivo(),
                cliente.getPenalizaciones());
    }

    @Override
    public List<ClienteResponse> toResponseList(List<Cliente> clientes) {
        return clientes.stream().map(this::toResponse).toList();
    }

    @Override
    public Cliente toEntity(CrearClienteRequest request) {
        if (request == null) {
            return null;
        }
        return new Cliente(request.nombre(), request.email());
    }
}
