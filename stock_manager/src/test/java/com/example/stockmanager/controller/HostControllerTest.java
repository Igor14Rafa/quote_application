package com.example.stockmanager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.HttpStatus; 

import com.example.stockmanager.model.Host;
import com.example.stockmanager.service.StockManagerService;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class HostControllerTest {

    @Mock
    private StockManagerService hostService;

    @InjectMocks
    private HostController hostController;

    @Test
    public void testGetAllHosts() {
        Host host1 = new Host();
        Host host2 = new Host();

        host1.setEndpoint("test");
        host2.setEndpoint("test2");

        host1.setHost("hosttest");
        host2.setHost("hosttest2");

        host1.setPort(161);
        host2.setPort(162);

        when(hostService.getAllHosts()).thenReturn(Arrays.asList(host1, host2));

        Iterable<Host> hosts = hostController.getAllHosts();

        assertNotNull(hosts);
        List<Host> hostList = (List<Host>) hosts;
        assertEquals(2, hostList.size());
        assertEquals(host1, hostList.get(0));
        assertEquals(host2, hostList.get(1));
    }

    @Test
    public void testAddNotification() {
        Host host = new Host();

        host.setEndpoint("test");
        host.setHost("hosttest");
        host.setPort(161);

        ResponseEntity<Host> expectedResponse = new ResponseEntity<>(host, HttpStatus.CREATED);

        when(hostService.addNotification(any(Host.class))).thenReturn(expectedResponse);

        ResponseEntity<Host> response = hostController.addNotification(host);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(host, response.getBody());
        verify(hostService).addNotification(host);
    }
}
