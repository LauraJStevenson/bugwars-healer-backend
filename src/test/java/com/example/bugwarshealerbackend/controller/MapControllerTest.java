package com.example.bugwarshealerbackend.controller;

import com.example.bugwarshealerbackend.jpa.MapRepository;
import com.example.bugwarshealerbackend.model.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class MapControllerTest {

    @Mock
    private MapRepository mapRepository;

    @InjectMocks
    private MapController mapController;

    private MockMvc mockMvc;

    @Test
    void TestGetMaps() throws Exception{
       //Mock Data
        Map map1 = new Map();
        map1.setId(1L);
        map1.setName("Map 1");
        Map map2 = new Map();
        map2.setId(2L);
        map2.setName("Map 2");
        List<Map> maps = new ArrayList<>();
        maps.add(map1);
        maps.add(map2);

        //Mock Map Repository behavior
        when(mapRepository.findAll()).thenReturn(maps);

        //set up MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(mapController).build();

        //perform the get request and verify the response
        mockMvc.perform(get("/api/v1/maps/")).andExpect(status().isOk());

        // verify that the mapRepository.findAll() method was called
        verify(mapRepository, times(1)).findAll();
        verifyNoMoreInteractions(mapRepository);


    }
}