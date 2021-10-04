package com.cafein.service;

import com.cafein.dto.bhour.selectbhour.SelectBhourInput;
import com.cafein.dto.bhour.selectbhour.SelectBhourOutput;
import com.cafein.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BhourService {
    ResponseEntity<Response<List<SelectBhourOutput>>> selectBhour(SelectBhourInput selectBhourInput);
}
