package com.dongpeng.system.controller;

import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.entity.system.Product;
import com.dongpeng.system.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController extends BaseDataController<ProductService,Product> {

}
