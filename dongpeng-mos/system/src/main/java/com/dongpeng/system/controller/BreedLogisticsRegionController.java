package com.dongpeng.system.controller;

import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.entity.system.BreedLogisticsRegion;
import com.dongpeng.system.service.BreedLogisticsRegionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/breedLogisticsRegion")
public class BreedLogisticsRegionController extends BaseDataController<BreedLogisticsRegionService,BreedLogisticsRegion> {
}
