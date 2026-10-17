package com.hotel.Hotel.service;

import com.hotel.Hotel.domain.Cliente;
import com.hotel.Hotel.dto.request.CrearClienteRequest;
import com.hotel.Hotel.dto.response.ClienteResponse;
import com.hotel.Hotel.mapper.ClienteMapper;
import com.hotel.Hotel.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void debeCrearClienteYRetornarRespuesta() {
        CrearClienteRequest request = new CrearClienteRequest("Ana Torres", "ana@correo.com");
        Cliente clienteGuardado = new Cliente(request.nombre(), request.email());
        ClienteResponse respuesta = new ClienteResponse(
                clienteGuardado.getId(), request.nombre(), request.email(), true, 0);

        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteGuardado);
        when(clienteMapper.toResponse(clienteGuardado)).thenReturn(respuesta);

        ClienteResponse resultado = clienteService.crear(request);

        assertNotNull(resultado);
        assertEquals("Ana Torres", resultado.nombre());
        verify(clienteRepository).save(any(Cliente.class));
        verify(clienteMapper).toResponse(clienteGuardado);
    }

    @Test
    void debeListarTodosLosClientes() {
        Cliente cliente = new Cliente("Luis Perez", "luis@correo.com");
        ClienteResponse respuesta = new ClienteResponse(
                cliente.getId(), cliente.getNombre(), cliente.getEmail(), true, 0);

        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        when(clienteMapper.toResponseList(List.of(cliente))).thenReturn(List.of(respuesta));

        List<ClienteResponse> resultado = clienteService.listarTodos();

        assertEquals(1, resultado.size());
        assertEquals("Luis Perez", resultado.get(0).nombre());
        verify(clienteRepository).findAll();
        verify(clienteMapper).toResponseList(List.of(cliente));
    }

    @Test
    void debeRetornarListaVaciaCuandoNoHayClientes() {
        when(clienteRepository.findAll()).thenReturn(List.of());
        when(clienteMapper.toResponseList(List.of())).thenReturn(List.of());

        List<ClienteResponse> resultado = clienteService.listarTodos();

        assertEquals(0, resultado.size());
        verify(clienteRepository).findAll();
    }

    @Test
    void debeObtenerClientePorId() {
        UUID id = UUID.randomUUID();
        Cliente cliente = new Cliente("Marta Ruiz", "marta@correo.com");
        ClienteResponse respuesta = new ClienteResponse(
                cliente.getId(), cliente.getNombre(), cliente.getEmail(), true, 0);

        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        when(clienteMapper.toResponse(cliente)).thenReturn(respuesta);

        ClienteResponse resultado = clienteService.obtenerPorId(id);

        assertEquals("Marta Ruiz", resultado.nombre());
        verify(clienteRepository).findById(id);
        verify(clienteMapper).toResponse(cliente);
    }

    @Test
    void debeLanzarExcepcionSiClienteNoExiste() {
        UUID id = UUID.randomUUID();
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> clienteService.obtenerPorId(id));

        assertEquals("Cliente no encontrado con ID: " + id, excepcion.getMessage());
        verify(clienteRepository).findById(id);
    }
}
