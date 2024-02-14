package com.nnk.springboot.unitServiceTest;

import com.nnk.springboot.configuration.Data;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CurvePointServiceTest {
    
    @Autowired
    private CurvePointService curvePointService;
    
    @MockBean
    private CurvePointRepository curvePointRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private Data data;

    private final CurvePoint curvePoint = new CurvePoint(1, 10, LocalDate.now(), 10d, 35.0, LocalDate.now());
   private final List<CurvePoint> curvePoints = new ArrayList<>(List.of(curvePoint, new CurvePoint()));
    private final CurvePointDto curvePointDto = CurvePointDto.builder()
            .term(10d)
            .value(35d)
            .build();
    @Test
    void shouldSaveTest(){
        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);
        
        CurvePoint result = curvePointService.save(curvePoint);
        
        assertEquals(curvePoint.getId(), result.getId());
    }
    
    @Test
    void shouldSaveWithCurvePointDtoTest(){
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);
        
        CurvePoint result = curvePointService.save(curvePointDto);
        
        assertEquals(curvePoint.getId(), result.getId());
    }
    
    @Test
    void shouldGetByIdTest(){
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
        
        CurvePoint result = curvePointService.getById(1);
        
        assertEquals(curvePoint.getCurveId(), result.getCurveId());
    }
    
    @Test
    void shouldReturnExceptionIfGetByIdNotFoundCurvePointTest(){
        Integer id = 1;
        when(curvePointRepository.findById(id)).thenReturn(Optional.empty());
        
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> curvePointService.getById(id));
        
        assertTrue("CurvePoint id: 1 not found.".contains(runtimeException.getMessage()));
    }
    
    @Test
    void shouldGetAllTest(){
        when(curvePointRepository.findAll()).thenReturn(curvePoints);
        
        List<CurvePoint> result = curvePointService.getAll();
        
        assertEquals(curvePoints.size(), result.size());
    }
    
    @Test
    void shouldUpdateCurvePointTest(){
        CurvePointDto toUpdate = CurvePointDto.builder()
                .id(1)
                .term(21d)
                .value(98d)
                .build();
        curvePoint.setValue(toUpdate.getValue());
        curvePoint.setTerm(toUpdate.getTerm());
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);
        
        CurvePoint result = curvePointService.update(toUpdate);
        
        assertEquals(curvePoint.getValue(), result.getValue());
        assertEquals(curvePoint.getTerm(), result.getTerm());
    }
    
    @Test
    void shouldDeleteCurvePointTest(){
        doNothing().when(curvePointRepository).deleteById(1);
        
        curvePointService.deleteById(1);
        
        assertDoesNotThrow(() -> curvePointService.deleteById(1));
    }
    

}
