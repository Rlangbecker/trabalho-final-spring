package com.vemser.geekers.controller.Classes;

import com.vemser.geekers.controller.Interfaces.LogMatchControllerInterface;
import com.vemser.geekers.dto.LogDTO;
import com.vemser.geekers.service.LogMatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/logMatchs")
@Validated
public class LogMatchController implements LogMatchControllerInterface {

    private final LogMatchService logMatchService;

    @GetMapping
    public ResponseEntity<List<LogDTO>> findAll(){
        List<LogDTO> list = logMatchService.listAllLogs();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @GetMapping("/log-por-data")
    public ResponseEntity<List<LogDTO>> findByDate(@RequestParam("Data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                       LocalDate data){
        return new ResponseEntity<>(logMatchService.listLogsByDataCriado(data), HttpStatus.OK);
    }

}
