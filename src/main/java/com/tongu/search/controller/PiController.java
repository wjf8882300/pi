package com.tongu.search.controller;

import com.tongu.search.model.RespData;
import com.tongu.search.model.bo.PiBO;
import com.tongu.search.service.PiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pi")
public class PiController extends BaseController {
	
	@Autowired
	private PiService piService;

	@PostMapping("/send")
	public RespData<Void> send(@RequestBody PiBO piBO) {
		piService.send(piBO.getType());
		return success();
	}
}
