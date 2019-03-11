package com.dongpeng.system.controller;

import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.entity.system.BreedLogistics;
import com.dongpeng.system.service.BreedLogisticsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/breedLogistics")
public class BreedLogisticsController extends BaseDataController<BreedLogisticsService,BreedLogistics> {
}
